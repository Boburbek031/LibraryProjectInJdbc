package uz.ali.controller;

import uz.ali.model.Profile;
import uz.ali.util.MD5Util;
import uz.ali.util.MenuOptions;

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


        tableRepository.createTable();
        initService.initAdmin();
        initService.initStudent();

        boolean startLoop = true;
        while (startLoop) {
            showMenu();
            switch (getAction()) {
                case MenuOptions.BOOK_LIST:
                    System.out.println("BookList");
                    break;
                case MenuOptions.SEARCH:
                    System.out.println("Search");
                    break;
                case MenuOptions.CATEGORIES:
                    System.out.println("Category");
                    break;
                case MenuOptions.LOGIN:
                    login();
                    break;
                case MenuOptions.REGISTRATION:
                    registration();
                    break;
                case MenuOptions.EXIT:
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
        String name = getNonEmptyInput("Enter name: ");
        String surname = getNonEmptyInput("Enter surname: ");
        String phone = getValidPhoneNumber();
        String login = getNonEmptyInput("Enter login: ");
        String password = getNonEmptyInput("Enter password: ");
        Profile profile = new Profile(name, surname, phone, login, MD5Util.encode(password));
        authService.registration(profile);
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
            if (input.length() <= 2 || input.isBlank()) {
                System.out.println("\nInput cannot be empty and length must be over 2!");
            }
        } while (input.length() <= 2 || input.isBlank());
        return input;
    }

    private String getValidPhoneNumber() {
        scannerStr = new Scanner(System.in);
        String phoneNumber;
        do {
            System.out.print("Enter phone number: ");
            phoneNumber = scannerStr.nextLine();
            if (isValidPhoneNumber(phoneNumber)) {
                System.out.println("Please enter a valid phone number!");
            }
        } while (isValidPhoneNumber(phoneNumber));
        return phoneNumber;
    }
}
