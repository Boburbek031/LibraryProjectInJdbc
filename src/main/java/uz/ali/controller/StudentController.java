package uz.ali.controller;

import static uz.ali.util.ScannerUtil.getAction;


public class StudentController {
    // password: 0203
    // login: alish

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

}
