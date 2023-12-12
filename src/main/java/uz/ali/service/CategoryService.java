package uz.ali.service;

import uz.ali.model.Category;

import static uz.ali.container.CompoundContainer.*;


public class CategoryService {

    public void createCategory(Category category) {
        if (categoryRepository.isCategoryExists(category.getName())) {
            System.out.println("There is a category with such name " + category.getName() + ". Please, add other one!");
        }

    }


}
