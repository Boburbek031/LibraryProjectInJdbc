package uz.ali.repository;

import uz.ali.db.DatabaseUtil;
import uz.ali.model.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileRepository {


  /*  public Profile getProfileByLogin(String login) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("select * from profile where login = ?")) {
            preparedStatement.setString(1, "login");
            ResultSet resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }*/


}
