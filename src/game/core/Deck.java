package game.core;

import game.utils.Constants;
import java.util.*;

/**
 * Deck class represents the collection of cards in the game. It provides methods
 * to shuffle the deck, draw cards from the deck, and check if the deck is empty.
 */
public class Deck {

    // ============================ Instance Variables ============================
    /**
     * List to hold all cards in the deck.
     */
    private final List<Card> cards;

    // ============================ Constructor ============================
    /**
     * Constructor to initialize the deck with cards of different colors and
     * values. Each color will have cards numbered from 0 to 10.
     */
    public Deck() {
        this.cards = new ArrayList<>();
        for (String color : Constants.COLORS) {
            for (int i = Constants.MINVALUEOFCARD; i <= Constants.MAXVALUEOFCARD; i++) { // Adding cards numbered 0 to 10
                cards.add(new Card(color, i));
            }
        }
    }

    // ============================ Deck Operations ============================
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card removeCardFromDeck() {
        return cards.isEmpty() ? null : cards.remove(cards.size() - 1);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    // ============================ Getters ============================
    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public int size() {
        return cards.size();
    }
}
