package utils;

import coreclasses.*;
import exceptions.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameUtil {
    private static final int DELAY_DURATION = 100;
    public static void welcomeMessage() {
        Helper.typewrite("Welcome to the Parade Card Game!\n", DELAY_DURATION);
        Helper.typewrite("Remember Players! The rule is simple. Score as low as possible. Good Luck!\n", DELAY_DURATION);
    }

    public static void pressEnterToContinue(Scanner scanner) {
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // Waits for the user to press Enter
        scanner.nextLine();
        Helper.flush();
    }

    public static int askForNumberOfPlayers(Scanner scanner) {
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
        return playerCount;
    }

    public static ArrayList<Player> createPlayers(int numPlayers, Scanner scanner) {
        ArrayList<Player> players = new ArrayList<>();

        int humanCount = 0;
        int botIndex = 1;
        for (int i = 1; i <= numPlayers; i++) {
            while (true) {
                try {
                    System.out.print("Is Player " + i + " (H)uman or (C)omputer? ");
                    String type = scanner.next().toUpperCase();
                    switch (type) {
                        case "HUMAN":
                        case "H":
                            System.out.print("Enter name: ");
                            String name = scanner.next();
                            players.add(new Human(name));
                            humanCount++;
                            break;
                        case "COMPUTER":
                        case "C":
                            //Check if there are no human players
                            if (i == numPlayers && humanCount == 0) {
                                System.out.println("There must be at least one human player for the game to start");
                                i--;
                            } else {
                                //Add a bot
                                players.add(new Computer("Bot " + botIndex++));
                            }
                            break;
                        default:
                            throw new InvalidTypeException("Invalid player type. Please enter 'H' or 'Human', [or] 'C' or 'Computer'.\n");
                    }
                    break;
                } catch (InvalidTypeException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
        return players;
    }
}
