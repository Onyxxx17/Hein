package game.gameplay.managers;

import game.core.*;
import game.renderer.*;
import game.utils.*;
import java.util.*;

/**
 * Handles the quit feature of the game.
 */
public class QuitHandler {

    // ============================ Instance Variables ============================

    /**
     * List of players in the game.
     */
    private final List<Player> players;

    /**
     * The scanner for user input.
     */
    private final Scanner scanner;

    // ============================ Constructor ============================

    /**
     * Constructs a QuitHandler with the list of players and a scanner.
     *
     * @param players The list of players in the game.
     * @param scanner The scanner for user input.
     */
    public QuitHandler(List<Player> players, Scanner scanner) {
        this.players = players;
        this.scanner = scanner;
    }

    // ============================ Instance Methods ============================
    /**
     * Checks if a player wants to quit the game.
     *
     * @param player   The player who is checking to quit.
     * @param isHuman  Whether the player is human or not.
     * @param iterator The iterator for the players list.
     * @return true if the player chose to quit, false otherwise.
     */
    public boolean checkForQuit(Player player, boolean isHuman, Iterator<Player> iterator) {
        if (isHuman) {
            // Show quit option
            GameFlowRenderer.showQuitOption();
            String input = scanner.nextLine().trim();

            // Check if the input is "quit" (case insensitive)
            if (input.equalsIgnoreCase("quit")) {

                // Confirm quit
                GameFlowRenderer.confirmQuit();
                String confirm = scanner.nextLine().trim().toLowerCase();
                while (!confirm.matches("y|n|yes|no")) {
                    System.out.print("Invalid input. Please enter 'y' or 'n': ");
                    confirm = scanner.nextLine().trim().toLowerCase();
                }

                // If the player confirms, remove them from the game, else the game will continue as normal
                if (confirm.equals("y") || confirm.equals("yes")) {
                    System.out.println("\n" + player.getName() + " chose to quit the game.");
                    iterator.remove();
                    int humanCount = countHumans();
                    if (players.size() == 1 || humanCount == 0) {
                        if (players.size() == 1) {
                            GameFlowRenderer.showGameOverOnlyOnePlayerLeft();
                        } else {
                            GameFlowRenderer.showGameOverNoHumansLeft();
                        }

                        Helper.sleep(Constants.NORMAL_DELAY_TIME);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Counts how many human players remain.
     */
    public int countHumans() {
        int count = 0;
        for (Player player : players) {
            if (player.isHuman()) {
                count++;
            }
        }
        return count;
    }
}