package uz.ali.controller;

import uz.ali.model.Category;

import java.util.Scanner;

import static uz.ali.container.CompoundContainer.*;

public class BookController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            showMenu();
            switch (getAction()) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:
                    addBook();
                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:

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


    private void showMenu() {
        System.out.println("\n\t\t **************** Book Menu ****************");
        System.out.println("1. Book List");
        System.out.println("2. Search");
        System.out.println("3. Add book");
        System.out.println("4. Delete book");
        System.out.println("5. Update book");
        System.out.println("6. Books on hand");
        System.out.println("7. Book history");
        System.out.println("8. Best books");
        System.out.println("0. Exit");
    }


    private void getCategoryList() {
        categoryService.getCategoryList();
    }

    private void addBook() {
        String title = getNonEmptyInput("Enter title (at least 2 characters): ");
        String author = getNonEmptyInput("Enter author (at least 2 characters): ");
        String categoryId = getNonEmptyInputNumber("Enter Category Id: ");
        String publishDate = getNonEmptyInput("Enter published date (yyyy-MM-dd): "); // 2024-08-31

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


    public String getNonEmptyInput(String message) {
        scannerStr = new Scanner(System.in);
        String input;
        do {
            System.out.print(message);
            input = scannerStr.nextLine().trim();
        } while (input.length() < 3 || input.isBlank());
        return input;
    }

    public String getNonEmptyInputNumber(String message) {
        scannerStr = new Scanner(System.in);
        String input;
        do {
            System.out.print(message);
            input = scannerStr.nextLine().trim();
        } while (input.isBlank() || !checkIfNumber(input));
        return input;
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
