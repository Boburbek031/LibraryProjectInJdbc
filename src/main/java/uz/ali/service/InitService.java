package uz.ali.service;

import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;
import uz.ali.model.Profile;
import uz.ali.util.MD5Util;

import java.time.LocalDateTime;

import static uz.ali.container.CompoundContainer.profileRepository;

public class InitService {

    // Bu method, o'zi bu class bazada admin bormi yoki yoqmi shun tekshiradi agar bo'lsa bor deydi, bo'masa yaratadi
    public void initAdmin() {
        // login: adminboy, password: adminboy_0661
        if (!profileRepository.isProfileExists("adminboy")) {
            Profile admin = new Profile("admin", "adminov", "adminboy", MD5Util.encode("adminboy_0661"), "998993960661", ProfileStatus.ACTIVE, ProfileRole.ADMIN, LocalDateTime.now());
            profileRepository.addProfile(admin);
        }
    }

    public void initStudent() {
        if (!profileRepository.isProfileExists("test")) {
            Profile student = new Profile("TEST", "TEST", "test_1", MD5Util.encode("test"), "998339060668", ProfileStatus.ACTIVE, ProfileRole.STUDENT, LocalDateTime.now());
            profileRepository.addProfile(student);
        }
    }


}
