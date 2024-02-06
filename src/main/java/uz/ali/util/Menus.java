package uz.ali.util;

public class Menus {

    public static void displayAdminMenu() {
        System.out.println("\n\t\t **************** Admin Menu ****************");
        System.out.println("1. Books");
        System.out.println("2. Student Profiles");
        System.out.println("3. Categories");
        System.out.println("4. Profiles");
        System.out.println("0. Exit...");
    }

    public static void displayBookMenu() {
        System.out.println("\n\t\t **************** Book Menu ****************");
        System.out.println("1. Book list");
        System.out.println("2. Search a book");
        System.out.println("3. Add a new book");
        System.out.println("4. Delete a book");
        System.out.println("5. Update a book");
        System.out.println("6. Books on hand");
        System.out.println("7. Book history");
        System.out.println("8. Best books");
        System.out.println("0. Exit...");
    }

    public static void displayCategoryMenu() {
        System.out.println("\n\t\t **************** Category Menu ****************");
        System.out.println("1. Category List");
        System.out.println("2. Add category");
        System.out.println("3. Delete category");
        System.out.println("4. Update category ");
        System.out.println("0. Exit...");
    }

    public static void displayMainMenu() {
        System.out.println("\n\t\t **************** Main Menu ****************");
        System.out.println("1. Book List");
        System.out.println("2. Search");
        System.out.println("3. Categories");
        System.out.println("4. Login");
        System.out.println("5. Registration");
        System.out.println("0. Exit...");
    }

    public static void displayProfileMenu() {
        System.out.println("\n\t\t **************** Profile Menu ****************");
        System.out.println("1. Profile List");
        System.out.println("2. Search Profile");
        System.out.println("3. Add Profile");
        System.out.println("4. Change Profile status");
        System.out.println("0. Exit...");
    }

    public static void displayStaffMenu() {
        System.out.println("\n\t\t **************** Staff Menu ****************");
        System.out.println("1. Book List");
        System.out.println("2. Student");
        System.out.println("0. Exit...");
    }

    public static void displayStudentMenu() {
        System.out.println("\n\t\t **************** Student Menu ****************");
        System.out.println("1. Book List");
        System.out.println("2. Search");
        System.out.println("3. Take book");
        System.out.println("4. Return book");
        System.out.println("5. Books on hand");
        System.out.println("6. Get history of books");
        System.out.println("0. Exit...");
    }

    public static void displayStudentProfileMenu() {
        System.out.println("\n\t\t **************** Student Profile Menu ****************");
        System.out.println("1. Student list");
        System.out.println("2. Search student");
        System.out.println("3. Block student");
        System.out.println("4. Activate student");
        System.out.println("0. Exit...");
    }


    public static void displayUpdateBookFieldsMenu() {
        System.out.println("\nSelect the field(s) you want to update: ");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Publish Date");
        System.out.println("4. Available Day");
        System.out.println("0. Exit...");
    }

}
