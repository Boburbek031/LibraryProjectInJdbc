package uz.ali.service;

import uz.ali.enums.StudentBookStatus;
import uz.ali.model.StudentBook;

import java.time.LocalDateTime;
import java.util.List;

import static uz.ali.container.CompoundContainer.*;

public class StudentBookService {


    public void takeBook(Integer bookId) {
        if (!bookService.isBookExistsById(bookId)) {
            System.out.println("Enter a valid book ID!");
            return;
        }

        List<StudentBook> studentBooks = studentBookRepository.checkStudentBookAlreadyTakenOrNot(currentProfile.getId());
        if (studentBooks.size() >= 5) {
            System.out.println("You have reached the limit of the taken books (5). To take a book, please return one of them first!");
            return;
        }
        for (StudentBook studentBook : studentBooks) {
            if (studentBook.getBookId().equals(bookId) && studentBook.getStatus().name().equals("TAKEN")) {
                System.out.println("You have already taken the book with this id: " + studentBook.getBookId());
                return;
            }
        }

        StudentBook studentBook = new StudentBook(currentProfile.getId(), bookId, LocalDateTime.now(), StudentBookStatus.TAKEN);
        if ((studentBookRepository.save(studentBook) == 1)) {
            System.out.println("Student Book is successfully taken.");
        } else {
            System.out.println("Error in taking Student Book!");
        }
    }

    public void booksOnHand() {
        printStudentBooksOnHand(studentBookRepository.studentBookOnHand(currentProfile.getId()));
    }

    public void printStudentBooksOnHand(List<StudentBook> bookList) {
        if (bookList.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("------------------------------------------------------------------------------------------------------");
            System.out.println("| Id        | Category Id  | Category name       | Book Id   | Author                   | Title               | Created Date              |");
            System.out.println("------------------------------------------------------------------------------------------------------");

            for (StudentBook studentBook : bookList) {
                String formattedContact = String.format("| %-10s| %-13s| %-20s| %-10s| %-25s| %-20s| %-13s|",
                        studentBook.getId(), studentBook.getBook().getCategory().getId(), studentBook.getBook().getCategory().getName(),
                        studentBook.getBookId(), studentBook.getBook().getAuthor(), studentBook.getBook().getTitle(),
                        studentBook.getCreatedDate());
                System.out.println(formattedContact);
            }
            System.out.println("------------------------------------------------------------------------------------------------------");
        }
    }

}
