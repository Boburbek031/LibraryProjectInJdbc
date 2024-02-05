package uz.ali.repository;

import uz.ali.model.Book;
import uz.ali.model.Category;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BookRepository {


    public int saveBook(Book book) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO book(title, author, category_id, publish_date, available_day, created_date, visible) " + "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getCategoryId());
            preparedStatement.setDate(4, Date.valueOf(book.getPublishDate()));
            preparedStatement.setInt(5, book.getAvailableDay());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(book.getCreatedDate()));
            preparedStatement.setBoolean(7, book.getVisible());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isBookExistsById(Integer bookId) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM book WHERE id = ?")) {
            preparedStatement.setInt(1, bookId);
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

    public Book getBookById(int bookId) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT b.*, c.name as categoryName FROM book as b " +
                             "inner join category as c " +
                             "on c.id = b.category_id WHERE b.id = ? AND b.visible = true")) {

            preparedStatement.setInt(1, bookId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Book book = new Book();
                    book.setId(resultSet.getInt("id"));
                    book.setTitle(resultSet.getString("title"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setPublishDate(resultSet.getDate("publish_date").toLocalDate());
                    book.setAvailableDay(resultSet.getInt("available_day"));
                    book.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                    Category category = new Category(resultSet.getInt("category_id"), resultSet.getString("categoryName"));
                    book.setCategory(category);
                    return book;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int updateBook(Book bookToUpdate) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE book SET title=?, author=?, publish_date=?, available_day=? WHERE id=?")) {

            preparedStatement.setString(1, bookToUpdate.getTitle());
            preparedStatement.setString(2, bookToUpdate.getAuthor());
            preparedStatement.setDate(3, Date.valueOf(bookToUpdate.getPublishDate()));
            preparedStatement.setInt(4, bookToUpdate.getAvailableDay());
            preparedStatement.setInt(5, bookToUpdate.getId());
            bookToUpdate.setCreatedDate(LocalDateTime.now());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Book> getBookList() {
        List<Book> bookList = new LinkedList<>();

        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT b.*, c.name as categoryName FROM book as b " + "inner join category as c " +
                             "on c.id = b.category_id WHERE b.visible = true order by b.id")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPublishDate(resultSet.getDate("publish_date").toLocalDate());
                book.setAvailableDay(resultSet.getInt("available_day"));
                book.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                Category category = new Category(resultSet.getInt("category_id"), resultSet.getString("categoryName"));
                book.setCategory(category);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public List<Book> getAllBooksByCategoryId(Integer categoryId) {
        List<Book> bookList = new LinkedList<>();

        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT b.id, b.title, b.author, b.publish_date FROM book as b " +
                             "where b.category_id = ? order by b.id")) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPublishDate(resultSet.getDate("publish_date").toLocalDate());
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public int deleteBookById(Integer bookId) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE book SET visible = false WHERE id = ?")) {

            preparedStatement.setInt(1, bookId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Book> searchBook(String searchTerm) {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT b.*, c.name as categoryName FROM book as b inner join category as c " +
                "on c.id = b.category_id WHERE LOWER(title) LIKE ? " +
                "OR LOWER(author) LIKE ? and b.visible = true order by b.id asc";
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            String likeTerm = "%" + searchTerm.toLowerCase() + "%";
            preparedStatement.setString(1, likeTerm);
            preparedStatement.setString(2, likeTerm);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = new Book();
                    book.setId(resultSet.getInt("id"));
                    book.setTitle(resultSet.getString("title"));
                    book.setAuthor(resultSet.getString("author"));
                    book.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                    book.setPublishDate(resultSet.getDate("publish_date").toLocalDate());
                    Category category = new Category(resultSet.getInt("category_id"), resultSet.getString("categoryName"));
                    book.setCategory(category);
                    bookList.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

}
