package uz.ali.util;

import java.util.InputMismatchException;
import java.util.Scanner;

import static uz.ali.container.CompoundContainer.*;

public class ScannerUtil {

    public static int getAction() {
        System.out.print("Choose one of the following menus above: ");
        try {
            return scannerNum.nextInt();
        } catch (InputMismatchException e) {
            scannerNum = new Scanner(System.in);
            return -1;
        }
    }


}
