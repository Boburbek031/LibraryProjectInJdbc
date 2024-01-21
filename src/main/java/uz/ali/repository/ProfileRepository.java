package uz.ali.repository;

import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;
import uz.ali.model.Book;
import uz.ali.model.Category;
import uz.ali.model.Profile;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProfileRepository {


    public boolean isProfileExists(String login) {
        try (Connection connection = ConnectionRepository.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM profile WHERE login = ?")) {
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
        try (Connection connection = ConnectionRepository.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, surname, login, password, phone, profile_status, profile_role, created_date " + "FROM profile WHERE login = ?")) {
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

    public boolean getProfileByPhoneNumber(String phoneNumber) {
        String selectQuery = "select * from profile where phone LIKE ?;";
        try (Connection connection = ConnectionRepository.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            phoneNumber = "%" + phoneNumber + "%";
            preparedStatement.setString(1, phoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null && resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Profile getProfileById(Integer id) {
        try (Connection connection = ConnectionRepository.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM profile WHERE id = ?")) {
            preparedStatement.setInt(1, id);
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

    public int addProfile(Profile profile) {
        try (Connection connection = ConnectionRepository.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO profile(name, surname, login, password, phone, profile_status, profile_role, created_date) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

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

    public List<Profile> getAllProfiles() {
        List<Profile> profileList = new LinkedList<>();

        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM profile WHERE profile_role != 'STUDENT' order by created_date")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                profileList.add(mapProfileFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profileList;
    }

    public List<Profile> searchProfile(String searchTerm) {
        List<Profile> profileList = new LinkedList<>();
        String query = "SELECT * FROM profile WHERE profile_role != 'STUDENT' AND (LOWER(name) LIKE ? " + "OR LOWER(surname) LIKE ? OR LOWER(login) LIKE ? OR phone LIKE ?);";
        try (Connection connection = ConnectionRepository.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            String likeTerm = "%" + searchTerm.toLowerCase() + "%";
            preparedStatement.setString(1, likeTerm);
            preparedStatement.setString(2, likeTerm);
            preparedStatement.setString(3, likeTerm);
            preparedStatement.setString(4, likeTerm);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    profileList.add(mapProfileFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profileList;
    }

    public int updateProfileStatus(Integer id, ProfileStatus profileStatus) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE profile SET profile_status=? WHERE id =?")) {

            preparedStatement.setString(1, profileStatus.name());
            preparedStatement.setInt(2, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public Profile mapProfileFromResultSet(ResultSet resultSet) throws SQLException {
        return new Profile(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("login"), resultSet.getString("password"), resultSet.getString("phone"), resultSet.getTimestamp("created_date").toLocalDateTime(), ProfileStatus.valueOf(resultSet.getString("profile_status")), ProfileRole.valueOf(resultSet.getString("profile_role")));
    }

}
