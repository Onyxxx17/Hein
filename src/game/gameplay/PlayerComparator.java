package game.gameplay;

import game.core.Player;
import java.util.Comparator;

/**
 * Comparator for comparing two players based on their scores and open cards.
 * The comparison is done in the following order:
 * 1. Score (lower score wins)
 * 2. Total open cards (lower card count wins)
 * 3. Open cards list size (lower colors wins)
 */
public class PlayerComparator implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        
        int result = Integer.compare(p1.getScore(), p2.getScore());
        if (result != 0) return result;

        
        result = Integer.compare(p1.getTotalOpenCards(), p2.getTotalOpenCards());
        if (result != 0) return result;

        
        return Integer.compare(p1.getOpenCards().size(), p2.getOpenCards().size());
    }
}
