package game.gameplay;

import java.util.ArrayList;

import game.core.Player;

/**
 * Displays a podium of top players at the end of the game like Kahoot
 */
public class Podium {
    
    /**
     * Displays the podium with player rankings based on their scores.
     * List of players sorted by score (lowest to highest for Parade game)
     */
    public static void displayPodium(ArrayList<Player> players) {
        if (players == null || players.isEmpty()) {
            System.out.println("No players to display on the podium.");
            return;
        }
        
        // Clear the console for a cleaner display
        System.out.println("\n\n");
        
        System.out.println("===============================");
        System.out.println("        FINAL RESULTS         ");
        System.out.println("===============================");
        
        // Display simple ASCII podium
        System.out.println("\n       PODIUM       ");
        
        // First place (winner)
        System.out.println("        1st         ");
        System.out.println("      ┌─────┐       ");
        System.out.println("      │     │       ");
        System.out.println("      │  " + getInitial(players.get(0).getName()) + "  │       ");
        
        // Handle different numbers of players
        if (players.size() >= 3) {
            // Show both 2nd and 3rd places
            System.out.println("┌─────┼─────┼─────┐");
            System.out.println("│     │     │     │");
            System.out.println("│  " + getInitial(players.get(1).getName()) + "  │     │  " + 
                              getInitial(players.get(2).getName()) + "  │");
            System.out.println("│ 2nd │     │ 3rd │");
            System.out.println("└─────┴─────┴─────┘");
        } else if (players.size() == 2) {
            // Show only 2nd place, not 3rd
            System.out.println("┌─────┼─────│     ");
            System.out.println("│     │     │     ");
            System.out.println("│  " + getInitial(players.get(1).getName()) + "  │     │     ");
            System.out.println("│ 2nd │     │     ");
            System.out.println("└─────┴─────┘     ");
        } 
        
        // Display detailed results for all players
        System.out.println("\n--- PLAYER RANKINGS ---");
        
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            String medal = "";
            
            if (i == 0) {
              medal = "🥇";
            } else if (i == 1) {
              medal = "🥈";
            } else if (i == 2) {
              medal = "🥉";
            }
            
            System.out.printf("%s #%d: %s - %d points%n", 
                    medal, (i + 1), player.getName(), player.getScore());
        }
        
        // Display winner message
        System.out.println("\n🎉 CONGRATULATIONS 🎉");
        System.out.println("🏆 " + players.get(0).getName() + " WINS THE GAME! 🏆");
        System.out.println("\nThanks for playing Parade!");
        System.out.println("===============================");
    }
    
    /**
     * Gets the first letter/initial of a name for the podium display
     */
    private static String getInitial(String name) {
        if (name == null || name.isEmpty()) {
            return " ";
        }
        return name.substring(0, 1).toUpperCase();
    }
}