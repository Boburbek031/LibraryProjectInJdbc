package uz.ali.service;

import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;
import uz.ali.model.Profile;
import uz.ali.util.MD5Util;

import java.time.LocalDateTime;
import java.util.Objects;

import static uz.ali.container.CompoundContainer.*;

public class AuthService {

    public void login(String login, String password) {
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
        redirectToRole(profile);
    }

    private void redirectToRole(Profile profile) {
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

    private boolean isLoginValid(Profile profile, String password) {
        return Objects.equals(MD5Util.encode(password), profile.getPassword());
    }

    private boolean isProfileActive(Profile profile) {
        return profile.getProfileStatus().equals(ProfileStatus.ACTIVE);
    }


    public void registration(Profile profile) {
        // check all the details
        boolean profileExists = profileRepository.isProfileExists(profile.getLogin());
        if (profileExists) {
            System.out.println("Such " + profile.getLogin() + " login already exists!");
            return;
        }
        profile.setCreatedDate(LocalDateTime.now());
        profile.setProfileRole(ProfileRole.STUDENT);
        profile.setProfileStatus(ProfileStatus.ACTIVE);
        int effectedRows = profileRepository.createProfile(profile);
        if (effectedRows == 1) {
            System.out.println("Registration is completed!");
        }

    }
}
