package app;
import exceptions.InvalidPlayerCountException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        // Welcome Message
        System.out.println("Welcome to the Parade Game!");
        Scanner scanner = new Scanner(System.in);

        int playerCount = 0;

        // Input loop for player count
        while (true) {
            try {
                System.out.print("Enter the number of players: ");
                playerCount = scanner.nextInt();

                // Validate player count
                if (playerCount < 2 || playerCount > 6) {
                    throw new InvalidPlayerCountException("Invalid player count. This game can be played by 2-6 players.\n");
                }

                // Break the loop if a valid player count is entered
                break;

            } catch (InvalidPlayerCountException e) {
                // Handle InvalidPlayerCountException
                System.out.println(e.getMessage());

            } catch (InputMismatchException e) {
                // Handle InputMismatchException (non-integer input)
                System.out.println("Invalid input. Please enter a number.\n");

                // Clear the input buffer
                scanner.next();
            }
        }

        // Game start message
        System.out.println("Starting the game with " + playerCount + " players...");

        // Start the game


        // Close the scanner
        scanner.close();
    }
}
