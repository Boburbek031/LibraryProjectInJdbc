package uz.ali.service;

import uz.ali.model.Category;

import java.time.LocalDateTime;

import static uz.ali.container.CompoundContainer.*;


public class CategoryService {

    public void createCategory(Category category) {
        if (categoryRepository.isCategoryExists(category.getName())) {
            System.out.println("There is a category with such name " + category.getName() + ". Please, add other one!");
        }
        category.setVisible(true);
        category.setCreatedDate(LocalDateTime.now());
        if (categoryRepository.saveCategory(category) > 0) {
            System.out.println("Category is successfully saved!");
        }
    }


}
