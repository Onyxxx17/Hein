package game.gameplay.managers;

import game.core.Player;
import game.setup.Dice;
import java.util.*;

/**
 * Handles tie-breaking among players using dice rolls.
 */
public class DiceTieBreaker {
    private final Dice dice;

    // ============================ Constructor ============================

    /**
     * Constructs a DiceTieBreaker with a new Dice instance.
     */
    public DiceTieBreaker() {
        this.dice = new Dice();
    }

    // ============================ Instance Method ============================

    /**
     * Resolves a tie among players by having each roll a die.
     * The player with the highest roll wins.
     *
     * @param tiedPlayers List of players involved in the tie.
     * @return The player who wins the tie-breaker.
     */
    public Player resolveTie(List<Player> tiedPlayers) {
        Map<Player, Integer> rolls = rollDice(tiedPlayers);
        sortPlayersByRolls(tiedPlayers, rolls);
        showResults(tiedPlayers, rolls);
        return tiedPlayers.get(0);
    }

    // ============================ Private Instance Methods (Helpers) ============================

    /**
     * Rolls the dice for each player.
     *
     * @param players List of players to roll for.
     * @return Map of players and their respective dice roll results.
     */
    private Map<Player, Integer> rollDice(List<Player> players) {
        Map<Player, Integer> results = new HashMap<>();

        System.out.println("\n🎲 Breaking tie with dice rolls...");
        for (Player p : players) {
            int roll = dice.roll();
            dice.animateRoll(p.getName(), roll);
            results.put(p, roll);
        }
        return results;
    }

    /**
     * Sorts the list of players in-place based on their roll results.
     *
     * @param players List of players to sort.
     * @param rolls   Map containing each player's dice roll result.
     */
    private void sortPlayersByRolls(List<Player> players, Map<Player, Integer> rolls) {
        Collections.sort(players, (p1, p2) -> {
            int roll1 = rolls.get(p1);
            int roll2 = rolls.get(p2);
            return Integer.compare(roll2, roll1); // Sort in descending order
        });
    }

    /**
     * Displays each player's dice roll result.
     *
     * @param players List of players sorted by dice result.
     * @param rolls   Map of player dice roll results.
     */
    private void showResults(List<Player> players, Map<Player, Integer> rolls) {
        System.out.println("\n📊 Dice Results:");
        players.forEach(p -> System.out.println(p.getName() + ": " + rolls.get(p)));
    }
}
