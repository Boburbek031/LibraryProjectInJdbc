package uz.ali.controller;

import static uz.ali.container.CompoundContainer.*;
import static uz.ali.util.Menus.displayStaffMenu;
import static uz.ali.util.ScannerUtil.getAction;


public class StaffController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            displayStaffMenu();
            switch (getAction()) {
                case 1:
                    bookController.start();
                    break;
                case 2:
                    studentProfileController.start();
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
