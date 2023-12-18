package uz.ali.repository;

import uz.ali.model.Book;
import uz.ali.model.Category;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BookRepository {


    public int saveBook(Book book) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO book(title, author, category_id, publish_date, available_day, created_date, visible) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

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

    public List<Book> getBookList() {
        List<Book> bookList = new LinkedList<>();

        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM book WHERE visible = true order by id")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookList.add(mapBookFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public Book mapBookFromResultSet(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("author"),
                resultSet.getInt("category_id"),
                resultSet.getDate("publish_date").toLocalDate(),
                resultSet.getInt("available_day"),
                resultSet.getTimestamp("created_date").toLocalDateTime());
    }
}
