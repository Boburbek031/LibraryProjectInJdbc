package uz.ali.container;

import uz.ali.controller.AdminController;
import uz.ali.controller.MainController;
import uz.ali.controller.StaffController;
import uz.ali.controller.StudentController;
import uz.ali.repository.ProfileRepository;
import uz.ali.repository.TableRepository;
import uz.ali.service.AuthService;
import uz.ali.service.InitService;

import java.util.Scanner;

public class CompoundContainer {


    public static TableRepository tableRepository = new TableRepository();
    public static InitService initService = new InitService();
    public static AuthService authService = new AuthService();
    public static ProfileRepository profileRepository = new ProfileRepository();
    public static AdminController adminController = new AdminController();
    public static StudentController studentController = new StudentController();
    public static StaffController staffController = new StaffController();
    public static MainController mainController = new MainController();
    public static Scanner scannerNum = new Scanner(System.in);
    public static Scanner scannerStr = new Scanner(System.in);


}
