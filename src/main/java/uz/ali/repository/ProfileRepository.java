package uz.ali.repository;

import uz.ali.db.DatabaseUtil;
import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;
import uz.ali.model.Profile;

import java.sql.*;

public class ProfileRepository {


    public boolean isProfileExists(String login) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM profile WHERE login = ?")) {
            preparedStatement.setString(1, "login");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            // Consider logging the exception or throwing a custom exception for better error handling
            e.printStackTrace();
        }
        return false;
    }


    public Profile getProfileByLogin(String login) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id, name, surname, login, password, phone, profileStatus, profileRole, createdDate " +
                             "FROM profile WHERE login = ?")) {
            preparedStatement.setString(1, "login");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Profile profile = new Profile(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("surname"),
                            resultSet.getString("login"),
                            resultSet.getString("password"),
                            resultSet.getString("phone"),
                            resultSet.getTimestamp("createdDate").toLocalDateTime());
                    profile.setProfileStatus(ProfileStatus.valueOf(resultSet.getString("profileStatus")));
                    profile.setProfileRole(ProfileRole.valueOf(resultSet.getString("profileRole")));
                    return profile;
                }
            }
        } catch (SQLException e) {
            // Consider logging the exception or throwing a custom exception for better error handling
            e.printStackTrace();
        }
        return null;
    }

    public int createProfile(Profile profile) {
        String insertQuery = "INSERT INTO profile(id, name, surname, login, password, phone, profileStatus, profileRole, createdDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, profile.getId());
            preparedStatement.setString(2, profile.getName());
            preparedStatement.setString(3, profile.getSurname());
            preparedStatement.setString(4, profile.getLogin());
            preparedStatement.setString(5, profile.getPassword());
            preparedStatement.setString(6, profile.getPhone());
            preparedStatement.setString(7, profile.getProfileStatus().name());
            preparedStatement.setString(8, profile.getProfileRole().name());
            preparedStatement.setTimestamp(9, Timestamp.valueOf(profile.getCreatedDate()));
            System.out.println("Profile inserted successfully.");
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
