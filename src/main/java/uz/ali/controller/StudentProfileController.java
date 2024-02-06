package uz.ali.controller;

import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;

import java.util.Scanner;

import static uz.ali.container.CompoundContainer.*;
import static uz.ali.util.ScannerUtil.getAction;

public class StudentProfileController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            showMenu();
            switch (getAction()) {
                case 1:
                    profileService.getStudentProfileList();
                    break;
                case 2:
                    profileService.search();
                    break;
                case 3:
                    blockStudentProfile();
                    break;
                case 4:
                    activateStudentProfile();
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

    private void blockStudentProfile() {
        System.out.print("Enter Student Profile ID: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int id = scannerNum.nextInt();
        profileService.changeStudentProfileStatus(id, ProfileStatus.BLOCK);
    }

    private void activateStudentProfile() {
        System.out.print("Enter Student Profile ID: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int id = scannerNum.nextInt();
        profileService.changeStudentProfileStatus(id, ProfileStatus.ACTIVE);
    }


    private void showMenu() {
        System.out.println("\n\t\t **************** Student Menu ****************");
        System.out.println("1. Student list");
        System.out.println("2. Search student");
        System.out.println("3. Block student");
        System.out.println("4. Activate student");
        System.out.println("0. Exit");
    }

}
