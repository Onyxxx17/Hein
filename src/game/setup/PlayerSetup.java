package game.setup;

import game.core.*;
import game.exceptions.*;
import game.utils.Helper;
import java.util.*;

public class PlayerSetup {

    /**
     * Prompts the user for the number of players and validates the input.
     *
     * @param scanner The scanner object for user input.
     * @return The number of players to play the game with.
     */
    public static int askForNumberOfPlayers(Scanner scanner) {
        int playerCount = 0;

        Helper.printBox("🎲 WELCOME TO THE GAME SETUP 🎲");
        // Input loop for player count
        while (true) {
            try {
                System.out.print("👥 Enter the number of players (2-6): ");
                playerCount = scanner.nextInt();

                // Validate player count
                if (playerCount < 2 || playerCount > 6) {
                    throw new InvalidPlayerCountException(
                            "❌ Invalid player count! This game requires 2 to 6 players.\n"
                    );
                }

                // Break the loop if a valid player count is entered
                System.out.println("\n✅ Player count: " + playerCount);
                System.out.println("=".repeat(40));
                break;

            } catch (InvalidPlayerCountException e) {
                System.out.println(e.getMessage());

            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.\n");

                // Clear the input buffer
                scanner.next();
            }
        }
        return playerCount;
    }

    /**
     * Creates a list of players with the specified number of players.
     *
     * Handles setting up each player by asking for their type (human or
     * computer) and name.
     *
     * @param numPlayers The number of players
     * @param scanner The scanner object for user input
     * @return The list of players
     */
    public static ArrayList<Player> createPlayers(int numPlayers, Scanner scanner) {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        int humanCount = 0;
        int botIndex = 1;

        System.out.println("🎭 PLAYER SETUP 🎭");
        System.out.println("=".repeat(40));

        for (int i = 1; i <= numPlayers; i++) {
            String type = getPlayerType(scanner, i);
            boolean isHuman = type.equals("H") || type.equals("HUMAN");

            if (isHuman) {
                handleHumanPlayer(scanner, names, players);
                humanCount++;
            } else {
                if (humanCount == 0 && i == numPlayers) {
                    System.out.println("❌ There must be at least one human player!");
                    i--;
                } else {
                    handleComputerPlayer(players, names, botIndex);
                    botIndex++;
                }
            }
        }

        System.out.println("\n🎉 All players have been set up! Let’s start the game! 🎉");
        System.out.println("=".repeat(40));

        return players;
    }

    /**
     * Gets the type of the player (human or computer) from the user.
     *
     * @param scanner The scanner object to read user input
     * @param playerNumber The number of the player to be set up
     * @return The type of the player as a string, either "HUMAN" or "COMPUTER"
     */
    private static String getPlayerType(Scanner scanner, int playerNumber) {
        String type = "";
        while (true) {
            System.out.print("\n🎮 Is Player " + playerNumber + " (H)uman or (C)omputer? ");
            type = scanner.next().toUpperCase();

            if (type.matches("H|C|HUMAN|COMPUTER")) {
                scanner.nextLine(); // Clear buffer
                return type;
            }
            System.out.println("❌ Invalid choice! Please enter ['H' or 'HUMAN'] or ['C' or 'COMPUTER'].\n");
        }
    }

    /**
     * Handles the setup of a human player.
     *
     * Prompts user for input, validates the input, and adds the player to the
     * list of players.
     *
     * @param scanner Scanner for user input.
     * @param names List of player names to add the new name to.
     * @param players List of players to add the new player to.
     */
    private static void handleHumanPlayer(Scanner scanner, ArrayList<String> names,
            ArrayList<Player> players) {
        String name;
        while (true) {
            System.out.print("📝 Enter player name: ");
            name = scanner.nextLine().trim();

            if (!isValidName(name)) {
                System.out.println("❌ Name must be at least 3 characters long and contain one letter.\n");
                continue;
            }

            if (checkNameWithBot(name)) {
                System.out.println("❌ Name cannot start with 'bot'. If your name contains 'bot', it must be at least 6 characters long.\n");
                continue;
            }

            if (names.contains(name.toLowerCase())) {
                System.out.println("❌ Name already taken by another player. Please choose a different name.\n");
                continue;
            }

            break; // Valid name
        }

        names.add(name.toLowerCase());
        players.add(new Human(name));
        System.out.println("✅ " + name + " has joined the game!");
    }

    /**
     * Handles the setup of a computer player.
     *
     * Generates a bot name, adds it to the list of player names, and adds a
     * Computer player to the list of players.
     *
     * @param players The list of players to add the new computer player to.
     * @param names The list of player names to add the bot name to.
     * @param botIndex The index of the bot (used for generating the bot name).
     */
    private static void handleComputerPlayer(ArrayList<Player> players, ArrayList<String> names, int botIndex) {

        String botName = "Bot " + botIndex;
        names.add(botName.toLowerCase());
        players.add(new Computer(botName));
        System.out.println("🤖 " + botName + " has joined the game!");
    }

    /**
     * Checks if a given name is valid.
     *
     * A name is valid if it is at least 3 characters long and contains at least
     * one letter (A-Z or a-z).
     *
     * @param name The name to check
     * @return true if the name is valid, false otherwise
     */
    public static boolean isValidName(String name) {
        // Ensure name is at least 3 characters long
        if (name.length() < 3) {
            return false;
        }

        // Check if the name contains at least one letter (A-Z or a-z)
        boolean containsLetter = false;
        for (int i = 0; i < name.length(); i++) {
            if (Character.isLetter(name.charAt(i))) {
                containsLetter = true;
                break;
            }
        }

        // If it doesn't contain a letter, return false
        return containsLetter;
    }

    /**
     * Checks if the given player name contains "bot" as a substring, if the
     * name contains bot, it must be at least 5 characters long to be valid
     *
     * @param name the player name to check
     * @return true if the name contains "bot", false otherwise
     */
    public static boolean checkNameWithBot(String name) {
        String first3letters = name.toLowerCase().substring(0, 3);
        if (first3letters.equals("bot")) {
            return !(name.length() > 5);
        }
        return false;
    }
}
