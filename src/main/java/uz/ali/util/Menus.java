package uz.ali.util;

public class Menus {

    public static void showAdminMenu() {
        System.out.println("\n\t\t **************** Admin Menu ****************");
        System.out.println("1. Books");
        System.out.println("2. Student Profiles");
        System.out.println("3. Categories");
        System.out.println("4. Profiles");
        System.out.println("0. Exit...");
    }

    public static void showBookMenu() {
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


    public static void updateBookFieldsMenu() {
        System.out.println("\nSelect the field(s) you want to update: ");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Publish Date");
        System.out.println("4. Available Day");
        System.out.println("0. Exit...");
    }

}
