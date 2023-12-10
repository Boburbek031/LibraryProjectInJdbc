package uz.ali.model;

import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Profile {


    private Integer id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String phone;
    private ProfileStatus profileStatus;

    private ProfileRole profileRole;
    private LocalDateTime createdDate;

    public Profile() {
    }

    public Profile(String name, String surname, String login, String password, String phone, ProfileStatus profileStatus, ProfileRole profileRole, LocalDateTime createdDate) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.profileStatus = profileStatus;
        this.profileRole = profileRole;
        this.createdDate = createdDate;
    }

    public Profile(Integer id, String name, String surname, String login, String password, String phone, LocalDateTime createdDate, ProfileStatus profileStatus, ProfileRole profileRole) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.createdDate = createdDate;
        this.profileStatus = profileStatus;
        this.profileRole = profileRole;
    }

    public Profile(Integer id, String name, String surname, String login, String password, String phone, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ProfileStatus getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(ProfileStatus profileStatus) {
        this.profileStatus = profileStatus;
    }

    public ProfileRole getProfileRole() {
        return profileRole;
    }

    public void setProfileRole(ProfileRole profileRole) {
        this.profileRole = profileRole;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", createdDate=" + createdDate +
                ", profileStatus=" + profileStatus +
                ", profileRole=" + profileRole +
                '}';
    }
}
