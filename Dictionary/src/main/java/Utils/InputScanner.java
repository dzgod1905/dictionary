package Utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputScanner {
    private static final Scanner scanner = new Scanner(System.in);
    public static int getInteger(String message) {
        while (true) {
            System.out.printf("%s", message);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException exception) {
                System.out.println("Expected a number!\n");
                scanner.next();
            }
        }
    }
    public static int getInteger() {
        return getInteger("Input a number: ");
    }
    public static int getIntegerRange(String message, int min, int max) {
        if (message == null) message = String.format("Input a number from %d to %d: ", min, max);
        int number;
        do {
            number = getInteger(message);
            if (number < min || max < number) {
                System.out.printf("Expected number from %d to %d!\n", min, max);
            }
        } while (number < min || max < number);
        return number;
    }
    public static int getIntegerRange(int min, int max) {
        return getIntegerRange(String.format("Input a number from %d to %d: ", min, max), min, max);
    }
    public static String getLine() { return scanner.nextLine(); }
    public static String getLine(String message) {
        System.out.printf("%s", message);
        return scanner.nextLine();
    }
    // test client
    public static void main(String[] args) {
        System.out.println(getIntegerRange(0, 9));
    }
}
