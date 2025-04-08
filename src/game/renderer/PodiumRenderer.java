package game.renderer;

import game.core.Player;
import game.utils.Constants;
import game.utils.Helper;
import java.util.*;

public class PodiumRenderer {

    public static void renderPodium(ArrayList<Player> players, Player winner) {
        System.out.println("===============================");
        System.out.println("        FINAL RESULTS         ");
        System.out.println("===============================");

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
            System.out.println("│  " + getInitial(players.get(1).getName()) + "  │     │  " +
                    getInitial(players.get(2).getName()) + "  │");
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
                case 0 -> medal = "🥇";
                case 1 -> medal = "🥈";
                case 2 -> medal = "🥉";
                default -> {
                }
            }

            System.out.printf("%s #%d: %s - %d points%n",
                    medal, (i + 1), player.getName(), player.getScore());
        }

        // Display winner message
        System.out.println("\n🎉 CONGRATULATIONS 🎉");
        System.out.println("🏆 " + winner.getName() + " WINS THE GAME! 🏆");
        // System.out.println("\nThanks for playing Parade!");
        System.out.println("===============================");
    }

    /**
     * Gets the first letter/initial of a name for the podium display
     */
    private static String getInitial(String name) {
        if (name == null || name.isEmpty()) {
            return " ";
        }
        return name.substring(0, 1).toUpperCase();
    }
}