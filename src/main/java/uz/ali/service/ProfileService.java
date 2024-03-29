package uz.ali.service;

import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;
import uz.ali.model.Profile;
import uz.ali.util.MD5Util;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static uz.ali.container.CompoundContainer.*;
import static uz.ali.container.CompoundContainer.scannerNum;
import static uz.ali.util.ValidateInputs.*;

public class ProfileService {

    public void addProfile() {
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

        String role = roleChecker("Enter role (STAFF OR ADMIN): ");
        Profile profile = new Profile(name, surname, phone, login, MD5Util.encode(password), ProfileRole.valueOf(role));


        if (profileRepository.isProfileExists(profile.getLogin())) {
            System.out.println("Such " + profile.getLogin() + " login already exists!");
            return;
        }
        profile.setCreatedDate(LocalDateTime.now());
        profile.setProfileStatus(ProfileStatus.ACTIVE);
        int effectedRows = profileRepository.addProfile(profile);
        if (effectedRows == 1) {
            System.out.println("Profile is added!");
        } else {
            System.out.println("Error in adding profile!");
        }
    }


    public void search() {
        String searchTerm;
        do {
            System.out.print("Enter search term (Profile's id, name, surname, login, or phone): ");
            scannerStr = new Scanner(System.in);
            searchTerm = scannerStr.nextLine();
        } while (searchTerm.isBlank());

        List<Profile> profileList = new LinkedList<>();

        if (isNumeric(searchTerm)) {
            if (!profileRepository.getProfileByPhoneNumber(searchTerm)) {
                // No phone number matches the entered numeric search term
                Profile profileById = profileRepository.getProfileById(Integer.parseInt(searchTerm));
                if (profileById != null) {
                    profileList.add(profileById);
                }
            } else {
                // The entered numeric search term matches a phone number
                profileList = profileRepository.search(searchTerm, ProfileRole.ADMIN, ProfileRole.STAFF);
            }
        } else {
            profileList = profileRepository.search(searchTerm, ProfileRole.ADMIN, ProfileRole.STAFF);
        }

        if (!profileList.isEmpty()) {
            printProfileList(profileList, false);
        } else {
            System.out.println("No matching profiles found.");
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }


    public void getStudentProfileList() {
        printStudentProfileList(profileRepository.getAllProfiles(ProfileRole.STUDENT));
    }

    public void getProfileList() {
        printProfileList(profileRepository.getAllProfiles(ProfileRole.ADMIN, ProfileRole.STAFF), true);
    }

    public void printProfileList(List<Profile> profileList, boolean notShowRole) {
        if (profileList.isEmpty()) {
            System.out.println("No Profiles available.");
        } else if (notShowRole) {
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("| Id | Name           | Surname        | Login          | Phone        | Status       | Role         | Created Date               |");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");

            for (Profile profile : profileList) {
                String formattedProfile = String.format("| %-3d| %-15s| %-15s| %-15s| %-13s| %-13s| %-13s| %-27s|",
                        profile.getId(), profile.getName(), profile.getSurname(), profile.getLogin(),
                        profile.getPhone(), profile.getProfileStatus(), profile.getProfileRole(), profile.getCreatedDate());
                System.out.println(formattedProfile);
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        } else {
            System.out.println("--------------------------------------------------------------------------------------------------------------------");
            System.out.println("| Id | Name           | Surname        | Login          | Phone        | Status       | Created Date               |");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");

            for (Profile profile : profileList) {
                String formattedProfile = String.format("| %-3d| %-15s| %-15s| %-15s| %-13s| %-13s| %-27s|",
                        profile.getId(), profile.getName(), profile.getSurname(), profile.getLogin(),
                        profile.getPhone(), profile.getProfileStatus(), profile.getCreatedDate());
                System.out.println(formattedProfile);
            }
            System.out.println("--------------------------------------------------------------------------------------------------------------------");
        }
    }

    public void printStudentProfileList(List<Profile> profileList) {
        if (profileList.isEmpty()) {
            System.out.println("No Profiles available.");
        } else {
            System.out.println("--------------------------------------------------------------------------------------------------------------------");
            System.out.println("| Id | Name           | Surname        | Login          | Phone        | Role         | Created Date               |");
            System.out.println("--------------------------------------------------------------------------------------------------------------------");

            for (Profile profile : profileList) {
                String formattedProfile = String.format("| %-3d| %-15s| %-15s| %-15s| %-13s| %-13s| %-27s|",
                        profile.getId(), profile.getName(), profile.getSurname(), profile.getLogin(),
                        profile.getPhone(), profile.getProfileRole(), profile.getCreatedDate());
                System.out.println(formattedProfile);
            }
            System.out.println("--------------------------------------------------------------------------------------------------------------------");
        }
    }


    public void changeStatus() {
        System.out.print("Enter profile ID: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int profileId = scannerNum.nextInt();

        Profile profile = profileRepository.getProfileById(profileId);
        if (profile == null) {
            System.out.println("Profile not found!");
            return;
        }
        int effectedRows;
        if (profile.getProfileStatus().equals(ProfileStatus.ACTIVE)) {
            effectedRows = profileRepository.updateStatus(profileId, ProfileStatus.BLOCK);
        } else {
            effectedRows = profileRepository.updateStatus(profileId, ProfileStatus.ACTIVE);
        }

        if (effectedRows == 1) {
            System.out.println("Status successfully changed!");
        } else {
            System.out.println("Status is not changed!");
        }
    }

    public void changeStudentProfileStatus(ProfileStatus status) {
        System.out.print("Enter Student Profile ID: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int id = scannerNum.nextInt();
        Profile profile = profileRepository.getProfileById(id);
        if (profile == null) {
            System.out.println("Student Profile not found!");
            return;
        }
        if (!profile.getProfileRole().equals(ProfileRole.STUDENT)) {
            System.out.println("Only Student Profile ID can be entered!");
            return;
        }
        int effectedRows = profileRepository.updateStatus(id, status);

        if (effectedRows == 1) {
            System.out.println("Status successfully changed!");
        } else {
            System.out.println("Status is not changed!");
        }
    }


}
