package game.gameplay;

import game.core.*;
import game.exceptions.InvalidInputException;
import game.renderer.GamePhaseRenderer;
import game.setup.*;
import game.utils.Constants;
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
            showMenuOptions();

            try {
                String choice = scanner.next().trim();
                int userChoice = Integer.parseInt(choice);

                switch (userChoice) {
                    case 1 -> {
                        startNewGame();
                        loop = false;
                    }
                    case 2 ->
                        showInstructions();
                    // loop continues, menu shown again
                    case 3 -> {
                        GamePhaseRenderer.showGoodByeMessage();
                        System.exit(0);
                    }
                    default ->
                        throw new InvalidInputException();
                }
            } catch (NumberFormatException | InvalidInputException e) {
                System.out.println("❌ Invalid input. Please enter a number (1-3).\n");

            }
        }
    }

    private void startNewGame() {
        System.out.print("\nStarting a new game");
        Helper.loading();
        Helper.flush();
        PlayerSetup setup = new PlayerSetup(scanner);
        int playerCount = setup.askForNumberOfPlayers();
        List<Player> players = setup.createPlayers(playerCount);
        Deck deck = new Deck();
        GameManager gameManager = new GameManager(players, deck);
        GameController game = new GameController(gameManager, scanner);
        game.startGame();
    }

    public boolean askForAnotherGame() {
        System.out.print("\n✨ Do you want to play another game? (y/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        while (!input.matches("yes|no|y|n")) {
            System.out.print("Invalid input. Please enter 'yes' or 'no'.\n");
            System.out.print("\nDo you want to play another game? (yes/no): ");
            input = scanner.nextLine().trim();
        }
        if (input.equals("yes") || input.equals("y")) {
            Helper.flush();
            return true;
        } else {
            GamePhaseRenderer.showGoodByeMessage();
            return false;
        }
    }

    private void showMenuOptions() {

        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║          🎮 MAIN MENU 🎮              ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║ 1. Start New Game                     ║");
        System.out.println("║ 2. Learn How to Play                  ║");
        System.out.println("║ 3. Quit                               ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("Choose an option (1-3): ");
    }

    private void showInstructions() {
        Helper.flush();
        System.out.println("\n══════════════════════════════════════════");
        System.out.println(Constants.BOLD + "🌟✨ HOW TO PLAY PARADE ✨🌟" + Constants.RESET);
        System.out.println("══════════════════════════════════════════\n");

        System.out.println("🎯 " + Constants.BOLD + "GOAL:" + Constants.RESET + " Collect the LOWEST score by playing cards strategically!\n");

        System.out.println("🏁 " + Constants.BOLD + "SETUP:" + Constants.RESET);
        System.out.println("  - Each player starts with " + Constants.BOLD + "5 cards" + Constants.RESET + " 🃏");
        System.out.println("  - " + Constants.BOLD + "6 cards" + Constants.RESET + " form the starting parade 🚶‍♂️🚶‍♀️🚶\n");

        System.out.println("🔄 " + Constants.BOLD + "YOUR TURN:" + Constants.RESET);
        System.out.println("  1️⃣ Play " + Constants.BOLD + "1 card" + Constants.RESET + " to the END of the parade ➡️");
        System.out.println("  2️⃣ Cards might get removed based on rules:");
        System.out.println("     💥 If you play a card with a NUMBER (e.g., " + Constants.BOLD + "5" + Constants.RESET + "):");
        System.out.println("        - The first " + Constants.BOLD + "5 cards" + Constants.RESET + " are SAFE 🔒");
        System.out.println("        - Remove cards AFTER these if they:");
        System.out.println("          • Match your card's " + Constants.BOLD + "COLOR" + Constants.RESET + " 🎨");
        System.out.println("          • Have a value " + Constants.BOLD + "≤ your card's number" + Constants.RESET + " 🔢\n");

        System.out.println("🚨 " + Constants.BOLD + "GAME ENDS WHEN:" + Constants.RESET);
        System.out.println("  - The deck runs out ❌");
        System.out.println("  - Someone collects all " + Constants.BOLD + "6 colors" + Constants.RESET + " 🌈\n");

        System.out.println("🏆 " + Constants.BOLD + "FINAL ROUND:" + Constants.RESET);
        System.out.println("  - Everyone discards " + Constants.BOLD + "2 cards" + Constants.RESET + " to their collection 🗑️");
        System.out.println("  - The player with the LOWEST TOTAL score WINS! 🏅");
        System.out.println("     (Tiebreaker: Fewer cards → Fewer colors)\n");

        System.out.println("══════════════════════════════════════════");
    }
}
