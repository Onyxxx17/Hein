package game.gameplay.managers;

import game.core.Player;
import game.setup.Dice;
import java.util.*;

public class DiceTieBreaker {
    private final Dice dice;

    public DiceTieBreaker() {
        this.dice = new Dice();
    }
    public Player resolveTie(List<Player> tiedPlayers) {
        Map<Player, Integer> rolls = rollDice(tiedPlayers);
        sortPlayersByRolls(tiedPlayers, rolls);
        showResults(tiedPlayers, rolls);
        return tiedPlayers.get(0);
    }

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

    private void sortPlayersByRolls(List<Player> players, Map<Player, Integer> rolls) {
        Collections.sort(players, (p1, p2) -> {
            int roll1 = rolls.get(p1);
            int roll2 = rolls.get(p2);
            return Integer.compare(roll2, roll1); // Sort in descending order
        });
    }

    private void showResults(List<Player> players, Map<Player, Integer> rolls) {
        System.out.println("\n📊 Dice Results:");
        players.forEach(p -> System.out.println(p.getName() + ": " + rolls.get(p)));
    }
}