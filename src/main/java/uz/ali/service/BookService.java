package uz.ali.service;

import uz.ali.model.Book;
import uz.ali.model.StudentBook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static uz.ali.container.CompoundContainer.*;
import static uz.ali.util.Menus.displayUpdateBookFieldsMenu;
import static uz.ali.util.ValidateInputs.*;

public class BookService {


    public void addBook() {
        String title = getNonEmptyInput("Enter title (at least 3 characters): ");
        String author = getNonEmptyInput("Enter author (at least 3 characters): ");
        int availableDay = getNonEmptyInputNumber("Enter available day: ");
        int categoryId = getNonEmptyInputNumber("Enter Category Id: ");
        String publishDate = getValidDateInput(); // 2024-08-31
        Book book = new Book(title, author, categoryId, LocalDate.parse(publishDate), availableDay);
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

    public void updateBook() {
        System.out.print("Enter book ID that you want to update: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int bookId = scannerNum.nextInt();
        if (isBookExistsById(bookId)) {
            Book bookToUpdate = getBookById(bookId);
            displayBookDetails(bookToUpdate);
            if (updateBookFields(bookToUpdate) != 0) {
                if (bookRepository.updateBook(bookToUpdate) > 0) {
                    System.out.println("Book is successfully updated!");
                } else {
                    System.out.println("Error in updating!");
                }
            }
        } else {
            System.out.println("Book not found!");
        }
    }

    public Book getBookById(int bookId) {
        return bookRepository.getBookById(bookId);
    }

    public void getBookList() {
        printBookList(bookRepository.getBookList());
    }

    public void searchBook() {
        String searchTerm;
        do {
            System.out.print("Enter search term (Book's title or author's name): ");
            scannerStr = new Scanner(System.in);
            searchTerm = scannerStr.nextLine();
        } while (searchTerm.isBlank());
        List<Book> bookList = bookRepository.searchBook(searchTerm);
        if (!bookList.isEmpty()) {
            printBookList(bookList);
        } else {
            System.out.println("No matching books are found.");
        }
    }

    public void deleteBookById() {
        System.out.print("Enter book ID that you want to delete: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int bookId = scannerNum.nextInt();
        if (bookRepository.deleteBookById(bookId) > 0) {
            System.out.println("Book is successfully deleted!");
        } else {
            System.out.println("Book not found!");
        }
    }

    public void bookHistoryById() {
        System.out.print("Enter book ID that you want to see the history: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int bookId = scannerNum.nextInt();
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


    private void displayBookDetails(Book bookToUpdate) {
        System.out.println("\nTitle: " + bookToUpdate.getTitle());
        System.out.println("Author: " + bookToUpdate.getAuthor());
        System.out.println("Publish Date: " + bookToUpdate.getPublishDate());
        System.out.println("Available Day: " + bookToUpdate.getAvailableDay());
    }

    private int updateBookFields(Book bookToUpdate) {
        displayUpdateBookFieldsMenu();
        int chosenField = getActionForUpdate(bookToUpdate); // Get the field choice

        switch (chosenField) {
            case 0:
                System.out.println("Exiting field update...");
                return 0;
            case 1:
                String newTitle = getNonEmptyInput("Enter new title (at least 3 characters): ");
                bookToUpdate.setTitle(newTitle);
                break;
            case 2:
                String newAuthor = getNonEmptyInput("Enter new author (at least 3 characters): ");
                bookToUpdate.setAuthor(newAuthor);
                break;
            case 3:
                String newPublishDate = getValidDateInput(); // Validate date format
                bookToUpdate.setPublishDate(LocalDate.parse(newPublishDate));
                break;
            case 4:
                int newAvailableDay = getNonEmptyInputNumber("Enter new available day: ");
                bookToUpdate.setAvailableDay(newAvailableDay);
                break;
            default:
                System.out.println("\nInvalid choice!");
                return 0;
        }
        return 1;
    }


    public int getActionForUpdate(Book bookToUpdate) {
        while (true) {
            System.out.print("Choose one of the actions above: ");
            String chosenMenu = scannerStr.next();
            if (checkIfNumber(chosenMenu)) {
                return Integer.parseInt(chosenMenu);
            } else {
                updateBookFields(bookToUpdate);
                System.out.println("\n Please, choose one of the following menus above!");
            }
        }
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
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("| Id      | Category name            | Author                   | Title                            | Created Date              |");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------");

            for (Book book : bookList) {
                String formattedContact = String.format("| %-8s| %-25s| %-25s| %-33s| %-15s|",
                        book.getId(), book.getCategory().getName(), book.getAuthor(), book.getTitle(),
                        book.getCreatedDate());
                System.out.println(formattedContact);
            }
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
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
