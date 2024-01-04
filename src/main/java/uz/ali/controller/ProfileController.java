package uz.ali.controller;

import uz.ali.model.Book;

import java.time.LocalDate;
import java.util.Scanner;

import static uz.ali.container.CompoundContainer.*;

public class ProfileController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            showMenu();
            switch (getAction()) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

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
        System.out.println("1. Profile List");
        System.out.println("2. Search Profile");
        System.out.println("3. Add Profile");
        System.out.println("4. Change Profile status");
        System.out.println("0. Exit");
    }


    private void getBookList() {
        bookService.getBookList();
    }

 /*   public void searchBook() {
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

    public String getNonEmptyInput(String message) {
        scannerStr = new Scanner(System.in);
        String input;
        do {
            System.out.print(message);
            input = scannerStr.nextLine().trim();
        } while (input.length() < 3 || input.isBlank());
        return input;
    }

    */

    public boolean checkIfNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
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
}
