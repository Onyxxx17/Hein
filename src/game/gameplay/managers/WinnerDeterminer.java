package game.gameplay.managers;

import game.core.Player;
import game.gameplay.PlayerComparator;
import game.renderer.GameFlowRenderer;
import java.util.*;

/**
 * Determines the winner of the game based on player scores and resolves ties using dice.
 */
public class WinnerDeterminer {

    // ============================ Instance Variables ============================

    /**
     * List of players in the game.
     */
    private final List<Player> players;

    /**
     * The tie-breaker used to resolve ties.
     */
    private final DiceTieBreaker diceTieBreaker;

    // ============================ Constructor ============================

    /**
     * Constructs a WinnerDeterminer with the list of players.
     *
     * @param players The list of players in the game.
     */
    public WinnerDeterminer(List<Player> players) {
        this.players = players;
        this.diceTieBreaker = new DiceTieBreaker();
    }
    // ============================ Public Instance Methods ============================

    /**
     * Determines the final winner, resolving ties using dice if needed.
     *
     * @return The winning player.
     */
    public Player determineWinner() {
        sortPlayers();

        // Get all the players with same score
        List<Player> playersWithSameScore = getPotentialWinners();

        if (playersWithSameScore.size() > 1) {
            GameFlowRenderer.showTieBreaker(playersWithSameScore);
        }

        // Get players with identical conditions to resolve tie with dice
        List<Player> playersWithAllSameConditions = new ArrayList<>();
        for (Player p : playersWithSameScore) {
            if (new PlayerComparator().compare(playersWithSameScore.get(0), p) == 0) {
                playersWithAllSameConditions.add(p);
            }
        }

        if (playersWithAllSameConditions.size() > 1) {
            return resolveTie(playersWithAllSameConditions);
        }
        return playersWithSameScore.get(0);
    }

    // ============================ Private Instance Methods ============================

    /**
     * Sorts players by their score.
     */
    private void sortPlayers() {
        Collections.sort(players, new PlayerComparator());
    }

    /**
     * Gets players with the highest score.
     *
     * @return List of top scoring players.
     */
    private List<Player> getPotentialWinners() {
        List<Player> winners = new ArrayList<>();
        if (players.isEmpty()) {
            return winners;
        }

        int topScore = players.get(0).getScore();
        for (Player p : players) {
            if (p.getScore() == topScore) {
                winners.add(p);
            }
        }
        return winners;
    }

    /**
     * Resolves a tie using a dice tie-breaker and updates the player order.
     *
     * @param tiedPlayers Players involved in the tie.
     * @return The winning player.
     */
    private Player resolveTie(List<Player> tiedPlayers) {
        Player winner = diceTieBreaker.resolveTie(tiedPlayers);
        updatePlayerOrder(tiedPlayers);
        return winner;
    }

    /**
     * Updates player order after a tie-breaker.
     *
     * @param tiedPlayers Players involved in the tie.
     */
    private void updatePlayerOrder(List<Player> tiedPlayers) {
        players.removeAll(tiedPlayers);
        players.addAll(0, tiedPlayers);
    }

}
