package utils;

import java.util.Scanner;

public class InputUtils {

    private static final Scanner sc = new Scanner(System.in);

    public static String readString(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    public static String readNonEmptyString(String msg) {
        String input;
        do {
            System.out.print(msg);
            input = sc.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }

    public static int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }
}
