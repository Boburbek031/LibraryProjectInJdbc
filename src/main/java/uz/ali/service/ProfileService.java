package uz.ali.service;

import uz.ali.enums.ProfileStatus;
import uz.ali.model.Category;
import uz.ali.model.Profile;

import java.time.LocalDateTime;
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


    public void getProfileList() {
        printProfileList(profileRepository.getAllProfiles());
    }

    public void printProfileList(List<Profile> profileList) {
        if (profileList.isEmpty()) {
            System.out.println("No Profiles available.");
        } else {
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("| Id | Name    | Surname | Login  | Phone    | Status       | Role         | Created Date             |");
            System.out.println("---------------------------------------------------------------------------------");

            for (Profile profile : profileList) {
                String formattedProfile = String.format("| %-3d| %-8s| %-9s| %-7s| %-9s| %-13s| %-13s| %-27s|",
                        profile.getId(), profile.getName(), profile.getSurname(), profile.getLogin(),
                        profile.getPhone(), profile.getProfileStatus(), profile.getProfileRole(), profile.getCreatedDate());
                System.out.println(formattedProfile);
            }
            System.out.println("---------------------------------------------------------------------------------");
        }
    }


}
