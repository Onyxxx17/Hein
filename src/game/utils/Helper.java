package game.utils;

import java.io.IOException;
import java.util.Scanner;

/**
 * A utility class providing various helper functions for console-based
 * applications. It includes methods for clearing the console, displaying
 * animations like spinners and progress bars, simulating typewriter effects,
 * and handling simple loading indicators.
 */
public class Helper {

    /**
     * Clears the console screen based on the operating system. - For Windows:
     * Uses the "cls" command. - For Unix-based systems (Linux, macOS): Uses
     * ANSI escape codes.
     */
    public static void flush() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error clearing the console: " + e.getMessage());
        }
    }

    /**
     * Simulates a typewriter effect by printing a message character by
     * character with a delay.
     *
     * @param message The message to display.
     * @param delay The delay (in milliseconds) between each character.
     */
    public static void typewrite(String message, int delay) {
        for (char c : message.toCharArray()) {
            System.out.print(c);
            sleep(delay);
        }
        System.out.println(); // Move to the next line after the message is printed
    }

    /**
     * Displays a progress bar that updates dynamically. The progress bar
     * consists of '=' characters filling up over time.
     */
    public static void progressBar() {
        int total = 30; // Total length of the progress bar
        for (int i = 0; i <= total; i++) {
            String bar = "=".repeat(i) + " ".repeat(total - i);
            System.out.print("\r[" + bar + "] " + (i * 100 / total) + "%"); // Update in place
            sleep(100); // Simulate loading time
        }
        System.out.println("\nComplete!"); // Move to the next line after completion
    }

    /**
     * Displays a simple loading animation by printing three dots sequentially.
     */
    public static void loading() {
        int loading_dots = 3;
        for (int i = 0; i < loading_dots; i++) {
            System.out.print(".");
            sleep(Constants.NORMAL_DELAY_TIME);
        }
        System.out.println(); // Move to the next line after loading
    }

    /**
     * Pauses the program execution for a given duration.
     *
     * @param milliseconds The duration (in milliseconds) to pause the
     * execution.
     */
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            System.out.println("Thread was interrupted!");
        }
    }

    /**
     * Returns the ANSI color code for a given color.
     *
     * @param color The color name.
     * @return The ANSI escape code for that color.
     */
    public static String getColorCode(String color) {
        return switch (color.toLowerCase()) {
            case "blue" ->
                Constants.BLUE;      // Blue text color
            case "green" ->
                Constants.GREEN;     // Green text color
            case "grey", "gray" ->
                Constants.GREY; // Grey (also accepts 'gray')
            case "orange" ->
                Constants.ORANGE;  // Orange text (extended 256-color range)
            case "purple" ->
                Constants.PURPLE;    // Purple text color
            case "red" ->
                Constants.RED;       // Red text color
            default ->
                Constants.RESET; // Default reset code
        };
    }

    /**
     * Waits for the user to press Enter to continue the game. The user is
     * prompted to press Enter to continue.
     *
     * @param scanner The scanner object for user input.
     */
    public static void pressEnterToContinue(Scanner scanner) {
        System.out.print("\n👉 Press Enter to continue...");
        scanner.nextLine(); // Waits for the user to press Enter
    }

    public static void printBox(String title) {
        System.out.println("=".repeat(40));
        System.out.println(title);
        System.out.println("=".repeat(40));
    }
}
