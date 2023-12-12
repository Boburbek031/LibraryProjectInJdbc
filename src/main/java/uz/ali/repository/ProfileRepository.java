package uz.ali.repository;

import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;
import uz.ali.model.Profile;

import java.sql.*;

public class ProfileRepository {


    public boolean isProfileExists(String login) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT COUNT(*) FROM profile WHERE login = ?")) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Profile getProfileByLogin(String login) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT id, name, surname, login, password, phone, profile_status, profile_role, created_date " +
                             "FROM profile WHERE login = ?")) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapProfileFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int createProfile(Profile profile) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO profile(name, surname, login, password, phone, profile_status, profile_role, created_date) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, profile.getName());
            preparedStatement.setString(2, profile.getSurname());
            preparedStatement.setString(3, profile.getLogin());
            preparedStatement.setString(4, profile.getPassword());
            preparedStatement.setString(5, profile.getPhone().replace("+", "").trim());
            preparedStatement.setString(6, profile.getProfileStatus().name());
            preparedStatement.setString(7, profile.getProfileRole().name());
            preparedStatement.setTimestamp(8, Timestamp.valueOf(profile.getCreatedDate()));
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Profile mapProfileFromResultSet(ResultSet resultSet) throws SQLException {
        return new Profile(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("phone"),
                resultSet.getTimestamp("created_date").toLocalDateTime(),
                ProfileStatus.valueOf(resultSet.getString("profile_status")),
                ProfileRole.valueOf(resultSet.getString("profile_role"))
        );
    }

}
