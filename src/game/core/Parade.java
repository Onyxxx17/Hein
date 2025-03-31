package game.core;

import java.util.ArrayList;
import java.util.List;

public class Parade {

    private final ArrayList<Card> Cards;

    /**
     * Constructor for Parade. Initializes the parade with 6 cards from the
     * deck.
     *
     * @param deck The deck from which cards are drawn to form the initial
     * parade.
     */
    public Parade() {
        this.Cards = new ArrayList<>();
        // for (int i = 0; i <= 5; i++) { // Adds 6 cards to the parade
        //     Card card = deck.removeCardFromDeck();
        //     cards.add(card);
        // }
    }

    public void initializeParade(Deck deck) {
        for (int i = 0; i <= 5; i++) { // Adds 6 cards to the parade
            Card card = deck.removeCardFromDeck();
            Cards.add(card);
        }
    }

    /**
     * Adds a card to the end of the parade.
     *
     * @param card The card to be added.
     */
    public void addCard(Card card) {
        Cards.add(card);
    }

    /**
     * Checks if the parade is empty.
     *
     * @return true if there are no cards in the parade, false otherwise.
     */
    public boolean isEmpty() {
        return Cards.isEmpty();
    }

    /**
     * Removes and returns a card from a specific index in the parade.
     *
     * @param index The index of the card to remove.
     * @return The removed card if the index is valid, otherwise returns null.
     */
    public Card removeCard(int index) {
        if (index >= 0 && index < Cards.size()) {
            return Cards.remove(index);
        }
        return null; // Invalid index
    }

    /**
     * Displays the current parade of cards in a fixed horizontal format.
     */
    public void showParade() {
        Card.setDisplayMode(false);
        System.out.println("Parade (Starts from left):");

        int cardsPerLine = 7; // Fixed number of cards per row
        int totalCards = Cards.size();
        int index = 0;

        while (index < totalCards) {
            int end = Math.min(index + cardsPerLine, totalCards);
            List<Card> chunk = Cards.subList(index, end);

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

            // Print lines, trimming trailing spaces
            for (StringBuilder line : cardLines) {
                if (line.length() >= 3) {
                    line.setLength(line.length() - 3); // Remove trailing spaces
                }
                System.out.println(line);
            }

            index = end; // Move to the next chunk
        }
    }

    /**
     * Retrieves the list of cards in the parade.
     *
     * @return The ArrayList of cards in the parade.
     */
    public ArrayList<Card> getCards() {
        return Cards;
    }
}
