package uz.ali.service;

import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;
import uz.ali.model.Profile;
import uz.ali.repository.ProfileRepository;
import uz.ali.util.MD5Util;

import java.time.LocalDateTime;

public class InitService {

    ProfileRepository profileRepository = new ProfileRepository();

    // Bu method, o'zi bu class bazada admin bormi yoki yoqmi shun tekshiradi agar bo'lsa bor deydi, bo'masa yaratadi
    public void initAdmin() {
        // login: adminboy, password: adminboy_0661
        boolean profileExists = profileRepository.isProfileExists("adminboy");
        if (!profileExists) {
            Profile admin = new Profile("admin", "adminov", "adminboy",MD5Util.encode("adminboy_0661"), "998993960661", ProfileStatus.ACTIVE,
                    ProfileRole.ADMIN, LocalDateTime.now());
            profileRepository.createProfile(admin);
        }
    }


}
