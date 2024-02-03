package uz.ali.repository;

import uz.ali.enums.StudentBookStatus;
import uz.ali.model.Book;
import uz.ali.model.Category;
import uz.ali.model.StudentBook;
import uz.ali.service.StudentBookService;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static uz.ali.container.CompoundContainer.*;

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
}
