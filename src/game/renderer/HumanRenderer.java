package game.renderer;

import game.core.Card;
import game.core.Human;
import java.util.List;
import java.util.Scanner;

public class HumanRenderer {

    /**
     * Displays the human player's closed cards in ASCII art format.
     */
    public static void showClosedCards(Human player) {
        List<Card> closedCards = player.getClosedCards();
        if (closedCards.isEmpty()) {
            System.out.println("Your hand is empty.");
            return;
        }

        CardUI.setSimpleDisplayMode(false);
        System.out.println("🎴 Your hand:");

        for (int i = 0; i < closedCards.size(); i++) {
            System.out.printf("    [%d]      ", i + 1);
        }
        System.out.println();

        displayCardsHorizontally(closedCards);
    }

    /**
     * Helper method to display a list of cards horizontally in ASCII art.
     */
    private static void displayCardsHorizontally(List<Card> cards) {
        StringBuilder[] cardLines = new StringBuilder[7];
        for (int i = 0; i < cardLines.length; i++) {
            cardLines[i] = new StringBuilder();
        }

        for (Card card : cards) {
            String[] cardParts = card.toString().split("\n");
            for (int i = 0; i < cardParts.length; i++) {
                cardLines[i].append(cardParts[i]).append("   ");
            }
        }

        for (StringBuilder line : cardLines) {
            System.out.println(line.toString().stripTrailing());
        }
    }

    /**
     * Handles and validates user input for selecting a card.
     */
    public static int getValidCardSelection(Scanner scanner, int maxCards) {
        while (true) {
            System.out.print("Enter the number of the card to play (1-" + maxCards + "): ");
            if (scanner.hasNextInt()) {
                int index = scanner.nextInt();
                if (index >= 1 && index <= maxCards) {
                    return index;
                }
            } else {
                scanner.next(); // Consume invalid input
            }
            System.out.println("Invalid input! Enter a number between 1 and " + maxCards + ".\n");
        }
    }
}
