package uz.ali.repository;

import uz.ali.enums.StudentBookStatus;
import uz.ali.model.Book;
import uz.ali.model.Category;
import uz.ali.model.StudentBook;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class StudentBookRepository {


    public int save(StudentBook studentBook) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO student_book(profile_id, book_id, status, created_date) " + "VALUES (?, ?, ?, ?)")) {

            preparedStatement.setInt(1, studentBook.getStudentId());
            preparedStatement.setInt(2, studentBook.getBookId());
            preparedStatement.setString(3, studentBook.getStatus().name());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(studentBook.getCreatedDate()));
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public List<StudentBook> checkStudentBookAlreadyTakenOrNot(Integer id) {

        List<StudentBook> studentBooksList = new LinkedList<>();
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("SELECT * FROM student_book WHERE profile_id = ?")) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StudentBook studentBook = new StudentBook();
                studentBook.setId(resultSet.getInt("id"));
                studentBook.setStudentId(resultSet.getInt("profile_id"));
                studentBook.setBookId(resultSet.getInt("book_id"));
                studentBook.setStatus(StudentBookStatus.valueOf(resultSet.getString("status")));
                studentBooksList.add(studentBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentBooksList;
    }


    public List<StudentBook> studentBookOnHand(Integer studentId) {


        List<StudentBook> bookList = new LinkedList<>();
        try (Connection connection = ConnectionRepository.getConnection();
             // b.id as bookId  ===> as qilib bookId qilib column ni nomlab javadan o'sha column name qilib chaqirib olamiz.
             // agar alias bermasak javada xatolik boladi yani 2 ta id keladi shunda confuse bo'ladi.
             // shuning uchun ham column larning name lari unique bo'lishi kerak.
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT sb.id, sb.created_date, b.id as bookId, b.title, b.author, b.category_id as categoryId, b.available_day, c.name as categoryName FROM student_book as sb "
                             + "inner join book as b on b.id = sb.book_id "
                             + "inner join category as c on c.id = b.category_id WHERE sb.profile_id = ? order by sb.created_date desc")) {
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StudentBook studentBook = new StudentBook();
                studentBook.setId(resultSet.getInt("id"));
                studentBook.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                studentBook.setBookId(resultSet.getInt("bookId"));

                Book book = new Book();
                book.setId(resultSet.getInt("bookId"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setAvailableDay(resultSet.getInt("available_day"));

                Category category = new Category(resultSet.getInt("categoryId"), resultSet.getString("categoryName"));
                book.setCategory(category);
                studentBook.setBook(book);
                bookList.add(studentBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;

    }


}
