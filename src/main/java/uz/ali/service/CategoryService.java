package uz.ali.service;

import uz.ali.model.Category;

import java.time.LocalDateTime;
import java.util.List;

import static uz.ali.container.CompoundContainer.*;


public class CategoryService {

    public void createCategory(Category category) {
        if (categoryRepository.isCategoryExistsByName(category.getName())) {
            System.out.println("There is a category with such name " + category.getName() + ". Please, add other one!");
        }
        category.setVisible(true);
        category.setCreatedDate(LocalDateTime.now());
        if (categoryRepository.saveCategory(category) > 0) {
            System.out.println("Category is successfully saved!");
        }
    }

    public void getCategoryList() {
        List<Category> categoryList = categoryRepository.getCategoryList();
        printContactList(categoryList);
    }

    public void deleteCategoryById(Integer categoryId) {
        if (categoryRepository.deleteCategoryById(categoryId) > 0) {
            System.out.println("Category is successfully deleted!");
        } else {
            System.out.println("Category not found!");
        }
    }

    public void printContactList(List<Category> categoryList) {
        if (categoryList.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            System.out.println("------------------------------------------------------");
            System.out.println("| Id | Name           |   Created Date             |");
            System.out.println("------------------------------------------------------");

            for (Category category : categoryList) {
                String formattedContact = String.format("| %-3s| %-15s| %-27s|",
                        category.getId(), category.getName(), category.getCreatedDate());
                System.out.println(formattedContact);
            }
            System.out.println("------------------------------------------------------");
        }
    }


    public boolean isCategoryExistsById(Integer categoryId) {
        return categoryRepository.isCategoryExistsById(categoryId);
    }

    public void updateCategoryById(String newCategoryName, Integer categoryId) {
        if (categoryRepository.updateCategoryById(categoryId, newCategoryName) > 0) {
            System.out.println("Category is successfully updated!");
        } else {
            System.out.println("Error in updating!");
        }
    }

}
