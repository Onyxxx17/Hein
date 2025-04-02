package game.core;

import java.util.ArrayList;

public class Parade {

    private final ArrayList<Card> cards;
    private static final int INITIAL_CARDS = 6; // Externalized magic number
    private static final int CARDS_PER_LINE = 7; // Fixed number of cards per row

    // ============================ Constructor ============================

    /**
     * Constructor for Parade. Initializes an empty parade.
     */
    public Parade() {
        this.cards = new ArrayList<>();
    }

    // ============================ Initialization Methods ============================

    /**
     * Initializes the parade with 6 cards from the deck.
     *
     * @param deck The deck from which cards are drawn to form the initial parade.
     */
    public void initializeParade(Deck deck) {
        for (int i = 0; i < INITIAL_CARDS; i++) { // Adds 6 cards to the parade
            Card card = deck.removeCardFromDeck();
            cards.add(card);
        }
    }

    // ============================ Parade Operations ============================

    /**
     * Adds a card to the end of the parade.
     *
     * @param card The card to be added.
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Checks if the parade is empty.
     *
     * @return true if there are no cards in the parade, false otherwise.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Removes and returns a card from a specific index in the parade.
     *
     * @param index The index of the card to remove.
     * @return The removed card if the index is valid, otherwise returns null.
     */
    public Card removeCard(int index) {
        if (index >= 0 && index < cards.size()) {
            return cards.remove(index);
        }
        return null; // Invalid index
    }

    // ============================ Display Methods ============================

    /**
     * Displays the current parade of cards in a fixed horizontal format.
     */
    public void showParade() {
        Card.setDisplayMode(false);
        System.out.println("Parade (Starts from left):");

        int totalCards = cards.size();
        int index = 0;

        while (index < totalCards) {
            int end = Math.min(index + CARDS_PER_LINE, totalCards);
            ArrayList<Card> chunk = new ArrayList<>(cards.subList(index, end));

            // Prepare card lines
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

            // Print lines
            for (StringBuilder line : cardLines) {
                System.out.println(line.toString().stripTrailing()); // Trim trailing spaces
            }

            index = end; // Move to the next chunk
        }
    }

    // ============================ Getter Methods ============================

    /**
     * Retrieves the list of cards in the parade.
     *
     * @return The ArrayList of cards in the parade.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }
}
