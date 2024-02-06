package uz.ali.controller;

import static uz.ali.container.CompoundContainer.profileService;
import static uz.ali.util.Menus.displayProfileMenu;
import static uz.ali.util.ScannerUtil.getAction;

public class ProfileController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            displayProfileMenu();
            switch (getAction()) {
                case 1:
                    profileService.getProfileList();
                    break;
                case 2:
                    profileService.search();
                    break;
                case 3:
                    profileService.addProfile();
                    break;
                case 4:
                    profileService.changeStatus();
                    break;
                case 0:
                    System.out.println("Exit");
                    startLoop = false;
                    break;
                default:
                    System.out.println("\n Please, choose one of the following menus below!");
            }
        }
    }

}
