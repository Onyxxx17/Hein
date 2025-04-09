package game.gameplay.managers;

import game.core.*;
import game.renderer.*;
import game.utils.*;
import java.util.*;

public class QuitHandler {

    private final List<Player> players;
    private final Scanner scanner;

    public QuitHandler(List<Player> players, Scanner scanner) {
        this.players = players;
        this.scanner = scanner;
    }

    public boolean checkForQuit(Player player, boolean isHuman, Iterator<Player> iterator) {
        if (isHuman) {
            GameFlowRenderer.showQuitOption();
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("quit")) {
                GameFlowRenderer.confirmQuit();
                String confirm = scanner.nextLine().trim().toLowerCase();
                while (!confirm.matches("y|n|yes|no")) {
                    System.out.print("Invalid input. Please enter 'y' or 'n': ");
                    confirm = scanner.nextLine().trim().toLowerCase();
                }

                if (confirm.equals("y") || confirm.equals("yes")) {
                    System.out.println("\n" + player.getName() + " chose to quit the game.");
                    iterator.remove();
                    int humanCount = countHumans();
                    if (players.size() == 1 || humanCount == 0) {
                        System.out.println(players.size() == 1
                                ? "There is only one player left in the game!"
                                : "There are no more human players left in the game!");
                        System.out.println("🎮 Game Over 🎮");
                        Helper.sleep(1500);
                        GamePhaseRenderer.goodbyeMessage();
                        // System.exit(0);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public int countHumans() {
        int count = 0;
        for (Player player : players) {
            if (player.isHuman()) {
                count++;
            }
        }
        return count;
    }
}
