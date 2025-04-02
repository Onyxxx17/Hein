package game.utils;

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
        } catch (Exception e) {
            System.out.println("Error clearing the console: " + e.getMessage());
        }
    }

    /**
     * Displays a spinner animation for a specified duration.
     *
     * @param message The message to display alongside the spinner.
     * @param duration The duration (in seconds) to show the spinner.
     */
    public static void showSpinnerLoading(String message, int duration) {
        String[] spinner = {"|", "/", "-", "\\"}; // Spinner characters
        int i = 0;
        long endTime = System.currentTimeMillis() + duration * 1000; // Duration in seconds
    
        System.out.print(message + " "); // Print the message once before starting the spinner
        while (System.currentTimeMillis() < endTime) {
            System.out.print("\r" + message + " " + spinner[i % 4]); // Overwrite the spinner character only
            i++;
            sleep(250); // Control the speed of the spinner
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
        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            sleep(500);
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
     * Prints a message inside a box made of '=' characters.
     *
     * @param message The message to display inside the box.
     */
    public static void printBox(String message) {
        System.out.println("=".repeat(40));
        System.out.println(message);
        System.out.println("=".repeat(40));
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
                "\u001B[34m";      // Blue text color
            case "green" ->
                "\u001B[32m";     // Green text color
            case "grey", "gray" ->
                "\u001B[37m"; // Grey (also accepts 'gray')
            case "orange" ->
                "\u001B[38;5;214m";  // Orange text (extended 256-color range)
            case "purple" ->
                "\u001B[35m";    // Purple text color
            case "red" ->
                "\u001B[31m";       // Red text color
            case "yellow" ->
                "\u001B[33m";    // Yellow text color
            case "cyan" ->
                "\u001B[36m";      // Cyan text color
            case "magenta" ->
                "\u001B[35m";   // Magenta text color
            case "black" ->
                "\u001B[30m";     // Black text color
            case "white" ->
                "\u001B[37m";     // White text color
            case "brightgreen" ->
                "\u001B[92m"; // Bright green text
            case "brightyellow" ->
                "\u001B[93m"; // Bright yellow text
            case "brightblue" ->
                "\u001B[94m"; // Bright blue text
            default ->
                "\u001B[0m"; // Default reset code
        };
    }
}
