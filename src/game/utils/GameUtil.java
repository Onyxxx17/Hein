package game.utils;

import game.core.*;
import game.exceptions.*;
import game.gameplay.RollDice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameUtil {

    private static final int DELAY_DURATION = 45;

    public static void welcomeMessage(Scanner scanner) {
        Helper.flush();
        AsciiArt.welcomeArt();
        String border = "****************************************";

        System.out.println("\n" + border);
        Helper.typewrite("🎉 WELCOME TO THE PARADE CARD GAME! 🎭", DELAY_DURATION);
        System.out.println(border + "\n");

        Helper.typewrite("🎴 Remember Players! The rule is simple.", DELAY_DURATION);
        Helper.typewrite("🏆 Score as LOW as possible. Good Luck! 🍀\n", DELAY_DURATION);

        System.out.println(border + "\n");

        pressEnterToContinue(scanner);
        Helper.flush();
    }

    public static void pressEnterToContinue(Scanner scanner) {
        System.out.print("\n👉 Press Enter to continue...");
        scanner.nextLine(); // Waits for the user to press Enter
    }

    public static int askForNumberOfPlayers(Scanner scanner) {
        int playerCount = 0;

        System.out.println("=".repeat(40));
        System.out.println("🎲 WELCOME TO THE GAME SETUP 🎲");
        System.out.println("=".repeat(40));
        // Input loop for player count
        while (true) {
            try {

                System.out.print("👥 Enter the number of players (2-6): ");
                playerCount = scanner.nextInt();

                // Validate player count
                if (playerCount < 2 || playerCount > 6) {
                    throw new InvalidPlayerCountException(
                            "\n❌ Invalid player count! This game requires 2 to 6 players.\n"
                    );
                }

                // Break the loop if a valid player count is entered
                System.out.println("\n✅ Player count: " + playerCount);
                System.out.println("=".repeat(40));
                break;

            } catch (InvalidPlayerCountException e) {
                System.out.println(e.getMessage());

            } catch (InputMismatchException e) {
                System.out.println("\n❌ Invalid input! Please enter a valid number.\n");

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

        System.out.println("🎭 PLAYER SETUP 🎭");
        System.out.println("=".repeat(40));

        for (int i = 1; i <= numPlayers; i++) {
            while (true) {
                try {
                    System.out.print("\n🎮 Is Player " + i + " (H)uman or (C)omputer? ");
                    String type = scanner.next().toUpperCase();

                    switch (type) {
                        case "HUMAN":
                        case "H":
                            System.out.print("📝 Enter player name: ");
                            String name = scanner.next();
                            players.add(new Human(name));
                            humanCount++;
                            System.out.println("✅ " + name + " has joined as a Human!");
                            break;

                        case "COMPUTER":
                        case "C":
                            // Check if there are no human players
                            if (i == numPlayers && humanCount == 0) {
                                System.out.println("\n❌ There must be at least one human player to start the game!");
                                i--; // Retry current player setup
                            } else {
                                // Add a bot
                                String botName = "Bot " + botIndex++;
                                players.add(new Computer(botName));
                                System.out.println("🤖 " + botName + " has joined as a Computer!");
                            }
                            break;

                        default:
                            throw new InvalidTypeException(
                                    "\n❌ Invalid choice! Please enter 'H' or 'Human', or 'C' or 'Computer'.\n"
                            );
                    }
                    break;
                } catch (InvalidTypeException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("\n🎉 All players have been set up! Let’s start the game! 🎉");
        System.out.println("=".repeat(40));

        return players;
    }

    public static Player decideStartingPlayer(ArrayList<Player> players) throws InterruptedException {
        Helper.typewrite("Before we start, every player will roll a dice to decide the starting player.", 45);
        Thread.sleep(1000);
        ArrayList<Player> contenders = new ArrayList<>(players);

        while (contenders.size() > 1) {
            System.out.println("\nRolling to decide the starting player... 🎲");

            int maxRoll = 0; // To track the highest roll
            HashMap<Integer, ArrayList<Player>> rollMap = new HashMap<>(); // Map roll values to players

            // Each contender rolls the dice
            for (Player player : contenders) {
                try {
                    int roll = RollDice.roll(); // Get a random dice roll (1-6)
                    RollDice.animateRoll(player.getName(), roll); // Display rolling animation
                    System.out.println(player.getName() + " rolled: " + "[ " + roll + " ]"); // Display the roll value

                    // Store players based on their rolled value
                    rollMap.putIfAbsent(roll, new ArrayList<>());
                    rollMap.get(roll).add(player);

                    // Update maxRoll if this roll is the highest so far
                    maxRoll = Math.max(maxRoll, roll);
                } catch (InterruptedException e) {
                    // Handle any interruptions during the animation
                    System.out.println("Animation interrupted for player: " + player.getName());
                    Thread.currentThread().interrupt();
                    // Continue execution instead of breaking
                }
            }

            // Get only the players who rolled the highest value
            contenders = rollMap.get(maxRoll);

            // If multiple players rolled the highest, they will reroll
            if (contenders.size() > 1) {
                System.out.println("\nTie between players scoring " + maxRoll + "! Rerolling for these players...");
            }
        }

        // The final remaining player is the starting player
        Player startingPlayer = contenders.get(0);
        System.out.println("\n" + startingPlayer.getName() + " got the highest roll! " + startingPlayer.getName() + " goes first! ✨");

        return startingPlayer;
    }

    public static void rearrangePlayersList(ArrayList<Player> players, Player startingPlayer) {
        int startingIndex = players.indexOf(startingPlayer);
        if (startingIndex == -1) {
            return; // Starting player not found in the list (should not happen but added just in case)
        }

        // Create a new list with the starting player and the rest in order
        ArrayList<Player> rearrangedList = new ArrayList<>();
        for (int i = startingIndex; i < players.size(); i++) {
            rearrangedList.add(players.get(i));
        }
        for (int i = 0; i < startingIndex; i++) {
            rearrangedList.add(players.get(i));
        }

        // Update the original players list
        players.clear();
        players.addAll(rearrangedList);
    }
}
