package gameplay;

import coreclasses.Player;
import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        int result = Integer.compare(o1.getScore(), o2.getScore());
        if (result == 0) {
            int totalCards1 = o1.getTotalOpenCards();
            int totalCards2 = o2.getTotalOpenCards();
            result = Integer.compare(totalCards1, totalCards2); // Lower card count wins
            if (result == 0) {
                result = Integer.compare(o1.getOpenCards().size(), o2.getOpenCards().size());
                return result; // Lower card count wins
            }
        }
        return result;
    }

}
