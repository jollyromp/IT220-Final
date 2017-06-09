package maze.io;
/*
 * Caleb Snoozy, Jacob Tran
 * 6/8/2017
 * maze.io
 * ConsoleIO.java
 * IT220-Final - Utility to interact with the users console
 */

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Helper class to do console IO.
 */
public class ConsoleIO {

    // Default input functions
    public static final Predicate<String> NOT_EMPTY = s -> !s.isEmpty();
    public static final UnaryOperator<String> TO_LOWER_CASE = String::toLowerCase;

    // Scanner input
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Print a new line.
     */
    public static void println() {
        System.out.println();
    }

    /**
     * Prints each variable string as a new line.
     */
    public static void println(String... args) {
        for (String line : args) {
            System.out.println(line);
        }
    }

    /**
     * prints each variable object.toString as a new line.
     */
    public static void println(Object... args) {
        for (Object object : args) {
            System.out.println(object);
        }
    }

    /**
     * Prints each variable string as a new phrase on the same line.
     * No extra formatting.
     */
    public static void print(String... args) {
        for (String phrase : args) {
            System.out.print(phrase);
        }
    }

    /**
     * Prints each variable object as a new phrase on the same line.
     * No extra formatting.
     */
    public static void print(Object... args) {
        for (Object phrase : args) {
            System.out.print(phrase);
        }
    }

    /**
     * Get a string from the user console with no prompt.
     */
    public static String nextLine() {
        return nextLine("");
    }

    /**
     * Get a string from the user with a prompt message.
     */
    public static String nextLine(String prompt) {
        return nextLine(prompt, NOT_EMPTY);
    }

    /**
     * Get a string from the user with a prompt, input requirement, and lowercase the input.
     */
    public static String nextLine(String prompt, Predicate<String> inputRequirement) {
        return nextLine(prompt, inputRequirement, TO_LOWER_CASE);
    }

    /**
     * Get a string from the user with a prompt, input requirement, and modifies the input with a operation.
     */
    public static String nextLine(String prompt, Predicate<String> inputRequirement, UnaryOperator<String> resultManipulation) {
        print(prompt + " ");
        do {
            try {
                print(">>> ");
                String input = scanner.nextLine();
                if (inputRequirement.test(input))
                    return resultManipulation.apply(input);
            } catch (InputMismatchException ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    /**
     * Wait for enter to be pressed, ignores typed input
     */
    public static void waitForEnter() {
        print("Press Enter to Continue");
        scanner.nextLine();
    }
}
