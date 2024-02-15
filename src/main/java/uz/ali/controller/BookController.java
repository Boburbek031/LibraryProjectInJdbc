package uz.ali.controller;

import static uz.ali.container.CompoundContainer.bookService;
import static uz.ali.container.CompoundContainer.studentBookService;
import static uz.ali.util.Menus.displayBookMenu;
import static uz.ali.util.ScannerUtil.getAction;

public class BookController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            displayBookMenu();
            switch (getAction()) {
                case 1:
                    bookService.getBookList();
                    break;
                case 2:
                    bookService.searchBook();
                    break;
                case 3:
                    bookService.saveBook();
                    break;
                case 4:
                    bookService.deleteBookById();
                    break;
                case 5:
                    bookService.updateBook();
                    break;
                case 6:
                    studentBookService.allBooksOnHandsOfStudents();
                    break;
                case 7:
                    bookService.bookHistoryById();
                    break;
                case 8:
                    studentBookService.bestBooks();
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
