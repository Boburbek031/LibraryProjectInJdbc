package uz.ali.controller;

import static uz.ali.container.CompoundContainer.*;

public class AdminController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            showMenu();
            switch (getAction()) {
                case 1:
                    bookController.start();
                    break;
                case 2:

                    break;
                case 3:
                    categoryController.start();
                    break;
                case 4:
                    profileController.start();
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
        System.out.println("\n\t\t **************** Admin Menu ****************");
        System.out.println("1. Book List");
        System.out.println("2. Student");
        System.out.println("3. Categories");
        System.out.println("4. Profile");
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
