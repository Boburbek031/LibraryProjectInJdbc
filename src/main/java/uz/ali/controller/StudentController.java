package uz.ali.controller;

import static uz.ali.util.Menus.displayStudentMenu;
import static uz.ali.util.ScannerUtil.getAction;
import static uz.ali.container.CompoundContainer.*;


public class StudentController {
    // password: Alish0661##
    // login: alish

    public void start() {
        studentBookService.alertStudentToReturnTakenBooks();
        boolean startLoop = true;
        while (startLoop) {
            displayStudentMenu();
            switch (getAction()) {
                case 1:
                    bookService.getBookList();
                    break;
                case 2:
                    bookService.searchBook();
                    break;
                case 3:
                    studentBookService.takeBook();
                    break;
                case 4:
                    studentBookService.returnBook();
                    break;
                case 5:
                    studentBookService.booksOnHand();
                    break;
                case 6:
                    studentBookService.takenBooksHistory();
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
