package game.renderer;

import game.core.Player;
import game.utils.Constants;
import game.utils.Helper;
import java.util.List;

public class StartingPlayerRenderer {

    public static void showInitialMessage() {
        Helper.typewrite("Before we start, every player will roll a dice to decide the starting player.", Constants.TYPEWRITE_DURATION);
        Helper.sleep(Constants.SLOWDELAY);
    }

    public static void showRollMessage() {
        System.out.print("\nRolling to decide the starting player");
        Helper.loading();
    }

    public static void showTie(List<Player> tiedPlayers, int maxRoll) {
        System.out.print("\n" + tiedPlayers.size() + " players tie at " + maxRoll + "! Rerolling for these players");
        Helper.loading();
    }

    public static void showWinner(Player player) {
        System.out.println("\n" + player.getName() + " got the highest roll! "
                + player.getName() + " goes first! ✨");
    }

    public static void showRollNumber(Player player, int roll) {
        System.out.println(player.getName() + " rolled: [ " + roll + " ]");
    }

}
