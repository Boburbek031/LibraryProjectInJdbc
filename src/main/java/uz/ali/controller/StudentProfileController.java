package uz.ali.controller;

import uz.ali.enums.ProfileRole;
import uz.ali.enums.ProfileStatus;

import java.util.Scanner;

import static uz.ali.container.CompoundContainer.*;

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
                    search();
                    break;
                case 3:
                    blockStudentProfile();
                    break;
                case 4:
                    activateStudentProfile();
                    break;
                case 5:

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

    public void search() {
        String searchTerm;
        do {
            System.out.print("Enter search term (Student profile's id, name, surname, login, or phone): ");
            scannerStr = new Scanner(System.in);
            searchTerm = scannerStr.nextLine();
        } while (searchTerm.isBlank());
        profileService.search(searchTerm, ProfileRole.STUDENT);
    }


    private void showMenu() {
        System.out.println("\n\t\t **************** Student Menu ****************");
        System.out.println("1. Student list");
        System.out.println("2. Search student");
        System.out.println("3. Block student");
        System.out.println("4. Activate student");
        System.out.println("5. Student by Book");
        System.out.println("0. Exit");
    }


    public int getAction() {
        while (true) {
            System.out.print("Choose one of the actions above: ");
            String chosenMenu = scannerStr.next();
            if (checkIfNumber(chosenMenu)) {
                return Integer.parseInt(chosenMenu);
            } else {
                showMenu();
                System.out.println("\n Please, choose one of the following menus above!");
            }
        }
    }

    public boolean checkIfNumber(String input) {
        try {
            // Attempt to parse the input as an integer
            Integer.parseInt(input);
            return true; // If successful, it's a number
        } catch (NumberFormatException e) {
            return false; // If an exception occurs, it's not a number
        }
    }


}
