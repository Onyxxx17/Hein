package game.gameplay;

import game.core.*;
import game.renderer.*;
import java.util.*;

/**
 * Displays a podium of top players at the end of the game like Kahoot
 */
public class Podium {

    /**
     * Displays the podium with player rankings based on their scores.
     * List of players sorted by score (lowest to highest for Parade game)
     */
    public static void displayPodium(ArrayList<Player> players, Player winner) {
        PodiumRenderer.renderPodium(players, winner);
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