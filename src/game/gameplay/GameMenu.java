package game.gameplay;

import java.util.List;
import java.util.Scanner;
import game.core.*;
import game.gameplay.*;
import game.renderer.*;
import game.setup.*;

public class GameMenu {

    private final Scanner scanner;

    public GameMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void launch() {
        boolean running = true;

        while (running) {
            printMenuOptions();
            int choice = getUserChoice(1, 3);

            switch (choice) {
                case 1 -> startNewGame();
                case 2 -> showInstructions();
                case 3 -> {
                    System.out.println("Thanks for playing. Goodbye!");
                    running = false;
                    System.exit(0);
                }
            }
        }
    }

    private void printMenuOptions() {
        System.out.println("\n========== 🎮 MAIN MENU 🎮 ==========");
        System.out.println("1. Start New Game");
        System.out.println("2. How to Play");
        System.out.println("3. Quit");
        System.out.println("=====================================");
        System.out.print("Choose an option (1-3): ");
    }

    private int getUserChoice(int min, int max) {
        while (true) {
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                }
            } else {
                scanner.next(); // Consume invalid input
            }
            System.out.print("Invalid input. Please enter a number between " + min + " and " + max + ": ");
        }
    }

    private void startNewGame() {
        System.out.println("\nStarting a new game...\n");
        PlayerSetup setup = new PlayerSetup(scanner);
        int playerCount = setup.askForNumberOfPlayers();
        List<Player> players = setup.createPlayers(playerCount);
        Deck deck = new Deck();
        GameManager gameManager = new GameManager(players, deck);
        Game game = new Game(gameManager, scanner);
        game.startGame();
    }

    private void showInstructions() {
        System.out.println("\n📜 HOW TO PLAY:");
        System.out.println("- Each player takes turns playing cards into the parade.");
        System.out.println("- The goal is to minimize your score based on card values.");
        System.out.println("- At the end of the game, you'll select two final cards to add to your open cards.");
        System.out.println("- The lowest score wins!");
        System.out.println();
    }
}
