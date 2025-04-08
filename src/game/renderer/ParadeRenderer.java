package game.renderer;

import game.core.*;
import game.utils.*;
import java.util.*;

public class ParadeRenderer {

    /**
     * Displays the current parade of cards in a fixed horizontal format.
     */
    public static void showParade(Parade parade) {
        List<Card> cards = parade.getCards();
        CardUI.setSimpleDisplayMode(false);
        System.out.println("Parade (Starts from left):");

        int totalCards = cards.size();
        int index = 0;

        while (index < totalCards) {
            int end = Math.min(index + Constants.CARDS_PER_LINE, totalCards);
            List<Card> chunk = new ArrayList<>(cards.subList(index, end));

            // Prepare card lines and print them
            StringBuilder[] cardLines = prepareCardLines(chunk);
            printCardLines(cardLines);

            index = end; // Move to the next chunk
        }
    }

    /**
     * Prepares the card lines for display from the chunk of cards.
     */
    private static StringBuilder[] prepareCardLines(List<Card> chunk) {
        StringBuilder[] cardLines = new StringBuilder[7];
        for (int i = 0; i < cardLines.length; i++) {
            cardLines[i] = new StringBuilder();
        }

        // Build each line for the chunk
        for (Card card : chunk) {
            String[] parts = card.toString().split("\n");
            for (int i = 0; i < parts.length; i++) {
                cardLines[i].append(parts[i]).append("   "); // Add spacing
            }
        }

        return cardLines;
    }

    /**
     * Prints the card lines to the console.
     */
    private static void printCardLines(StringBuilder[] cardLines) {
        // Print lines
        for (StringBuilder line : cardLines) {
            System.out.println(line.toString().stripTrailing()); // Trim trailing spaces
        }
    }
}
