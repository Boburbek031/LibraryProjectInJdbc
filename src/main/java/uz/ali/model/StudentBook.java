package uz.ali.model;

import uz.ali.enums.StudentBookStatus;

import java.time.LocalDateTime;

public class StudentBook {


    private Integer id;
    private Integer studentId;
    private Integer bookId;
    private LocalDateTime createdDate;
    private LocalDateTime returnedDate;
    private StudentBookStatus status;
    private Book book;

    public StudentBook() {
    }

    public StudentBook(Integer id, Integer studentId, Integer bookId, LocalDateTime createdDate, LocalDateTime returnedDate, StudentBookStatus status) {
        this.id = id;
        this.studentId = studentId;
        this.bookId = bookId;
        this.createdDate = createdDate;
        this.returnedDate = returnedDate;
        this.status = status;
    }

    public StudentBook(Integer studentId, Integer bookId, LocalDateTime createdDate, StudentBookStatus status) {
        this.studentId = studentId;
        this.bookId = bookId;
        this.createdDate = createdDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDateTime returnedDate) {
        this.returnedDate = returnedDate;
    }

    public StudentBookStatus getStatus() {
        return status;
    }

    public void setStatus(StudentBookStatus status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "StudentBook{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", bookId=" + bookId +
                ", createdDate=" + createdDate +
                ", returnedDate=" + returnedDate +
                ", status=" + status +
                '}';
    }
}
