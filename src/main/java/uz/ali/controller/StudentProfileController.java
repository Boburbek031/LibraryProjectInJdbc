package uz.ali.controller;

import uz.ali.enums.ProfileStatus;

import static uz.ali.container.CompoundContainer.*;
import static uz.ali.util.Menus.displayStudentProfileMenu;
import static uz.ali.util.ScannerUtil.getAction;

public class StudentProfileController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            displayStudentProfileMenu();
            switch (getAction()) {
                case 1:
                    profileService.getStudentProfileList();
                    break;
                case 2:
                    profileService.search();
                    break;
                case 3:
                    profileService.changeStudentProfileStatus(ProfileStatus.BLOCK);
                    break;
                case 4:
                    profileService.changeStudentProfileStatus(ProfileStatus.ACTIVE);
                    break;
                case 0:
                    startLoop = false;
                    break;
                default:
                    System.out.println("\n Please, choose one of the following menus below!");
            }
        }
    }

}
