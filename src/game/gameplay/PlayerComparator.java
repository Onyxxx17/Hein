package game.gameplay;

import game.core.Player;
import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        // Primary: Compare by score (lower score wins)
        int result = Integer.compare(p1.getScore(), p2.getScore());
        if (result != 0) return result;

        // Secondary: Compare by total open cards (lower card count wins)
        result = Integer.compare(p1.getTotalOpenCards(), p2.getTotalOpenCards());
        if (result != 0) return result;

        // Tertiary: Compare by open cards list size (lower colors wins)
        return Integer.compare(p1.getOpenCards().size(), p2.getOpenCards().size());
    }
}
