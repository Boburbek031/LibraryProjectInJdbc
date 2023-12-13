package uz.ali.repository;

import uz.ali.model.Category;
import uz.ali.model.Profile;

import java.sql.*;

public class CategoryRepository {

    public boolean isCategoryExists(String categoryName) {
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


}
