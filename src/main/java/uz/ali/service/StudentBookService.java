package uz.ali.service;

import uz.ali.enums.StudentBookStatus;
import uz.ali.model.Book;
import uz.ali.model.StudentBook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static uz.ali.container.CompoundContainer.*;

public class StudentBookService {


    public void takeBook(Integer bookId) {
        if (!bookService.isBookExistsById(bookId)) {
            System.out.println("Enter a valid book ID!");
            return;
        }
        Book bookById = bookService.getBookById(bookId);
        if (bookById == null){
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

        StudentBook studentBook = new StudentBook();
        studentBook.setStudentId(currentProfile.getId());
        studentBook.setBookId(bookId);
        studentBook.setCreatedDate(LocalDateTime.now());
        studentBook.setStatus(StudentBookStatus.TAKEN);
        studentBook.setDeadlineDate(LocalDate.now().plusDays(bookById.getAvailableDay()));


        if ((studentBookRepository.save(studentBook) == 1)) {
            System.out.println("Student Book is successfully taken.");
        } else {
            System.out.println("Error in taking Student Book!");
        }
    }

    public void returnBook(Integer bookId) {
       /*  // bu yerda hozi biz ikkita ish qilyapmiz, yani database ga 2 martta so'rov jo'natyapmiz, shuni bittada qilsa ham bo'ladi
       StudentBook studentBook = studentBookRepository.getStudentBook(bookId, currentProfile.getId());
        if (studentBook != null) {
            if (studentBookRepository.returnStudentBook(studentBook.getId()) != 0) {
                System.out.println("Student Book is successfully returned.");
            } else {
                System.out.println("Error in returning Student book.");
            }
        } else {
            System.out.println("Student did not take the book with such ID: " + bookId + ". ERROR!!!");
        }*/
        if (studentBookRepository.returnStudentBook(bookId, currentProfile.getId()) != 0) {
            System.out.println("Student Book is successfully returned.");
        } else {
            System.out.println("Error in returning Student book or Student did not take the book with such ID: " + bookId);
        }
    }

    public void booksOnHand() {
        printStudentBooksOnHand(studentBookRepository.studentBookOnHandAndBookHistory(currentProfile.getId(), StudentBookStatus.TAKEN), StudentBookStatus.TAKEN);
    }


    public void allBooksOnHandsOfStudents() {
        printAllBooksOnHandOfStudents(studentBookRepository.allBooksOnHand());
    }


    public void takenBooksHistory() {
        printStudentBooksOnHand(studentBookRepository.studentBookOnHandAndBookHistory(currentProfile.getId(), null), null);
    }

    public void bestBooks() {
        printBestBooks(studentBookRepository.bestBooks());
    }

    public void alertStudentToReturnTakenBooks() {
        List<StudentBook> studentBooks = studentBookRepository.studentBookOnHandAndBookHistory(currentProfile.getId(), StudentBookStatus.TAKEN);
        if (!studentBooks.isEmpty()) {
            System.out.println("Please provide the books you have borrowed within the specified deadlines.\n");
            printStudentBooksOnHand(studentBooks, StudentBookStatus.TAKEN);
        }
    }

    public void printBestBooks(List<StudentBook> bestBooks) {
        if (bestBooks.isEmpty()) {
            System.out.println("No book's history available.");
        } else {
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("| Category ID   | Category Name              |  Book ID      |  Book's Title                 |  Book's author                | Taken Book Counts  |");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");

            for (StudentBook studentBook : bestBooks) {
                String formattedContact = String.format("| %-14s| %-27s| %-14s| %-30s| %-30s| %-19s|",
                        studentBook.getBook().getCategory().getId(), studentBook.getBook().getCategory().getName(),
                        studentBook.getBook().getId(), studentBook.getBook().getTitle(), studentBook.getBook().getAuthor(), studentBook.getTakenCount());
                System.out.println(formattedContact);
            }
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    public void printAllBooksOnHandOfStudents(List<StudentBook> bookList) {
        if (bookList.isEmpty()) {
            System.out.println("No books available on hands.");
        } else {
            System.out.println("------------------------------------------------------------------------" +
                    "------------------------------------------------------------------------------------" +
                    "----------------------------------------------------------------");
            System.out.println("| Book Id  | Category Id  | Category name       | Author                   | Title               " +
                    "| Taken Date                 | Deadline Date |  Student Id    |  Student name   " +
                    "    |  Student surname    | Student phone |");
            System.out.println("--------------------------------------------------------------------------------" +
                    "-------------------------------------------------------------------------------------------" +
                    "-------------------------------------------------");

            for (StudentBook studentBook : bookList) {
                String formattedContact = String.format("| %-9s| %-13s| %-20s| %-25s| %-20s| %-14s | %-14s| %-15s| %-20s| %-20s| %-14s|",
                        studentBook.getBook().getId(), studentBook.getBook().getCategory().getId(),
                        studentBook.getBook().getCategory().getName(), studentBook.getBook().getAuthor(),
                        studentBook.getBook().getTitle(), studentBook.getCreatedDate(), studentBook.getDeadlineDate(),
                        studentBook.getStudentProfile().getId(), studentBook.getStudentProfile().getName(),
                        studentBook.getStudentProfile().getSurname(), studentBook.getStudentProfile().getPhone());
                System.out.println(formattedContact);
            }
            System.out.println("---------------------------------------------------------" +
                    "--------------------------------------------------------------------" +
                    "-----------------------------------------------------------------------------------------------");
        }
    }


    public void printStudentBooksOnHand(List<StudentBook> bookList, StudentBookStatus status) {
        if (bookList.isEmpty()) {
            System.out.println("No books available on hands.");
        } else if (status != null) {
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("| Category Id  | Category name       | Book Id   | Author                   | Title               | Taken Date                 | Deadline Date|");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");

            for (StudentBook studentBook : bookList) {
                /*Integer availableDay = studentBook.getBook().getAvailableDay();
                LocalDateTime takenDate = studentBook.getCreatedDate();
                LocalDateTime deadLine = takenDate.plusDays(availableDay);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

                // Parse the date string
                LocalDateTime dateTime = LocalDateTime.parse(deadLine.toString(), formatter);

                // Extract the date
                String extractedDate = dateTime.toLocalDate().toString();*/
                String formattedContact = String.format("| %-13s| %-20s| %-10s| %-25s| %-20s| %-14s | %-13s|",
                        studentBook.getBook().getCategory().getId(), studentBook.getBook().getCategory().getName(),
                        studentBook.getBookId(), studentBook.getBook().getAuthor(), studentBook.getBook().getTitle(),
                        studentBook.getCreatedDate(), studentBook.getDeadlineDate());
                System.out.println(formattedContact);
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        } else {
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("| Category Id  | Category name       | Book Id   | Author                   | Title               | Taken Date                | Returned Date              | Deadline Date|");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            for (StudentBook studentBook : bookList) {
                String formattedContact = String.format("| %-13s| %-20s| %-10s| %-25s| %-20s| %-14s| %-27s| %-13s|",
                        studentBook.getBook().getCategory().getId(), studentBook.getBook().getCategory().getName(),
                        studentBook.getBookId(), studentBook.getBook().getAuthor(), studentBook.getBook().getTitle(),
                        studentBook.getCreatedDate(), studentBook.getReturnedDate(), studentBook.getDeadlineDate());
                System.out.println(formattedContact);
            }
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

}
