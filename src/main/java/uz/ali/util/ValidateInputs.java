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


}
