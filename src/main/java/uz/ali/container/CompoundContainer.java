package uz.ali.container;

import uz.ali.controller.*;
import uz.ali.repository.BookRepository;
import uz.ali.repository.CategoryRepository;
import uz.ali.repository.ProfileRepository;
import uz.ali.repository.TableRepository;
import uz.ali.service.AuthService;
import uz.ali.service.BookService;
import uz.ali.service.CategoryService;
import uz.ali.service.InitService;

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
    public static MainController mainController = new MainController();
    public static InitService initService = new InitService();
    public static AuthService authService = new AuthService();
    public static CategoryService categoryService = new CategoryService();
    public static BookService bookService = new BookService();
    public static ProfileRepository profileRepository = new ProfileRepository();
    public static CategoryRepository categoryRepository = new CategoryRepository();
    public static BookRepository bookRepository = new BookRepository();
    public static TableRepository tableRepository = new TableRepository();


}
