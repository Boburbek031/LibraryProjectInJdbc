package uz.ali.service;

import uz.ali.model.Category;

import java.time.LocalDateTime;
import java.util.List;

import static uz.ali.container.CompoundContainer.*;
import static uz.ali.util.ValidateInputs.getNonEmptyInput;


public class CategoryService {

    public void createCategory() {
        Category category = new Category(getNonEmptyInput("Enter category name (at least 3 characters): "));
        if (categoryRepository.isCategoryExistsByName(category.getName())) {
            System.out.println("There is a category with such name " + category.getName() + ". Please, add other one!");
        }
        category.setVisible(true);
        category.setCreatedDate(LocalDateTime.now());
        if (categoryRepository.saveCategory(category) > 0) {
            System.out.println("Category is successfully saved!");
        }
    }

    public void getCategoryList(boolean getCategoriesWithoutCreatedTime) {
        printCategoryList(categoryRepository.getCategoryList(), getCategoriesWithoutCreatedTime);
    }

    public void deleteCategoryById() {
        System.out.print("Enter category ID that you want to delete: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int categoryId = scannerNum.nextInt();
        if (categoryRepository.deleteCategoryById(categoryId) > 0) {
            System.out.println("Category is successfully deleted!");
        } else {
            System.out.println("Category not found!");
        }
    }

    public void printCategoryList(List<Category> categoryList, boolean getCatWithoutCreatedDate) {
        if (categoryList.isEmpty()) {
            System.out.println("No Categories available.");
        } else if (getCatWithoutCreatedDate) {
            System.out.println("--------------------------------------");
            System.out.println("| Id | Name                          |");
            System.out.println("--------------------------------------");

            for (Category category : categoryList) {
                String formattedContact = String.format("| %-3s| %-30s|", category.getId(), category.getName());
                System.out.println(formattedContact);
            }
            System.out.println("--------------------------------------");
        } else {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("| Id | Name                          |   Created Date             |");
            System.out.println("-------------------------------------------------------------------");

            for (Category category : categoryList) {
                String formattedContact = String.format("| %-3s| %-30s| %-27s|", category.getId(), category.getName(), category.getCreatedDate());
                System.out.println(formattedContact);
            }
            System.out.println("-------------------------------------------------------------------");
        }
    }

    public boolean isCategoryExistsById(Integer categoryId) {
        return categoryRepository.isCategoryExistsById(categoryId);
    }

    public void updateCategoryById() {
        System.out.print("Enter category ID that you want to update: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int categoryId = scannerNum.nextInt();
        if (categoryService.isCategoryExistsById(categoryId)) {
            String newCategoryName = getNonEmptyInput("Enter category name (at least 3 characters) that you want to update: ");
            if (categoryRepository.updateCategoryById(categoryId, newCategoryName) > 0) {
                System.out.println("Category is successfully updated!");
            } else {
                System.out.println("Error in updating!");
            }
        } else {
            System.out.println("Category not found!");
        }
    }

}
