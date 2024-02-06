package uz.ali.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static uz.ali.container.CompoundContainer.scannerStr;

public class ValidateInputs {


    public static String getNonEmptyInput(String message) {
        scannerStr = new Scanner(System.in);
        String input;
        do {
            System.out.print(message);
            input = scannerStr.nextLine().trim();
        } while (input.length() < 3 || input.isBlank());
        return input;
    }

    public static int getNonEmptyInputNumber(String message) {
        scannerStr = new Scanner(System.in);
        String input;
        do {
            System.out.print(message);
            input = scannerStr.nextLine().trim();
        } while (input.isBlank() || !checkIfNumber(input));
        return Integer.parseInt(input);
    }

    public static String getValidDateInput() {
        scannerStr = new Scanner(System.in);
        String publishDate;
        do {
            System.out.print("Enter published date (yyyy-MM-dd): ");
            publishDate = scannerStr.nextLine();
        } while (publishDate.isEmpty() || !isValidDateFormat(publishDate));
        return publishDate;
    }

    public static boolean checkIfNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidDateFormat(String dateToValidate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(dateToValidate, formatter);
            return true; // Date is in the correct format
        } catch (DateTimeParseException e) {
            return false; // Date format is invalid
        }
    }

    public static boolean isValidLogin(String login) {
        return login.length() >= 5;
    }

    public static boolean isValidPassword(String password) {
        // Password should be at least 6 characters long and contain at least one uppercase, one lowercase, one digit, and one special symbol
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$";
        return password.matches(regex);
    }

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


    public static String roleChecker(String message) {
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

    public static String getValidPhoneNumber() {
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
