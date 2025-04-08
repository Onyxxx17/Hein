package game.gameplay.managers;

import game.core.Player;
import game.setup.Dice;
import game.utils.Helper;
import java.util.*;

public class DiceTieBreaker {
    public Player resolveTie(List<Player> tiedPlayers) {
        Map<Player, Integer> rolls = rollDice(tiedPlayers);
        sortPlayersByRolls(tiedPlayers, rolls);
        displayResults(tiedPlayers, rolls);
        return tiedPlayers.get(0);
    }

    private Map<Player, Integer> rollDice(List<Player> players) {
        Map<Player, Integer> results = new HashMap<>();
        Dice dice = new Dice();
        
        System.out.println("\n🎲 Breaking tie with dice rolls...");
        for (Player p : players) {
            Helper.loading();
            int roll = dice.roll();
            dice.animateRoll(p.getName(), roll);
            results.put(p, roll);
        }
        return results;
    }

    private void sortPlayersByRolls(List<Player> players, Map<Player, Integer> rolls) {
        players.sort((p1, p2) -> rolls.get(p2) - rolls.get(p1));
    }

    private void displayResults(List<Player> players, Map<Player, Integer> rolls) {
        System.out.println("\n📊 Dice Results:");
        players.forEach(p -> System.out.println(p.getName() + ": " + rolls.get(p)));
    }
}