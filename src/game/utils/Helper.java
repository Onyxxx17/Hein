package game.utils;
public class Helper {

    // Function to clear the console
    public static void flush() {
        try {
            // For Windows
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix-based systems (Linux, macOS, etc.)
                System.out.print("\033[H\033[2J");  
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error clearing the console: " + e.getMessage());
        }
    }

    public static void showSpinnerLoading(String message, int duration) {
        String[] spinner = {"|", "/", "-", "\\"};
        int i = 0;
        long endTime = System.currentTimeMillis() + duration * 1000;  // Set duration for spinner in seconds
    
        System.out.print(message);
        while (System.currentTimeMillis() < endTime) {
            System.out.print("\r" + message + " " + spinner[i % 4]);  // Overwrite previous character
            i++;
            try {
                Thread.sleep(250);  // Delay between spins (250 milliseconds)
            } catch (InterruptedException e) {
            }
        }
        System.out.println("\nLoading complete!");
    }

    /**
     * Introduces a delay to simulate waiting between player turns.
     * 
     * @param milliseconds the duration to wait in milliseconds
     */
    public static void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread was interrupted.");
        }
    }

    /**
     * Simulates a typewriter effect when printing a message.
     * 
     * @param message the message to display
     * @param delay the delay in milliseconds between characters
     */
    public static void typewrite(String message, long delay) {
        for (char c : message.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread was interrupted.");
            }
        }
        System.out.println();
    }

    public static String getColorCode(String color) {
        switch (color.toLowerCase()) {
            case "blue": return "\u001B[34m"; // Blue
            case "green": return "\u001B[32m"; // Green
            case "grey": return "\u001B[37m"; // Grey
            case "orange": return "\u001B[38;5;214m"; // Orange (ANSI extended color code)
            case "purple": return "\u001B[35m"; // Purple
            case "red": return "\u001B[31m"; // Red
            default: return "\u001B[0m"; // Reset (Default color)
        }
    }

    public static void progressBar() throws InterruptedException {
        int total = 30;
        for (int i = 0; i <= total; i++) {
            String bar = "=".repeat(i) + " ".repeat(total - i);
            System.out.print("\r[" + bar + "] " + (i * 100 / total) + "%");
            Thread.sleep(100);
        }
        System.out.println("\nComplete!");
    }

    public static void loading(){
        try {
            for (int i = 0; i < 3; i++) {
                System.out.print(".");
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread was interrupted.");
        }
    }
}
