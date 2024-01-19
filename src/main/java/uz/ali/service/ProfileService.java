package uz.ali.service;

import uz.ali.enums.ProfileStatus;
import uz.ali.model.Profile;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static uz.ali.container.CompoundContainer.profileRepository;

public class ProfileService {

    public void addProfile(Profile profile) {
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


    public void searchProfile(String searchTerm) {
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
                profileList = profileRepository.searchProfile(searchTerm);
            }
        } else {
            profileList = profileRepository.searchProfile(searchTerm);
        }

        if (!profileList.isEmpty()) {
            printProfileList(profileList);
        } else {
            System.out.println("No matching profiles found.");
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }


    public void getProfileList() {
        printProfileList(profileRepository.getAllProfiles());
    }

    public void printProfileList(List<Profile> profileList) {
        if (profileList.isEmpty()) {
            System.out.println("No Profiles available.");
        } else {
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("| Id | Name           | Surname        | Login          | Phone        | Status       | Role         | Created Date               |");
            System.out.println("---------------------------------------------------------------------------------");

            for (Profile profile : profileList) {
                String formattedProfile = String.format("| %-3d| %-15s| %-15s| %-15s| %-13s| %-13s| %-13s| %-27s|",
                        profile.getId(), profile.getName(), profile.getSurname(), profile.getLogin(),
                        profile.getPhone(), profile.getProfileStatus(), profile.getProfileRole(), profile.getCreatedDate());
                System.out.println(formattedProfile);
            }
            System.out.println("---------------------------------------------------------------------------------");
        }
    }


}
