package uz.ali.controller;

import uz.ali.model.Book;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static uz.ali.container.CompoundContainer.*;

public class BookController {

    // hamma checking larni service class da qilish kere // update qilasan

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            showMenu();
            switch (getAction()) {
                case 1:
                    getBookList();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    addBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    updateBook();
                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:

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
        System.out.println("\n\t\t **************** Book Menu ****************");
        System.out.println("1. Book List");
        System.out.println("2. Search");
        System.out.println("3. Add book");
        System.out.println("4. Delete book");
        System.out.println("5. Update book");
        System.out.println("6. Books on hand");
        System.out.println("7. Book history");
        System.out.println("8. Best books");
        System.out.println("0. Exit");
    }


    private void getBookList() {
        bookService.getBookList();
    }

    public void searchBook() {
        String searchTerm;
        do {
            System.out.print("Enter search term (Book's title or author's name): ");
            scannerStr = new Scanner(System.in);
            searchTerm = scannerStr.nextLine();
        } while (searchTerm.isBlank());
        bookService.searchBook(searchTerm);
    }

    private void addBook() {
        String title = getNonEmptyInput("Enter title (at least 3 characters): ");
        String author = getNonEmptyInput("Enter author (at least 3 characters): ");
        int availableDay = getNonEmptyInputNumber("Enter available day: ");
        int categoryId = getNonEmptyInputNumber("Enter Category Id: ");
        String publishDate = getValidDateInput(); // 2024-08-31
        bookService.addBook(new Book(title, author, categoryId, LocalDate.parse(publishDate), availableDay));
    }

    private void deleteBook() {
        System.out.print("Enter book ID that you want to delete: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int bookId = scannerNum.nextInt();
        bookService.deleteBookById(bookId);
    }

    private void updateBook() {
        System.out.print("Enter book ID that you want to update: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int bookId = scannerNum.nextInt();

        if (bookService.isBookExistsById(bookId)) {
            Book bookToUpdate = bookService.getBookById(bookId);
            displayBookDetails(bookToUpdate);
            if (updateBookFields(bookToUpdate) != 0) {
                bookService.updateBook(bookToUpdate);
            }
        } else {
            System.out.println("Book not found!");
        }
    }

    private void displayBookDetails(Book bookToUpdate) {
        System.out.println("\nTitle: " + bookToUpdate.getTitle());
        System.out.println("Author: " + bookToUpdate.getAuthor());
        System.out.println("Publish Date: " + bookToUpdate.getPublishDate());
        System.out.println("Available Day: " + bookToUpdate.getAvailableDay());
    }

    private int updateBookFields(Book bookToUpdate) {
        System.out.println("\nSelect the field(s) you want to update: ");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Publish Date");
        System.out.println("4. Available Day");
        System.out.println("0. Exit");

        int chosenField = getActionForUpdate(bookToUpdate); // Get the field choice

        switch (chosenField) {
            case 0:
                System.out.println("Exiting field update...");
                return 0;
            case 1:
                String newTitle = getNonEmptyInput("Enter new title (at least 3 characters): ");
                bookToUpdate.setTitle(newTitle);
                break;
            case 2:
                String newAuthor = getNonEmptyInput("Enter new author (at least 3 characters): ");
                bookToUpdate.setAuthor(newAuthor);
                break;
            case 3:
                String newPublishDate = getValidDateInput(); // Validate date format
                bookToUpdate.setPublishDate(LocalDate.parse(newPublishDate));
                break;
            case 4:
                int newAvailableDay = getNonEmptyInputNumber("Enter new available day: ");
                bookToUpdate.setAvailableDay(newAvailableDay);
                break;
            default:
                System.out.println("\nInvalid choice!");
                return 0;
        }
        return 1;
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

    public int getNonEmptyInputNumber(String message) {
        scannerStr = new Scanner(System.in);
        String input;
        do {
            System.out.print(message);
            input = scannerStr.nextLine().trim();
        } while (input.isBlank() || !checkIfNumber(input));
        return Integer.parseInt(input);
    }

    public int getActionForUpdate(Book bookToUpdate) {
        while (true) {
            System.out.print("Choose one of the actions above: ");
            String chosenMenu = scannerStr.next();
            if (checkIfNumber(chosenMenu)) {
                return Integer.parseInt(chosenMenu);
            } else {
                updateBookFields(bookToUpdate);
                System.out.println("\n Please, choose one of the following menus above!");
            }
        }
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

    public boolean isValidDateFormat(String dateToValidate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(dateToValidate, formatter);
            return true; // Date is in the correct format
        } catch (DateTimeParseException e) {
            return false; // Date format is invalid
        }
    }

    public String getValidDateInput() {
        scannerStr = new Scanner(System.in);
        String publishDate;
        do {
            System.out.print("Enter published date (yyyy-MM-dd): ");
            publishDate = scannerStr.nextLine();
        } while (publishDate.isEmpty() || !isValidDateFormat(publishDate));
        return publishDate;
    }

}
