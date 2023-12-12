package uz.ali.service;

import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;
import uz.ali.model.Profile;
import uz.ali.util.MD5Util;

import java.time.LocalDateTime;

import static uz.ali.container.CompoundContainer.*;

public class AuthService {


    public void login(String login, String password) {
        Profile profile = profileRepository.getProfileByLogin(login);
        if (profile == null) {
            System.out.println("Login or Password is wrong!");
            return;
        }
        String encodedPassword = MD5Util.encode(password);
        if (!encodedPassword.equals(profile.getPassword())) {
            System.out.println("Login or Password is wrong!");
            return;
        }
        if (!profile.getProfileStatus().equals(ProfileStatus.ACTIVE)) {
            System.out.println("Status is wrong!");
            return;
        }

        System.out.println("\n\t\t********** Welcome to Library Project **********");
        // Redirect
        if (profile.getProfileRole().equals(ProfileRole.STUDENT)) {
            studentController.start();
        } else if (profile.getProfileRole().equals(ProfileRole.STAFF)) {
            staffController.start();
        } else if (profile.getProfileRole().equals(ProfileRole.ADMIN)) {
            adminController.start();
        }

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
