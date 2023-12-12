package uz.ali.model;

import java.time.LocalDateTime;

public class Category {

    private Integer id;
    private String name;
    private LocalDateTime createdDate;

    private boolean visible = true;

    public Category() {
    }

    public Category(Integer id, String name, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
    }

    public Category(String name, LocalDateTime createdDate) {
        this.name = name;
        this.createdDate = createdDate;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(Integer id, String name, LocalDateTime createdDate, boolean visible) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.visible = visible;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", visible=" + visible +
                '}';
    }
}
