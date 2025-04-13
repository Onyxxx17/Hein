package game.gameplay;

import game.core.*;
import game.exceptions.InvalidInputException;
import game.renderer.GamePhaseRenderer;
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
            GamePhaseRenderer.displayMenuOptions();

            try {
                String choice = scanner.next().trim();
                int userChoice = Integer.parseInt(choice);

                switch (userChoice) {
                    case 1 -> {
                        startNewGame();
                        loop = false;
                    }
                    case 2 ->
                        GamePhaseRenderer.displayInstructions();
                    // loop continues, menu shown again
                    case 3 -> {
                        GamePhaseRenderer.displayGoodByeMessage();
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
            GamePhaseRenderer.displayGoodByeMessage();
            return false;
        }
    }
}
