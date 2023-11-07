package model.util;

import java.util.Scanner;

public class Input {
    private static final Scanner sc = new Scanner(System.in);
    public static int choiceIntegerInput(String request){
        int choice;
        do {
            try{
                System.out.println(request);
                choice = Integer.parseInt(sc.nextLine());
                break;
            }catch (NumberFormatException e){
                System.out.println("Invalid input! " + e.getMessage() + " .Please try again!");
            }
        }while (true);
        return choice;
    }
    public static String promt(String request){
        System.out.println(request);
        return sc.nextLine();
    }

     //Input cho phần đăng ký

    public static String prompt(String request, String regexPattern){
        String text;
        do {
            text = promt(request);
            if (Validation.validate(text, regexPattern)){
                System.out.println("Invalid Input! Wrong format! ");
            }
        }while (Validation.validate(text, regexPattern));
        return  text;
    }
}
