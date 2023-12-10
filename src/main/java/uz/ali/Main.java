package uz.ali;

import uz.ali.controller.MainController;

public class Main {
    public static void main(String[] args) {


        MainController mainController = new MainController();
        mainController.start();
        System.out.println("Program is stopped!");

       /* String input = "Hello, MD5!";
        String md5Hash = encode(input);
        System.out.println("MD5 hash of '" + input + "': " + md5Hash);*/


    }
}