package game.gameplay;

import game.core.*;
import game.renderer.GamePhaseRenderer;
import game.utils.*;
import java.util.*;

/**
 * Displays the final podium and rankings of players at the end of the game.
 * Mimics a celebratory leaderboard similar to Kahoot.
 */
public class Podium {

    /**
     * Renders the podium and final rankings based on player scores.
     *
     * @param players List of players sorted by rank (highest score first).
     * @param winner The player who won the game.
     */
    public static void showPodium(List<Player> players, Player winner) {
        GamePhaseRenderer.showFinalResultPhase();
        Helper.sleep(500);

        // Display simple ASCII podium
        System.out.println("\n       PODIUM       ");

        // First place (winner)
        System.out.println("        1st         ");
        System.out.println("      ┌─────┐       ");
        System.out.println("      │     │       ");
        System.out.println("      │  " + getInitial(winner.getName()) + "  │       ");

        // Handle different numbers of players
        if (players.size() >= Constants.PODIUM_SIZE) {
            // Show both 2nd and 3rd places
            System.out.println("┌─────┼─────┼─────┐");
            System.out.println("│     │     │     │");
            System.out.println("│  " + getInitial(players.get(1).getName()) + "  │     │  "
                    + getInitial(players.get(2).getName()) + "  │");
            System.out.println("│ 2nd │     │ 3rd │");
            System.out.println("└─────┴─────┴─────┘");
        } else if (players.size() == Constants.MIN_PLAYERS) {
            // Show only 2nd place, not 3rd
            System.out.println("┌─────┼─────│     ");
            System.out.println("│     │     │     ");
            System.out.println("│  " + getInitial(players.get(1).getName()) + "  │     │     ");
            System.out.println("│ 2nd │     │     ");
            System.out.println("└─────┴─────┘     ");
        }

        // Display detailed results for all players
        System.out.println("\n--- PLAYER RANKINGS ---");

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            String medal = "";

            switch (i) {
                case 0 ->
                    medal = "🥇";
                case 1 ->
                    medal = "🥈";
                case 2 ->
                    medal = "🥉";
                default -> {
                }
            }

            System.out.printf("%s #%d: %s - %d points%n",
                    medal, (i + 1), player.getName(), player.getScore());
        }

        // Display winner message
        System.out.println("\n🎉 CONGRATULATIONS 🎉");
        System.out.println("🏆 " + winner.getName() + " WINS THE GAME! 🏆");
        System.out.println("===============================");
    }

    /**
     * Returns the uppercase first character of a player's name.
     *
     * @param name Player's name.
     * @return Initial letter as uppercase string, or a space if name is empty.
     */
    private static String getInitial(String name) {
        if (name == null || name.isEmpty()) {
            return " ";
        }
        return name.substring(0, 1).toUpperCase();
    }
}
