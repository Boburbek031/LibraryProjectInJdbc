package uz.ali.controller;

import static uz.ali.container.CompoundContainer.*;
import static uz.ali.util.Menus.showAdminMenu;
import static uz.ali.util.ScannerUtil.getAction;


public class AdminController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            showAdminMenu();
            switch (getAction()) {
                case 1:
                    bookController.start();
                    break;
                case 2:
                    studentProfileController.start();
                    break;
                case 3:
                    categoryController.start();
                    break;
                case 4:
                    profileController.start();
                    break;
                case 0:
                    System.out.println("Exit...");
                    startLoop = false;
                    break;
                default:
                    System.out.println("\n Please, choose one of the following menus below!");
            }
        }
    }
}
