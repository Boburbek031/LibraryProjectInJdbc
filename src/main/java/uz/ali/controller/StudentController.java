package uz.ali.controller;

import java.util.Scanner;

import static uz.ali.util.ScannerUtil.getAction;
import static uz.ali.container.CompoundContainer.*;


public class StudentController {
    // password: Alish0661##
    // login: alish

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            showMenu();
            switch (getAction()) {
                case 1:
                    bookService.getBookList();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

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
        System.out.println("\n\t\t **************** Student Menu ****************");
        System.out.println("1. Book List");
        System.out.println("2. Search");
        System.out.println("3. Take book");
        System.out.println("4. Return book");
        System.out.println("5. Books on hand");
        System.out.println("6. Get history of books");
        System.out.println("0. Exit");
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

}
