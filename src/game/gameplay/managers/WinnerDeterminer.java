package game.gameplay.managers;

import game.core.Player;
import game.gameplay.PlayerComparator;
import game.renderer.GameFlowRenderer;
import java.util.*;

public class WinnerDeterminer {
    private final List<Player> players;
    private final DiceTieBreaker diceTiebreaker;

    public WinnerDeterminer(List<Player> players) {
        this.players = players;
        this.diceTiebreaker = new DiceTieBreaker();
    }

    public Player determineWinner() {
        sortPlayers();
        List<Player> potentialWinners = getPotentialWinners();
        
        if (potentialWinners.size() > 1) {
            GameFlowRenderer.showTieBreaker(potentialWinners);
            return resolveTie(potentialWinners);
        }
        return potentialWinners.get(0);
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
        Player winner = diceTiebreaker.resolveTie(tiedPlayers);
        updatePlayerOrder(tiedPlayers);
        return winner;
    }

    private void updatePlayerOrder(List<Player> tiedPlayers) {
        players.removeAll(tiedPlayers);
        players.addAll(0, tiedPlayers);
    }

}