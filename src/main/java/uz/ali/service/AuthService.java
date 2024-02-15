package uz.ali.service;

import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;
import uz.ali.model.Profile;
import uz.ali.util.MD5Util;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

import static uz.ali.container.CompoundContainer.*;
import static uz.ali.util.ValidateInputs.*;

public class AuthService {

    public void registration() {
        scannerStr = new Scanner(System.in);
        String name = getNonEmptyInput("Enter name (at least 2 characters): ");
        String surname = getNonEmptyInput("Enter surname (at least 2 characters): ");
        String phone = getValidPhoneNumber();
        String login;
        String password;
        do {
            login = getNonEmptyInput("Enter login (at least 5 characters): ");
        } while (!isValidLogin(login));

        do {
            password = getNonEmptyInput("Enter password (at least 6 characters with 1 uppercase, 1 lowercase, 1 digit, and 1 special symbol): ");
        } while (!isValidPassword(password));

        Profile profile = new Profile(name, surname, phone, login, MD5Util.encode(password));
        if (profileRepository.isProfileExists(profile.getLogin())) {
            System.out.println("Such " + profile.getLogin() + " login already exists!");
            return;
        }
        profile.setCreatedDate(LocalDateTime.now());
        profile.setProfileRole(ProfileRole.STUDENT);
        profile.setProfileStatus(ProfileStatus.ACTIVE);
        int effectedRows = profileRepository.addProfile(profile);
        if (effectedRows == 1) {
            System.out.println("Registration is completed!");
        }
    }

    public void login() {
        String login = getNonEmptyInput("Enter login: ");
        String password = getNonEmptyInput("Enter password: ");
        Profile profile = profileRepository.getProfileByLogin(login);

        if (profile == null || !isLoginValid(profile, password)) {
            System.out.println("Login or Password is wrong!");
            return;
        }

        if (!isProfileActive(profile)) {
            System.out.println("Status is wrong!");
            return;
        }

        System.out.println("\n\t\t********** Welcome to Library Project **********");
        // Redirect
        currentProfile = profile;
        redirectToRole(profile);
    }

    public void redirectToRole(Profile profile) {
        switch (profile.getProfileRole()) {
            case STUDENT:
                studentController.start();
                break;
            case STAFF:
                staffController.start();
                break;
            case ADMIN:
                adminController.start();
                break;
            default:
                System.out.println("Unknown role!");
                break;
        }
    }

    public boolean isLoginValid(Profile profile, String password) {
        return Objects.equals(MD5Util.encode(password), profile.getPassword());
    }

    public boolean isProfileActive(Profile profile) {
        return profile.getProfileStatus().equals(ProfileStatus.ACTIVE);
    }


}
