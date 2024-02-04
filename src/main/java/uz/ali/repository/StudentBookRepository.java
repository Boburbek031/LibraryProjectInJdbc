package uz.ali.repository;

import uz.ali.enums.StudentBookStatus;
import uz.ali.model.Book;
import uz.ali.model.Category;
import uz.ali.model.Profile;
import uz.ali.model.StudentBook;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class StudentBookRepository {


    public int save(StudentBook studentBook) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO student_book(profile_id, book_id, status, created_date, deadline_date) " + "VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setInt(1, studentBook.getStudentId());
            preparedStatement.setInt(2, studentBook.getBookId());
            preparedStatement.setString(3, studentBook.getStatus().name());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(studentBook.getCreatedDate()));
            preparedStatement.setDate(5, Date.valueOf(studentBook.getDeadlineDate()));
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
                     connection.prepareStatement("SELECT * FROM student_book WHERE profile_id = ? and status = 'TAKEN'")) {

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

    public List<StudentBook> studentBookOnHandAndBookHistory(Integer studentId, StudentBookStatus statusFilter) {
        List<StudentBook> bookList = new LinkedList<>();
        String query = "SELECT sb.id, sb.created_date, sb.deadline_date, sb.returned_date, b.id as bookId, b.title, " +
                "b.author, b.category_id as categoryId, b.available_day, c.name as categoryName FROM student_book as sb "
                + "inner join book as b on b.id = sb.book_id "
                + "inner join category as c on c.id = b.category_id WHERE sb.profile_id = ?";

        if (statusFilter != null) {
            query += " and sb.status = ? ";
        }
        query += " order by sb.created_date desc";

        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, studentId);
            if (statusFilter != null) {
                preparedStatement.setString(2, statusFilter.name());
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StudentBook studentBook = new StudentBook();
                studentBook.setId(resultSet.getInt("id"));
                studentBook.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                studentBook.setBookId(resultSet.getInt("bookId"));
                studentBook.setDeadlineDate(resultSet.getDate("deadline_date").toLocalDate());
                Timestamp returnedDate = resultSet.getTimestamp("returned_date");
                if (returnedDate != null) {
                    studentBook.setReturnedDate(returnedDate.toLocalDateTime());
                }

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

    public List<StudentBook> allBooksOnHand() {
        List<StudentBook> bookList = new LinkedList<>();
        String query = "SELECT b.id as bookId, b.category_id as categoryId, c.name as categoryName, b.title, b.author, " +
                "sb.created_date as takenDate, sb.deadline_date, p.id as profileId, " +
                "p.name as profileName, p.surname as profileSurname, p.phone FROM student_book as sb " +
                "inner join book as b on b.id = sb.book_id " +
                "inner join category as c on c.id = b.category_id " +
                "inner join profile as p on p.id = sb.profile_id " +
                "where sb.status = 'TAKEN' order by sb.created_date desc;";

        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StudentBook studentBook = new StudentBook();
                studentBook.setStudentId(resultSet.getInt("profileId"));
                studentBook.setCreatedDate(resultSet.getTimestamp("takenDate").toLocalDateTime());
                studentBook.setDeadlineDate(resultSet.getDate("deadline_date").toLocalDate());

                Book book = new Book();
                book.setId(resultSet.getInt("bookId"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));

                Category category = new Category(resultSet.getInt("categoryId"), resultSet.getString("categoryName"));
                book.setCategory(category);
                studentBook.setBook(book);

                Profile studentProfile = new Profile();
                studentProfile.setId(resultSet.getInt("profileId"));
                studentProfile.setName(resultSet.getString("profileName"));
                studentProfile.setSurname(resultSet.getString("profileSurname"));
                studentProfile.setPhone(resultSet.getString("phone"));
                studentBook.setStudentProfile(studentProfile);
                bookList.add(studentBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public List<StudentBook> bookHistoryById(Integer bookId) {
        List<StudentBook> bookHistory = new LinkedList<>();
        String query = "SELECT sb.status, sb.created_date as takenDate, sb.returned_date, p.id as profileId, " +
                "p.name as profileName, p.surname as profileSurname, p.phone FROM student_book as sb " +
                "inner join profile as p on p.id = sb.profile_id " +
                "where sb.book_id = ? order by sb.created_date desc;";
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StudentBook studentBook = new StudentBook();
                studentBook.setStatus(StudentBookStatus.valueOf(resultSet.getString("status")));
                studentBook.setStudentId(resultSet.getInt("profileId"));
                studentBook.setCreatedDate(resultSet.getTimestamp("takenDate").toLocalDateTime());
                Timestamp returnedDate = resultSet.getTimestamp("returned_date");
                if (returnedDate != null) {
                    studentBook.setReturnedDate(returnedDate.toLocalDateTime());
                }

                Profile studentProfile = new Profile();
                studentProfile.setId(resultSet.getInt("profileId"));
                studentProfile.setName(resultSet.getString("profileName"));
                studentProfile.setSurname(resultSet.getString("profileSurname"));
                studentProfile.setPhone(resultSet.getString("phone"));
                studentBook.setStudentProfile(studentProfile);
                bookHistory.add(studentBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookHistory;
    }

    public List<StudentBook> bestBooks() {
        List<StudentBook> bestBooks = new LinkedList<>();
        String query = "select c.id as categoryId, c.name as categoryName, " +
                "b.id as bookId, b.title, b.author, tempTable.takenBookCounts from (select book_id, count(book_id) as takenBookCounts " +
                "from student_book group by book_id) as tempTable " +
                "inner join book as b on b.id = tempTable.book_id " +
                "inner join category as c on c.id = b.category_id order by takenBookCounts desc limit 10;";
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StudentBook studentBook = new StudentBook();
                studentBook.setTakenCount(resultSet.getInt("takenBookCounts"));

                Book book = new Book();
                book.setId(resultSet.getInt("bookId"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));

                Category category = new Category(resultSet.getInt("categoryId"), resultSet.getString("categoryName"));
                book.setCategory(category);
                studentBook.setBook(book);
                bestBooks.add(studentBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bestBooks;
    }


   /* public StudentBook getStudentBook(Integer bookId, Integer studentId) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "select * from student_book where book_id = ? and profile_id = ? and status = 'TAKEN';")) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StudentBook studentBook = new StudentBook();
                studentBook.setId(resultSet.getInt("id"));
                studentBook.setStudentId(resultSet.getInt("profile_id"));
                studentBook.setBookId(resultSet.getInt("book_id"));
                studentBook.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                studentBook.setDeadlineDate(resultSet.getDate("deadline_date").toLocalDate());
                studentBook.setStatus(StudentBookStatus.valueOf(resultSet.getString("status")));
                return studentBook;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int returnStudentBook(Integer studentBookId) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE student_book SET status = 'RETURNED', returned_date = CURRENT_TIMESTAMP where id = ?")) {
            preparedStatement.setInt(1, studentBookId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }*/

    public int returnStudentBook(Integer bookId, Integer studentId) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE student_book SET status = 'RETURNED', returned_date = now()" +
                             " where book_id = ? and profile_id = ? and status = 'TAKEN';")) {
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, studentId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
