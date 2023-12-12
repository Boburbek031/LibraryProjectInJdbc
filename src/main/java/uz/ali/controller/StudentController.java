package uz.ali.controller;

import uz.ali.util.MenuOptions;

import static uz.ali.container.CompoundContainer.scannerStr;

public class StudentController {
    // password: 0203
    // login: alish

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            showMenu();
            switch (getAction()) {
                case MenuOptions.BOOK_LIST:

                    break;
                case MenuOptions.SEARCH:

                    break;
                case MenuOptions.TAKE_BOOK:

                    break;
                case MenuOptions.RETURN_BOOK:

                    break;
                case MenuOptions.BOOKS_ON_HAND:

                    break;
                case MenuOptions.GET_HISTORY_OF_BOOKS:

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
        System.out.println("\n\t\t **************** Student Menu ****************");
        System.out.println("1. Book List");
        System.out.println("2. Search");
        System.out.println("3. Take book");
        System.out.println("4. Return book");
        System.out.println("5. Books on hand");
        System.out.println("6. Get history of books");
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
