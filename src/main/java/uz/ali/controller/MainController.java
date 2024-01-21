package uz.ali.controller;

import uz.ali.model.Profile;
import uz.ali.util.MD5Util;

import static uz.ali.container.CompoundContainer.*;

import java.util.Scanner;

public class MainController {

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


        tableRepository.createTables();
        initService.initAdmin();
        initService.initStudent();

        boolean startLoop = true;
        while (startLoop) {
            showMenu();
            switch (getAction()) {
                case 1:
                    System.out.println("BookList");
                    bookService.getBookList();
                    break;
                case 2:
                    System.out.println("Search");
                    break;
                case 3:
                    System.out.println("Category");
                    break;
                case 4:
                    login();
                    break;
                case 5:
                    registration();
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
        System.out.println("\n\t\t **************** Main Menu ****************");
        System.out.println("1. Book List");
        System.out.println("2. Search");
        System.out.println("3. Categories");
        System.out.println("4. Login");
        System.out.println("5. Registration");
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
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void registration() {
        scannerStr = new Scanner(System.in);
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

        Profile profile = new Profile(name, surname, phone, login, MD5Util.encode(password));
        authService.registration(profile);
    }

    private boolean isValidLogin(String login) {
        return login.length() >= 5;
    }

    private boolean isValidPassword(String password) {
        // Password should be at least 6 characters long and contain at least one uppercase, one lowercase, one digit, and one special symbol
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$";
        return password.matches(regex);
    }

    private void login() {
        String login = getNonEmptyInput("Enter login: ");
        String password = getNonEmptyInput("Enter password: ");
        authService.login(login, password);
    }

    public String getNonEmptyInput(String message) {
        scannerStr = new Scanner(System.in);
        String input;
        do {
            System.out.print(message);
            input = scannerStr.nextLine().trim();
        } while (input.length() < 2 || input.isBlank());
        return input;
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
}
