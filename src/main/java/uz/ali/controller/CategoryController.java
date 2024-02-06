package uz.ali.controller;

import static uz.ali.container.CompoundContainer.categoryService;
import static uz.ali.util.Menus.displayCategoryMenu;
import static uz.ali.util.ScannerUtil.getAction;

public class CategoryController {

    public void start() {
        boolean startLoop = true;
        while (startLoop) {
            displayCategoryMenu();
            switch (getAction()) {
                case 1:
                    categoryService.getCategoryList(false);
                    break;
                case 2:
                    categoryService.createCategory();
                    break;
                case 3:
                    categoryService.deleteCategoryById();
                    break;
                case 4:
                    categoryService.updateCategoryById();
                    break;
                case 0:
                    startLoop = false;
                    break;
                default:
                    System.out.println("\n Please, choose one of the following menus below!");
            }
        }
    }

}
