package coreclasses;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    // Define available colors for cards
    public static final String[] CARD_COLORS = {"Blue", "Green", "Grey", "Orange", "Purple", "Red"};

    // List to hold all cards in the deck
    private final ArrayList<Card> cards;

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
     * @return The removed card from the deck.
     */
    public Card removeCardFromDeck() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        }
        return null; // Return null if the deck is empty
    }

    // /**
    //  * Checks if the deck is empty.
    //  *
    //  * @return true if the deck is empty, false otherwise.
    //  */
    // public Boolean isEmpty() {
    //     return cards.size() == 0;
    // }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int size() {
        return cards.size();
    }
}