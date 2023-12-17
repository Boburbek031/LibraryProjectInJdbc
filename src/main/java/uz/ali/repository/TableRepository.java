package uz.ali.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableRepository {

    public void createTables() {
        String sqlQueryToCreateProfileTable = "CREATE TABLE IF NOT EXISTS profile (" +
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

        executeSql(sqlQueryToCreateProfileTable);

        String sqlQueryToCreateCategoryTable = "CREATE TABLE IF NOT EXISTS category (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(25) NOT NULL UNIQUE," +
                "created_date TIMESTAMP DEFAULT NOW()," +
                "visible BOOLEAN DEFAULT TRUE" +
                ")";
        executeSql(sqlQueryToCreateCategoryTable);

        String sqlQueryToCreateBookTable = "CREATE TABLE IF NOT EXISTS book (" +
                "id SERIAL PRIMARY KEY," +
                "title VARCHAR(25) NOT NULL," +
                "author VARCHAR(25) NOT NULL," +
                "category_id INT NOT NULL UNIQUE," +
                "publish_date DATE," +
                "available_day int," +
                "created_date TIMESTAMP DEFAULT NOW()," +
                "visible BOOLEAN DEFAULT TRUE," +
                "CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category (id)" +
                ")";
        executeSql(sqlQueryToCreateBookTable);
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
