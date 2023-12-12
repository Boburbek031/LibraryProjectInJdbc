package uz.ali.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionRepository {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/library_project_in_jdbc_in_java_maven";
    private static final String DB_USERNAME = "library_userjon_jdbc_project";
    private static final String DB_PASSWORD = "library_userjon";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }


}
