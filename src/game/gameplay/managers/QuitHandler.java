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
                        if (players.size() == 1) {
                            System.out.println("\n==============================================");
                            System.out.println("║ 🎮 " + Constants.BOLD + "\t\t   GAME OVER!" + Constants.RESET + " \t\t   🎮║");
                            System.out.println("==============================================");
                            System.out.println("║ There is only " + Constants.BOLD + "one player" + Constants.RESET + " left in the game! ║");
                            System.out.println("==============================================\n");
                        } else {
                            System.out.println("\n====================================================");
                            System.out.println("║ 🎮 " + Constants.BOLD + "\t\t  GAME OVER!" + Constants.RESET + " \t\t\t 🎮║");
                            System.out.println("====================================================");
                            System.out.println("║ There are no more " + Constants.BOLD + "human players" + Constants.RESET + " left in the game!║");
                            System.out.println("====================================================\n");
                        }

                        Helper.sleep(1500);
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
