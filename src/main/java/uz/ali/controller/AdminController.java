package uz.ali.controller;

import uz.ali.util.Menus;

import static uz.ali.container.CompoundContainer.*;
import static uz.ali.util.ScannerUtil.getAction;

public class AdminController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            Menus.showAdminMenu();
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
