package game.setup;

import game.core.Player;
import game.renderer.*;
import game.utils.Helper;
import java.util.*;
public class StartingPlayerDecider {
    private final Dice dice;
    public StartingPlayerDecider(Dice dice) {
        this.dice = dice;
    }
    /**
     * Decides starting player via dice rolls with tie-breakers.
     *
     * @return The winning player who goes first
     */
    public Player decideStartingPlayer(List<Player> players) {
        StartingPlayerRenderer.showInitialMessage();
        List<Player> contenders = new ArrayList<>(players);
        while (contenders.size() > 1) {
            contenders = runDiceRound(contenders);
        }

        return annouceStartingPlayer(contenders.get(0));
    }

    private List<Player> runDiceRound(List<Player> contenders) {
        StartingPlayerRenderer.rollMessage();
        HashMap<Integer, List<Player>> rollMap = new HashMap<>();
        int maxRoll = 0;

        for (Player player : contenders) {
            int roll = processPlayerRoll(player);
            updateRollMap(rollMap, roll, player);
            maxRoll = Math.max(maxRoll, roll);
        }

        return handleTieOfDice(rollMap.get(maxRoll), maxRoll);
    }

    private int processPlayerRoll(Player player) {
        int roll = dice.roll();
        dice.animateRoll(player.getName(), roll);
        StartingPlayerRenderer.showRollNumber(player, roll);
        return roll;
    }

    private void updateRollMap(HashMap<Integer, List<Player>> rollMap, int roll, Player player) {
        rollMap.computeIfAbsent(roll, k -> new ArrayList<>()).add(player);
    }

    private List<Player> handleTieOfDice(List<Player> tiedPlayers, int maxRoll) {
        if (tiedPlayers.size() > 1) {
            StartingPlayerRenderer.showTie(tiedPlayers, maxRoll);
            Helper.loading();
        }
        return tiedPlayers;
    }

    private Player annouceStartingPlayer(Player winner) {
        StartingPlayerRenderer.showWinner(winner);
        return winner;
    }
}
