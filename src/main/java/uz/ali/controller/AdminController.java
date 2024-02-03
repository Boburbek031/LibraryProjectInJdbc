package uz.ali.controller;

import static uz.ali.container.CompoundContainer.*;
import static uz.ali.util.ScannerUtil.getAction;

public class AdminController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            showMenu();
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
                    System.out.println("Exit");
                    startLoop = false;
                    break;
                default:
                    System.out.println("\n Please, choose one of the following menus below!");
            }
        }
    }


    private void showMenu() {
        System.out.println("\n\t\t **************** Admin Menu ****************");
        System.out.println("1. Book");
        System.out.println("2. Student");
        System.out.println("3. Categories");
        System.out.println("4. Profile");
        System.out.println("0. Exit");
    }

}
