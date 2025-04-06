package game.renderer;

import java.util.List;
import game.core.Player;
import game.utils.Helper;

public class StartingPlayerRenderer {

    public static void showInitialMessage() {
        Helper.typewrite("Before we start, every player will roll a dice to decide the starting player.", 45);
        Helper.sleep(1000);
    }

    public static void showTie(int numPlayers, int maxRoll) {
        System.out.println("\n" + numPlayers + " players tie at " + maxRoll + "! Rerolling for these players...");
    }

    public static void rollMessage() {
        System.out.print("\nRolling to decide the starting player");
        Helper.loading();
        System.out.println("🎲");
    }

    public static void showTie(List<Player> tiedPlayers, int maxRoll) {
        System.out.print("\n" + tiedPlayers.size() + "players tie at " + maxRoll + "! Rerolling for these players");
    }

    public static void showWinner(Player player) {
        System.out.println("\n" + player.getName() + " got the highest roll! "
                + player.getName() + " goes first! ✨");
    }

    public static void showRollNumber(Player player, int roll) {
        System.out.println(player.getName() + " rolled: [ " + roll + " ]");
    }

}
