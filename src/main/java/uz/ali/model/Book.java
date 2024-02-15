package uz.ali.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Book {

    private Integer id;
    private String title;
    private String author;
    private Integer categoryId;
    private LocalDate publishDate;
    private Integer availableDay;
    private Boolean visible;
    private LocalDateTime createdDate;

    private Category category;

    public Book() {
    }

    public Book(String title, String author, Integer categoryId, LocalDate publishDate, Integer availableDay, Boolean visible, LocalDateTime createdDate) {
        this.title = title;
        this.author = author;
        this.categoryId = categoryId;
        this.publishDate = publishDate;
        this.availableDay = availableDay;
        this.visible = visible;
        this.createdDate = createdDate;
    }

    public Book(Integer id, String title, String author, Integer categoryId, LocalDate publishDate, Integer availableDay, LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.categoryId = categoryId;
        this.publishDate = publishDate;
        this.availableDay = availableDay;
        this.createdDate = createdDate;
    }

    public Book(Integer id, String title, String author, Integer categoryId, LocalDate publishDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.categoryId = categoryId;
        this.publishDate = publishDate;
    }

    public Book(String title, String author, Integer categoryId, LocalDate publishDate, Integer availableDay) {
        this.title = title;
        this.author = author;
        this.categoryId = categoryId;
        this.publishDate = publishDate;
        this.availableDay = availableDay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getAvailableDay() {
        return availableDay;
    }

    public void setAvailableDay(Integer availableDay) {
        this.availableDay = availableDay;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", categoryId=" + categoryId +
                ", publishDate=" + publishDate +
                ", availableDay=" + availableDay +
                ", visible=" + visible +
                ", createdDate=" + createdDate +
                '}';
    }
}
