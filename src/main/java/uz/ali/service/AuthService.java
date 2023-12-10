package uz.ali.service;

import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;
import uz.ali.model.Profile;
import uz.ali.repository.ProfileRepository;
import uz.ali.util.MD5Util;

public class AuthService {

    ProfileRepository profileRepository = new ProfileRepository();

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
            // StudentController....
        } else if (profile.getProfileRole().equals(ProfileRole.STAFF)) {
            // StaffController....
        } else if (profile.getProfileRole().equals(ProfileRole.ADMIN)) {
            // AdminController....
        }

    }


}
