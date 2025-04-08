package game.gameplay.managers;

import game.core.Player;
import game.gameplay.PlayerComparator;
import game.renderer.GameFlowRenderer;
import java.util.*;

public class WinnerDeterminer {
    private final List<Player> players;
    private final DiceTieBreaker diceTieBreaker;

    public WinnerDeterminer(List<Player> players) {
        this.players = players;
        this.diceTieBreaker = new DiceTieBreaker();
    }

    public Player determineWinner() {
        sortPlayers();

        // Get all the players with same score
        List<Player> playersWithSameScore = getPotentialWinners();
        
        if (playersWithSameScore.size() > 1) {
            GameFlowRenderer.showTieBreaker(playersWithSameScore);
        }

        //Get all platers with all same conditions to solve with dice
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

    private void sortPlayers() {
        Collections.sort(players, new PlayerComparator());
    }

    private List<Player> getPotentialWinners() {
        List<Player> winners = new ArrayList<>();
        if (players.isEmpty()) return winners;
        
        int topScore = players.get(0).getScore();
        for (Player p : players) {
            if (p.getScore() == topScore) winners.add(p);
        }
        return winners;
    }

    private Player resolveTie(List<Player> tiedPlayers) {
        Player winner = diceTieBreaker.resolveTie(tiedPlayers);
        updatePlayerOrder(tiedPlayers);
        return winner;
    }

    private void updatePlayerOrder(List<Player> tiedPlayers) {
        players.removeAll(tiedPlayers);
        players.addAll(0, tiedPlayers);
    }

}