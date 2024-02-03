package uz.ali.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableRepository {

    public void createTables() {
        String profileTable = "CREATE TABLE IF NOT EXISTS profile (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(25) NOT NULL," +
                "surname VARCHAR(25) NOT NULL," +
                "login VARCHAR(25) NOT NULL UNIQUE," +
                "password VARCHAR(35) NOT NULL," +
                "phone VARCHAR(12) NOT NULL UNIQUE," +
                "profile_status VARCHAR(15) NOT NULL," +
                "profile_role VARCHAR(15) NOT NULL," +
                "created_date TIMESTAMP DEFAULT NOW()" +
                ")";

        executeSql(profileTable);

        String categoryTable = "CREATE TABLE IF NOT EXISTS category (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(25) NOT NULL UNIQUE," +
                "created_date TIMESTAMP DEFAULT NOW()," +
                "visible BOOLEAN DEFAULT TRUE" +
                ")";
        executeSql(categoryTable);

        String bookTable = "CREATE TABLE IF NOT EXISTS book (" +
                "id SERIAL PRIMARY KEY," +
                "title VARCHAR(25) NOT NULL," +
                "author VARCHAR(25) NOT NULL," +
                "category_id INT NOT NULL," +
                "publish_date DATE NOT NULL," +
                "available_day int NOT NULL," +
                "created_date TIMESTAMP DEFAULT NOW()," +
                "visible BOOLEAN DEFAULT TRUE," +
                "CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category (id)" +
                ")";
        executeSql(bookTable);

        String studentBookTable = "CREATE TABLE IF NOT EXISTS student_book (" +
                "id SERIAL PRIMARY KEY," +
                "profile_id INT NOT NULL," +
                "book_id INT NOT NULL," +
                "status VARCHAR(10) NOT NULL," +
                "created_date TIMESTAMP DEFAULT NOW()," +
                "returned_date TIMESTAMP," +
                "deadline_date DATE," +
                "CONSTRAINT fk_profile_id FOREIGN KEY (profile_id) REFERENCES profile (id)," +
                "CONSTRAINT fk_book_id FOREIGN KEY (book_id) REFERENCES book (id)" +
                ")";
        executeSql(studentBookTable);

    }

    public void executeSql(String sql) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table: " + e.getMessage(), e);
        }
    }

}
