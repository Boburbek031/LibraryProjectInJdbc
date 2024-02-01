package uz.ali.controller;

import static uz.ali.container.CompoundContainer.*;
import static uz.ali.util.ScannerUtil.getAction;


public class StaffController {

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
        System.out.println("\n\t\t **************** Staff Menu ****************");
        System.out.println("1. Book List");
        System.out.println("2. Student");
        System.out.println("0. Exit");
    }

}
