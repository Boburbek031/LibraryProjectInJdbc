package uz.ali.service;

import uz.ali.model.Book;

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
            return;
        }
    }


    public void getBookList() {
        printBookList(bookRepository.getBookList());
    }

    /*public void deleteCategoryById(Integer categoryId) {
        if (categoryRepository.deleteCategoryById(categoryId) > 0) {
            System.out.println("Category is successfully deleted!");
        } else {
            System.out.println("Category not found!");
        }
    }*/

    public void printBookList(List<Book> bookList) {
        if (bookList.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("-----------------------------------------------------------------------------------------------------");
            System.out.println("| Id   | Title               | Author              | Category Id | Publish Date | Available Day | Created Date              |");
            System.out.println("-----------------------------------------------------------------------------------------------------");

            for (Book book : bookList) {
                String formattedContact = String.format("| %-5s| %-20s| %-20s| %-12s| %-13s| %-14s| %-23s|",
                        book.getId(), book.getTitle(), book.getAuthor(), book.getCategoryId(),
                        book.getPublishDate(), book.getAvailableDay(), book.getCreatedDate());
                System.out.println(formattedContact);
            }
            System.out.println("-----------------------------------------------------------------------------------------------------");
        }
    }
}
