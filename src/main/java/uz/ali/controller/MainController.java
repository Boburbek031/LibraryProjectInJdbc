package uz.ali.controller;

import static uz.ali.container.CompoundContainer.*;
import static uz.ali.util.Menus.displayMainMenu;
import static uz.ali.util.ScannerUtil.getAction;

public class MainController {


    public void start() {

        tableRepository.createTables();
        initService.initAdmin();
        initService.initStudent();

        boolean startLoop = true;
        while (startLoop) {
            displayMainMenu();
            switch (getAction()) {
                case 1:
                    bookService.getBookList();
                    break;
                case 2:
                    bookService.searchBook();
                    break;
                case 3:
                    bookService.booksByCategoryId();
                    break;
                case 4:
                    authService.login();
                    break;
                case 5:
                    authService.registration();
                    break;
                case 0:
                    startLoop = false;
                    break;
                default:
                    System.out.println("\n Please, choose one of the following menus below!");
            }
        }
    }

}
