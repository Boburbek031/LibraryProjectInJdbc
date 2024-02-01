package uz.ali.controller;

import uz.ali.enums.ProfileRole;
import uz.ali.model.Profile;
import uz.ali.util.MD5Util;
import static uz.ali.util.ScannerUtil.getAction;


import java.util.Scanner;

import static uz.ali.container.CompoundContainer.*;

public class ProfileController {

    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Basic validation: Check if the phone number is not null and not empty
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return true;
        }

        // Remove non-digit characters from the phone number
        String numericPhoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        String trimPhoneNumber = numericPhoneNumber.trim();

        // Check if the phone number starts with the country code 998 and has a length of 12
        return !trimPhoneNumber.startsWith("998") || trimPhoneNumber.length() != 12;
    }

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            showMenu();
            switch (getAction()) {
                case 1:
                    profileService.getProfileList();
                    break;
                case 2:
                    searchProfile();
                    break;
                case 3:
                    addProfile();
                    break;
                case 4:
                    changeStatus();
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

    private void changeStatus() {
        System.out.print("Enter profile ID: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int profileId = scannerNum.nextInt();
        profileService.changeStatus(profileId);
    }

    public void addProfile() {
        String name = getNonEmptyInput("Enter name (at least 2 characters): ");
        String surname = getNonEmptyInput("Enter surname (at least 2 characters): ");
        String phone = getValidPhoneNumber();
        String login;
        String password;
        do {
            login = getNonEmptyInput("Enter login (at least 5 characters): ");
        } while (!isValidLogin(login));

        do {
            password = getNonEmptyInput("Enter password (at least 6 characters with 1 uppercase, 1 lowercase, 1 digit, and 1 special symbol): ");
        } while (!isValidPassword(password));

        String role = roleChecker("Enter role (STAFF OR ADMIN): ");
        Profile profile = new Profile(name, surname, phone, login, MD5Util.encode(password), ProfileRole.valueOf(role));

        profileService.addProfile(profile);


    }

    private String getValidPhoneNumber() {
        scannerStr = new Scanner(System.in);
        String phoneNumber;
        do {
            System.out.print("Enter phone number (998XXX1234567): ");
            phoneNumber = scannerStr.nextLine();
            if (isValidPhoneNumber(phoneNumber)) {
                System.out.println("Please enter a valid phone number!");
            }
        } while (isValidPhoneNumber(phoneNumber));
        return phoneNumber;
    }

    private boolean isValidLogin(String login) {
        return login.length() >= 5;
    }

    private boolean isValidPassword(String password) {
        // Password should be at least 6 characters long and contain at least one uppercase, one lowercase, one digit, and one special symbol
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$";
        return password.matches(regex);
    }


    private void showMenu() {
        System.out.println("\n\t\t **************** Book Menu ****************");
        System.out.println("1. Profile List");
        System.out.println("2. Search Profile");
        System.out.println("3. Add Profile");
        System.out.println("4. Change Profile status");
        System.out.println("0. Exit");
    }


    public void searchProfile() {
        String searchTerm;
        do {
            System.out.print("Enter search term (Profile's id, name, surname, login, or phone): ");
            scannerStr = new Scanner(System.in);
            searchTerm = scannerStr.nextLine();
        } while (searchTerm.isBlank());
        profileService.search(searchTerm, ProfileRole.ADMIN, ProfileRole.STAFF);
    }

    public String getNonEmptyInput(String message) {
        scannerStr = new Scanner(System.in);
        String input;
        do {
            System.out.print(message);
            input = scannerStr.nextLine().trim();
        } while (input.length() < 3 || input.isBlank());
        return input;
    }

    public String roleChecker(String message) {
        scannerStr = new Scanner(System.in);
        String input;
        while (true) {
            System.out.print(message);
            input = scannerStr.nextLine().trim().toUpperCase();

            if (input.length() >= 5 && (input.equals("STAFF") || input.equals("ADMIN"))) {
                return input;
            }
            System.out.println("Invalid input. Please enter STAFF or ADMIN.");  // Provide clearer error message
        }
    }
}
