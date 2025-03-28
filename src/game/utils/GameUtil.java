package game.utils;

import game.exceptions.*;
import game.core.*;
import game.gameplay.RollDice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;


public class GameUtil {

    private static final int DELAY_DURATION = 45;

    public static void welcomeMessage() {
        Helper.typewrite("Welcome to the Parade Card Game!", DELAY_DURATION);
        Helper.typewrite("Remember Players! The rule is simple. Score as low as possible. Good Luck!\n", DELAY_DURATION);
    }

    public static void pressEnterToContinue(Scanner scanner) {
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // Waits for the user to press Enter
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
                    System.out.println(player.getName() + " rolled " + roll); // Display the roll value

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
