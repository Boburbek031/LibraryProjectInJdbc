package uz.ali.controller;

import uz.ali.db.DatabaseUtil;
import uz.ali.repository.TableRepository;
import uz.ali.service.AuthService;
import uz.ali.service.InitService;

import java.util.Scanner;

public class MainController {

    TableRepository tableRepository = new TableRepository();
    InitService initService = new InitService();
    AuthService authService = new AuthService();
    private Scanner scannerNum = new Scanner(System.in);
    private Scanner scannerStr = new Scanner(System.in);

    public void start() {

        tableRepository.createTable();
        initService.initAdmin();

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
                    login();
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

    private void login() {
        System.out.print("Enter login: ");
        String login = scannerStr.next();
        System.out.print("Enter password: ");
        String password = scannerStr.next();

        authService.login(login, password);


    }


    private void showMenu() {
        System.out.println("\n\t\t **************** Main menu ****************");
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
            // Attempt to parse the input as an integer
            Integer.parseInt(input);
            return true; // If successful, it's a number
        } catch (NumberFormatException e) {
            return false; // If an exception occurs, it's not a number
        }
    }
}
