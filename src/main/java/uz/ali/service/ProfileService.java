package uz.ali.service;

import uz.ali.enums.ProfileRole;
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


    public void search(String searchTerm, ProfileRole... roles) {
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
                profileList = profileRepository.search(searchTerm, roles);
            }
        } else {
            profileList = profileRepository.search(searchTerm, roles);
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


    public void getStudentProfileList() {
        printStudentProfileList(profileRepository.getAllProfiles(ProfileRole.STUDENT));
    }

    public void getProfileList() {
        printProfileList(profileRepository.getAllProfiles(ProfileRole.ADMIN, ProfileRole.STAFF));
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

    public void printStudentProfileList(List<Profile> profileList) {
        if (profileList.isEmpty()) {
            System.out.println("No Profiles available.");
        } else {
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("| Id | Name           | Surname        | Login          | Phone        | Role         | Created Date               |");
            System.out.println("---------------------------------------------------------------------------------");

            for (Profile profile : profileList) {
                String formattedProfile = String.format("| %-3d| %-15s| %-15s| %-15s| %-13s| %-13s| %-27s|",
                        profile.getId(), profile.getName(), profile.getSurname(), profile.getLogin(),
                        profile.getPhone(), profile.getProfileRole(), profile.getCreatedDate());
                System.out.println(formattedProfile);
            }
            System.out.println("---------------------------------------------------------------------------------");
        }
    }


    public void changeStatus(int profileId) {
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

    public void changeStudentProfileStatus(int id, ProfileStatus status) {
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
