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

}
