package uz.ali.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TableRepository {

    public void createTable() {
        String sqlCreateTableQuery = "create table if not exists profile (" +
                "id serial primary key," +
                "name varchar (25) not null," +
                "surname varchar (25) not null," +
                "login varchar (25) not null unique," +
                "password varchar (35) not null," +
                "phone varchar (12) not null unique," +
                "profile_status varchar (15) not null," +
                "profile_role varchar (15) not null," +
                "created_date timestamp default now()" +
                ");";
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCreateTableQuery)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating table: " + e.getMessage(), e);
        }
    }

}
