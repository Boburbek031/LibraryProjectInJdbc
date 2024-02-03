package uz.ali.container;

import uz.ali.controller.*;
import uz.ali.model.Profile;
import uz.ali.repository.*;
import uz.ali.service.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CompoundContainer {


    public static Scanner scannerNum = new Scanner(System.in);
    public static Scanner scannerStr = new Scanner(System.in);
    public static AdminController adminController = new AdminController();
    public static StudentController studentController = new StudentController();
    public static StaffController staffController = new StaffController();
    public static CategoryController categoryController = new CategoryController();
    public static BookController bookController = new BookController();
    public static ProfileController profileController = new ProfileController();
    public static StudentProfileController studentProfileController = new StudentProfileController();
    public static MainController mainController = new MainController();
    public static InitService initService = new InitService();
    public static AuthService authService = new AuthService();
    public static CategoryService categoryService = new CategoryService();
    public static BookService bookService = new BookService();
    public static ProfileService profileService = new ProfileService();
    public static StudentBookService studentBookService = new StudentBookService();
    public static ProfileRepository profileRepository = new ProfileRepository();
    public static CategoryRepository categoryRepository = new CategoryRepository();
    public static BookRepository bookRepository = new BookRepository();
    public static StudentBookRepository studentBookRepository = new StudentBookRepository();
    public static TableRepository tableRepository = new TableRepository();

    // bu project da bitta odam ishlatgani uchun buni static qilib ishlatsek bolaveradi, lekin real project larda
    // bunday bo'lmaydi, ularda bir vaqtni o'zida bir nechta user kirib ishlatadi
    public static Profile currentProfile;
}
