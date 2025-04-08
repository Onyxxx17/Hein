package game.core;

import game.utils.Constants;
import java.util.*;

public class Parade {

    private final List<Card> cards;
    private final Deck deck;
    // ============================ Constructor ============================

    /**
     * Constructor for Parade. Initializes an empty parade.
     */
    public Parade(Deck deck) {
        this.deck = deck;
        this.cards = new ArrayList<>();
    }
    // ============================ Parade Operations ============================
    public void initializeParade() {
        for (int i = 0; i < Constants.INITIAL_CARDS_OF_PARADE; i++) {
            if (deck.isEmpty()) {
                throw new IllegalStateException("Not enough cards in the deck to initialize a Parade");
            }
            cards.add(deck.removeCardFromDeck());
        }
    }
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

    // ============================ Getter Methods ============================

    /**
     * Retrieves the list of cards in the parade.
     *
     * @return The ArrayList of cards in the parade.
     */
    public List<Card> getCards() {
        return cards;
    }
}
