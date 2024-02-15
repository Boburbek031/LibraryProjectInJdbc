package uz.ali.repository;

import uz.ali.model.Category;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CategoryRepository {

    public boolean isCategoryExistsByName(String categoryName) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM category WHERE name = ?")) {
            preparedStatement.setString(1, categoryName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isCategoryExistsById(Integer categoryId) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT EXISTS(SELECT 1 FROM category WHERE id = ?);")) {
            preparedStatement.setInt(1, categoryId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Category getCategoryByName(String categoryName) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM category WHERE name = ?")) {
            preparedStatement.setString(1, categoryName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapCategoryFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Category mapCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        return new Category(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getTimestamp("created_date").toLocalDateTime(),
                resultSet.getBoolean("visible"));
    }

    public int saveCategory(Category category) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO category (name, created_date, visible) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, category.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(category.getCreatedDate()));
            preparedStatement.setBoolean(3, category.isVisible());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public List<Category> getCategoryList() {
        List<Category> categoryList = new LinkedList<>();

        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM category WHERE visible = true order by id")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                categoryList.add(mapCategoryFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }


    public int deleteCategoryById(Integer categoryId) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE category SET visible = false WHERE id = ?")) {

            preparedStatement.setInt(1, categoryId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateCategoryById(int categoryId, String newCategoryName) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE category SET name = ? WHERE id = ?")) {

            preparedStatement.setString(1, newCategoryName);
            preparedStatement.setInt(2, categoryId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
