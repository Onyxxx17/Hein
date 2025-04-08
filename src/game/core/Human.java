package game.core;

import game.renderer.CardRenderer;
import java.util.*;

/**
 * Represents a human player in the game.
 * Handles player input for card selection and provides human-specific display methods.
 */
public class Human extends Player {

    // ============================ Constructor ============================

    /**
     * Constructor for the Human player.
     *
     * @param name The name of the player.
     */
    public Human(String name) {
        super(name);
    }

    // ============================ Gameplay Methods ============================

    /**
     * Allows the human player to play a card from their hand into the parade.
     *
     * @param parade The parade where the card will be added.
     * @param scanner Scanner object for user input.
     */
    @Override
    public void playCard(Parade parade, Scanner scanner) {
        if (closedCards.isEmpty()) {
            System.out.println("No cards left to play!");
            return;
        }

        showClosedCards();
        int cardIndex = getValidCardSelection(scanner, closedCards.size());
        Card selectedCard = closedCards.remove(cardIndex - 1);
        parade.addCard(selectedCard);

        System.out.println(name + " played: ");
        System.out.println(selectedCard);
    }

    /**
     * Allows the player to choose two cards from their hand to be moved to open
     * cards before scoring.
     *
     * @param parade The parade object (not used here but passed for consistency).
     * @param scanner Scanner object for user input.
     */
    @Override
    public void finalPlay(Parade parade, Scanner scanner) {
        // System.out.println("\nBefore we go to score calculation, choose two cards from your hand to add to open cards for scoring!\n");

        for (int selection = 1; selection <= 2; selection++) {
            showClosedCards();
            int cardIndex = getValidCardSelection(scanner, closedCards.size());
            Card selectedCard = closedCards.remove(cardIndex - 1);


            CardRenderer.setDisplayMode(true);
            openCards.computeIfAbsent(selectedCard.getColor(), key -> new ArrayList<>()).add(selectedCard);
            System.out.println(selectedCard + " is added to " + name + "'s Open Cards!\n");
        }
    }

    // ============================ Input Handling ============================

    /**
     * Gets a valid card selection from the user within the given range.
     * 
     * @param scanner Scanner for user input.
     * @param maxCards The maximum number of cards to choose from.
     * @return The validated card index (1-based).
     */
    private int getValidCardSelection(Scanner scanner, int maxCards) {
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

    // ============================ Display Methods ============================

    /**
     * Displays the player's closed cards in ASCII art format arranged
     * horizontally, with selection numbers above each card.
     */
    public void showClosedCards() {
        if (closedCards.isEmpty()) {
            System.out.println("Your hand is empty.");
            return;
        }

        CardRenderer.setDisplayMode(false);
        System.out.println("🎴 Your hand:");

        // Print selection numbers above each card
        for (int i = 0; i < closedCards.size(); i++) {
            System.out.printf("    [%d]      ", i + 1);
        }
        System.out.println();

        // Display cards in ASCII art format
        displayCardsHorizontally(closedCards);
    }

    /**
     * Displays a list of cards horizontally in ASCII art format.
     * 
     * @param cards The list of cards to display.
     */
    private void displayCardsHorizontally(List<Card> cards) {
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

    @Override
    public boolean isHuman() {
        return true;
    }
}
