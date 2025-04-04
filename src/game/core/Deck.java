package game.core;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    // ============================ Constants ============================

    /** Define available colors for cards. */
    public static final String[] CARD_COLORS = {"Blue", "Green", "Grey", "Orange", "Purple", "Red"};

    // ============================ Instance Variables ============================

    /** List to hold all cards in the deck. */
    private final ArrayList<Card> cards;

    // ============================ Constructor ============================

    /**
     * Constructor to initialize the deck with cards of different colors and values.
     * Each color will have cards numbered from 0 to 10.
     */
    public Deck() {
        this.cards = new ArrayList<>();
        for (String color : CARD_COLORS) {
            for (int i = 0; i <= 10; i++) { // Adding cards numbered 0 to 10
                cards.add(new Card(color, i));
            }
        }
    }

    // ============================ Deck Operations ============================

    /**
     * Shuffles the deck to randomize card order.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Removes and returns the top card from the deck.
     * Assumes the deck is not empty.
     *
     * @return The removed card from the deck, or null if the deck is empty.
     */
    public Card removeCardFromDeck() {
        return cards.isEmpty() ? null : cards.remove(cards.size() - 1);
    }

    /**
     * Checks if the deck is empty.
     *
     * @return true if the deck is empty, false otherwise.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    // ============================ Getters ============================

    /**
     * Returns the list of cards in the deck.
     *
     * @return An ArrayList of Card objects.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Returns the number of cards remaining in the deck.
     *
     * @return The size of the deck.
     */
    public int size() {
        return cards.size();
    }
}
