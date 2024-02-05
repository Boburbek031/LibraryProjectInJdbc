package uz.ali.service;

import uz.ali.model.Book;
import uz.ali.model.StudentBook;

import java.time.LocalDateTime;
import java.util.List;

import static uz.ali.container.CompoundContainer.*;

public class BookService {


    public void addBook(Book book) {
        if (categoryRepository.isCategoryExistsById(book.getCategoryId())) {
            book.setCreatedDate(LocalDateTime.now());
            book.setVisible(true);
            if (bookRepository.saveBook(book) == 1) {
                System.out.println("Book is successfully saved.");
            } else {
                System.out.println("Error in saving Book!");
            }
        } else {
            System.out.println("Category not found.");
        }
    }

    public boolean isBookExistsById(Integer bookId) {
        return bookRepository.isBookExistsById(bookId);
    }

    public void updateBook(Book bookToUpdate) {
        if (bookRepository.updateBook(bookToUpdate) > 0) {
            System.out.println("Book is successfully updated!");
        } else {
            System.out.println("Error in updating!");
        }
    }

    public Book getBookById(int bookId) {
        return bookRepository.getBookById(bookId);
    }

    public void getBookList() {
        printBookList(bookRepository.getBookList());
    }

    public void searchBook(String searchTerm) {
        List<Book> bookList = bookRepository.searchBook(searchTerm);
        if (!bookList.isEmpty()) {
            printBookList(bookList);
        } else {
            System.out.println("No matching contacts found.");
        }
    }

    public void deleteBookById(Integer bookId) {
        if (bookRepository.deleteBookById(bookId) > 0) {
            System.out.println("Book is successfully deleted!");
        } else {
            System.out.println("Book not found!");
        }
    }

    public void bookHistoryById(int bookId) {
        if (!isBookExistsById(bookId)) {
            System.out.println("There is no book with such ID: " + bookId);
        } else {
            printBookHistory(studentBookRepository.bookHistoryById(bookId));
        }
    }

    public void booksByCategoryId(Integer categoryId) {
        while (!categoryService.isCategoryExistsById(categoryId)) {
            System.out.print("Please enter a valid category ID: ");
            while (!scannerNum.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scannerNum.next(); // Clear the invalid input
            }
            categoryId = scannerNum.nextInt();
        }
        printBookByCategoryId(bookRepository.getAllBooksByCategoryId(categoryId));
    }


    public void printBookByCategoryId(List<Book> bookList) {
        if (bookList.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("| Book Id | Author                   | Title               | Publish Date |");
            System.out.println("---------------------------------------------------------------------------");

            for (Book book : bookList) {
                String formattedContact = String.format("| %-8s| %-25s| %-20s| %-13s|",
                        book.getId(), book.getAuthor(), book.getTitle(),
                        book.getPublishDate());
                System.out.println(formattedContact);
            }
            System.out.println("---------------------------------------------------------------------------");
        }
    }

    public void printBookList(List<Book> bookList) {
        if (bookList.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("-------------------------------------------------------------------------------------------------------------------");
            System.out.println("| Id      | Category name            | Author                   | Title               | Created Date              |");
            System.out.println("-------------------------------------------------------------------------------------------------------------------");

            for (Book book : bookList) {
                String formattedContact = String.format("| %-8s| %-25s| %-25s| %-20s| %-15s|",
                        book.getId(), book.getCategory().getName(), book.getAuthor(), book.getTitle(),
                        book.getCreatedDate());
                System.out.println(formattedContact);
            }
            System.out.println("-------------------------------------------------------------------------------------------------------------------");
        }
    }

    public void printBookHistory(List<StudentBook> bookHistoryList) {
        if (bookHistoryList.isEmpty()) {
            System.out.println("No book's history available.");
        } else {
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("| Status    | Taken Date                 |  Returned Date             |  Student Id    |  Student name       |  Student surname    | Student phone |");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

            for (StudentBook studentBook : bookHistoryList) {
                String formattedContact = String.format("| %-10s| %-27s| %-27s| %-15s| %-20s| %-20s| %-14s|",
                        studentBook.getStatus(), studentBook.getCreatedDate(), studentBook.getReturnedDate(),
                        studentBook.getStudentProfile().getId(), studentBook.getStudentProfile().getName(),
                        studentBook.getStudentProfile().getSurname(), studentBook.getStudentProfile().getPhone());
                System.out.println(formattedContact);
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }


}
