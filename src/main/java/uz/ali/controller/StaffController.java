package uz.ali.controller;

import uz.ali.util.MenuOptions;

import static uz.ali.container.CompoundContainer.scannerStr;

public class StaffController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            showMenu();
            switch (getAction()) {
                case MenuOptions.BOOK_LIST:
                    System.out.println("Book List");
                    break;
                case MenuOptions.STUDENT:
                    System.out.println("Student");
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
        System.out.println("\n\t\t **************** Staff Menu ****************");
        System.out.println("1. Book List");
        System.out.println("2. Student");
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


}
