package uz.ali.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/library_project_in_jdbc_in_java_maven",
                    "library_userjon_jdbc_project", "library_userjon");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
