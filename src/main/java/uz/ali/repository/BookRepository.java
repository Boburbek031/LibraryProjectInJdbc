package uz.ali.repository;

import uz.ali.model.Book;
import uz.ali.model.Category;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BookRepository {


    public int saveBook(Book book) {
        try (Connection connection = ConnectionRepository.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO book(title, author, category_id, publish_date, available_day, created_date, visible) " + "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

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

        try (Connection connection = ConnectionRepository.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT b.*, c.name as category_name FROM book as b " + "inner join category as c on c.id = b.category_id WHERE b.visible = true order by b.id")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPublishDate(resultSet.getDate("publish_date").toLocalDate());
                book.setAvailableDay(resultSet.getInt("available_day"));
                book.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                Category category = new Category(resultSet.getInt("category_id"), resultSet.getString("category_name"));
                book.setCategory(category);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

}
