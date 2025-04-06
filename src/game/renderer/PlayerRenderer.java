package game.renderer;

import game.core.*;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerRenderer {

    /**
     * Displays cards received by a player in ASCII art format.
     */
    public static void displayReceivedCards(Player player, ArrayList<Card> receivedCards) {
        if (receivedCards.isEmpty()) {
            System.out.printf("%s receives no cards this round.%n", player.getName());
            return;
        }
        
        System.out.printf("%s receives:%n", player.getName());
        renderCardGroup(receivedCards);
    }

    /**
     * Displays a player's open cards grouped by color.
     */
    public static void showOpenCards(Player player) {
        Map<String, ArrayList<Card>> openCards = player.getOpenCards();
        
        if (openCards.isEmpty()) {
            System.out.printf("%s has no open cards.%n%n", player.getName());
            return;
        }
        
        CardRenderer.setDisplayMode(true);
        System.out.printf("🎴 %s's Open Cards:%n", player.getName());
        
        openCards.forEach((color, cards) -> 
            System.out.printf("%s cards: %s%n",
                color,
                cards.stream()
                    .map(Card::toString)
                    .collect(Collectors.joining(", ")))
        );
        
        System.out.println();
    }

    /**
     * Shared helper for rendering a group of cards.
     */
    private static void renderCardGroup(ArrayList<Card> cards) {
        CardRenderer.setDisplayMode(false);
        StringBuilder[] lines = new StringBuilder[7];
        
        // Initialize lines
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new StringBuilder();
        }

        // Build card display
        for (Card card : cards) {
            String[] parts = card.toString().split("\n");
            for (int i = 0; i < parts.length; i++) {
                lines[i].append(parts[i]).append("   ");
            }
        }

        // Print the assembled display
        for (StringBuilder line : lines) {
            System.out.println(line.toString().stripTrailing());
        }
    }
}