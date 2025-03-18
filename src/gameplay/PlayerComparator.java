package gameplay;

import java.util.Comparator;

import coreclasses.Player;

public class PlayerComparator implements Comparator<Player>{

    @Override
    public int compare(Player o1, Player o2) {
        return Integer.compare(o1.getScore(), o2.getScore());
    }
    
}
