package uz.ali.controller;

import uz.ali.model.Category;

import java.util.Scanner;

import static uz.ali.container.CompoundContainer.*;

public class CategoryController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            showMenu();
            switch (getAction()) {
                case 1:
                    getCategoryList();
                    break;
                case 2:
                    addCategory();
                    break;
                case 3:
                    deleteCategory();
                    break;
                case 4:
                    updateCategory();
                    break;
                case 0:
                    System.out.println("Exit");
                    startLoop = false;
                    break;
                default:
                    System.out.println("\n Please, choose one of the following menus below!");
            }
        }
    }

    private void updateCategory() {
        System.out.print("Enter category ID that you want to update: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int categoryId = scannerNum.nextInt();
        if (categoryService.isCategoryExistsById(categoryId)) {
            String newCategoryName = getNonEmptyInput("Enter category name (at least 3 characters) that you want to update: ");
            categoryService.updateCategoryById(newCategoryName, categoryId);
        } else {
            System.out.println("Category not found!");
        }
    }

    private void getCategoryList() {
        categoryService.getCategoryList();
    }

    private void addCategory() {
        categoryService.createCategory(new Category(getNonEmptyInput("Enter category name (at least 3 characters): ")));
    }

    private void deleteCategory() {
        System.out.print("Enter category ID that you want to delete: ");
        while (!scannerNum.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scannerNum.next(); // Clear the invalid input
        }
        int categoryId = scannerNum.nextInt();
        categoryService.deleteCategoryById(categoryId);
    }

    public String getNonEmptyInput(String message) {
        scannerStr = new Scanner(System.in);
        String input;
        do {
            System.out.print(message);
            input = scannerStr.nextLine().trim();
        } while (input.length() < 3 || input.isBlank());
        return input;
    }

    private void showMenu() {
        System.out.println("\n\t\t **************** Category Menu ****************");
        System.out.println("1. Category List");
        System.out.println("2. Add category");
        System.out.println("3. Delete category");
        System.out.println("4. Update category ");
        System.out.println("0. Exit");
    }


    public int getAction() {
        while (true) {
            System.out.print("Choose one of the actions above: ");
            String chosenMenu = scannerStr.next();
            if (checkIfNumber(chosenMenu)) {
                return Integer.parseInt(chosenMenu);
            } else {
                showMenu();
                System.out.println("\n Please, choose one of the following menus above!");
            }
        }
    }

    public boolean checkIfNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
