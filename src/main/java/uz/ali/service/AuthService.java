package uz.ali.service;

import uz.ali.controller.AdminController;
import uz.ali.controller.StaffController;
import uz.ali.controller.StudentController;
import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;
import uz.ali.model.Profile;
import uz.ali.repository.ProfileRepository;
import uz.ali.util.MD5Util;

import java.time.LocalDateTime;

public class AuthService {

    ProfileRepository profileRepository = new ProfileRepository();
    AdminController adminController = new AdminController();
    StudentController studentController = new StudentController();
    StaffController staffController = new StaffController();


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
