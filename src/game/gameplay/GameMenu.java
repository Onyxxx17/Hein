package game.gameplay;

import game.core.*;
import game.renderer.GameState;
import game.setup.*;
import game.utils.Helper;
import java.util.List;
import java.util.Scanner;

public class GameMenu {

    private final Scanner scanner;

    public GameMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void launch() {
        boolean loop = true;
        while (loop) {
            printMenuOptions();

            try {
                String choice = scanner.next().trim();
                int userChoice = Integer.parseInt(choice);

                switch (userChoice) {
                    case 1:
                        startNewGame(); // your original game flow
                        loop = false;   // exit loop after starting game
                        break;
                    case 2:
                        showInstructions();
                        break; // loop continues, menu shown again
                    case 3:
                        GameState.goodbyeMessage();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("❌ Invalid choice. Please enter a number (1-3).\n");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number (1-3).\n");
                scanner.nextLine(); // clear the invalid input
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

    // private void startNewGameLoop() {
    //     boolean playAgain = true;
    //     while (playAgain) {
    //         startNewGame(); // your original game flow
    //         playAgain = askForAnotherGame(); // ask after each game
    //     }
    //     GameState.goodbyeMessage(); // say goodbye if they say no
    // }
    private void startNewGame() {
        System.out.print("\nStarting a new game");
        Helper.loading();
        Helper.flush();
        System.out.println("\n");
        PlayerSetup setup = new PlayerSetup(scanner);
        int playerCount = setup.askForNumberOfPlayers();
        List<Player> players = setup.createPlayers(playerCount);
        Deck deck = new Deck();
        GameManager gameManager = new GameManager(players, deck);
        Game game = new Game(gameManager, scanner);
        game.startGame();
    }

    // private void showInstructions() {
    //     System.out.println("\n📜 HOW TO PLAY:");
    //     System.out.println("- Each player takes turns playing cards into the parade.");
    //     System.out.println("- The goal is to minimize your score based on card values.");
    //     System.out.println("- At the end of the game, you'll select two final cards to add to your open cards.");
    //     System.out.println("- The lowest score wins!");
    //     System.out.println();
    // }
    private void showInstructions() {
        Helper.flush();
        System.out.println("\n🌟 HOW TO PLAY PARADE 🌟");
        System.out.println("🎯 GOAL: Collect the LOWEST score by playing cards strategically!");
        System.out.println("\n🏁 SETUP:");
        System.out.println("- Everyone gets 5 cards");
        System.out.println("- 6 cards form the starting parade");

        System.out.println("\n🔄 YOUR TURN:");
        System.out.println("1. Play 1 card to the END of the parade");
        System.out.println("2. Cards might get removed:");
        System.out.println("   💥 If you play ZERO (0):");
        System.out.println("      - EVERYTHING disappears except your 0 card!");
        System.out.println("   💥 If you play a NUMBER (like 5):");
        System.out.println("      - First 5 cards are SAFE 🔒");
        System.out.println("      - Remove cards AFTER these if they:");
        System.out.println("        • Match your card's COLOR 🎨");
        System.out.println("        • Have value ≤ your card's number 🔢");

        System.out.println("\n🚨 GAME ENDS WHEN:");
        System.out.println("- Deck runs out ❌");
        System.out.println("- Someone collects all 6 colors 🌈");

        System.out.println("\n🏆 FINAL ROUND:");
        System.out.println("- Everyone discards 2 cards to their collection");
        System.out.println("- Lowest TOTAL of collected cards WINS! 🏅");
        System.out.println("  (Tiebreaker: Fewer cards → Fewer colors)");
    }

    public boolean askForAnotherGame() {
        System.out.print("\nDo you want to play another game? (yes/no): ");
        String input = scanner.nextLine().trim();
        while (!input.equalsIgnoreCase("yes") && !input.equalsIgnoreCase("no")) {
            System.out.print("Invalid input. Please enter 'yes' or 'no'.\n");
            System.out.print("\nDo you want to play another game? (yes/no): ");
            input = scanner.nextLine().trim();
        }
        if (input.equalsIgnoreCase("yes")) {
            return true;
        } else {
            GameState.goodbyeMessage();
            return false;
        }
    }
}
