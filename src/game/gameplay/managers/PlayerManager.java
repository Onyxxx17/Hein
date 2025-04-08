package game.gameplay.managers;

import game.core.Player;
import java.util.*;

public class PlayerManager {
    private final List<Player> players;

    public PlayerManager(List<Player> players) {
        this.players = players;
    }

    /**
     * Reorders the players in the list such that the given player is at the
     * start of the list and the rest of the players are in the same order
     * relative to each other.
     *
     * Does nothing if the given player is not in the list.
     *
     * @param startingPlayer The player to put at the start of the list
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
     * Gets the list of players.
     *
     * @return The list of players
     */
    public List<Player> getPlayers() {
        return players;
    }
}