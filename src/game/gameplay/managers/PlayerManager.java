package game.gameplay.managers;

import game.core.Player;
import java.util.*;

/**
 * Manages the list of players in the game.
 */
public class PlayerManager {
    // ============================ Instance Variables ============================
    /**
     * List of players in the game.
     */
    private final List<Player> players;

    // ============================ Constructor ============================

    /**
     * Constructs a PlayerManager with the list of players.
     *
     * @param players The list of players in the game.
     */
    public PlayerManager(List<Player> players) {
        this.players = players;
    }

    // ============================ Instance Methods ============================

    /**
     * Moves the given player to the start of the list.
     * Keeps the relative order of the rest unchanged.
     */
    public void rearrangePlayers(Player startingPlayer) {
        int index = players.indexOf(startingPlayer);
        if (index == -1) return;

        List<Player> rearranged = new ArrayList<>(players.size());
        rearranged.addAll(players.subList(index, players.size()));
        rearranged.addAll(players.subList(0, index));
        
        players.clear();
        players.addAll(rearranged);
    }

    /**
     * Returns the list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }
}
