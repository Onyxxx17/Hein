package game.setup;

import game.core.Player;
import game.renderer.*;
import java.util.*;

/**
 * StartingPlayerDecider is responsible for determining the starting player in a
 * game by rolling dice. It handles tie-breakers and announces the winner.
 */
public class StartingPlayerDecider {

    // ============================ Instance Variables ============================
    private final Dice dice;

    // ============================ Constructor ============================
    /**
     * Constructs a StartingPlayerDecider with the given dice.
     *
     * @param dice The dice used for rolling.
     */
    public StartingPlayerDecider(Dice dice) {
        this.dice = dice;
    }

    // ============================ Instance Methods ============================
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

        return announceStartingPlayer(contenders.get(0));
    }

    /**
     * Runs a round of dice rolls for the given list of players, processes the
     * results, and returns the list of players who rolled the highest number.
     * If there is a tie, it is passed to handleTieOfDice to resolve the tie.
     *
     * @param contenders The list of players to roll the dice for
     * @return The list of players who rolled the highest number
     */
    private List<Player> runDiceRound(List<Player> contenders) {
        StartingPlayerRenderer.showRollMessage();
        HashMap<Integer, List<Player>> rollMap = new HashMap<>();
        int maxRoll = 0;

        for (Player player : contenders) {
            int roll = processPlayerRoll(player);
            updateRollMap(rollMap, roll, player);
            maxRoll = Math.max(maxRoll, roll);
        }

        return handleTieOfDice(rollMap.get(maxRoll), maxRoll);
    }

    /**
     * Rolls the dice for a single player, animates the dice roll, displays the
     * result, and returns the result of the dice roll.
     *
     * @param player The player to roll the dice for
     * @return The result of the dice roll
     */
    private int processPlayerRoll(Player player) {
        int roll = dice.roll();
        dice.animateRoll(player.getName(), roll);
        StartingPlayerRenderer.showRollNumber(player, roll);
        return roll;
    }

    private void updateRollMap(HashMap<Integer, List<Player>> rollMap, int roll, Player player) {
        rollMap.computeIfAbsent(roll, key -> new ArrayList<>()).add(player);
    }

    private List<Player> handleTieOfDice(List<Player> tiedPlayers, int maxRoll) {
        if (tiedPlayers.size() > 1) {
            StartingPlayerRenderer.showTie(tiedPlayers, maxRoll);
        }
        return tiedPlayers;
    }

    private Player announceStartingPlayer(Player winner) {
        StartingPlayerRenderer.showWinner(winner);
        return winner;
    }
}
