package coreclasses;

import java.util.ArrayList;

public class Parade {

    private final ArrayList<Card> CARDS;

    /**
     * Constructor for Parade. Initializes the parade with 6 cards from the
     * deck.
     *
     * @param deck The deck from which cards are drawn to form the initial
     * parade.
     */
    public Parade() {
        this.CARDS = new ArrayList<>();
        // for (int i = 0; i <= 5; i++) { // Adds 6 cards to the parade
        //     Card card = deck.removeCardFromDeck();
        //     cards.add(card);
        // }
    }

    public void initializeParade(Deck deck) {
        for (int i = 0; i <= 5; i++) { // Adds 6 cards to the parade
            Card card = deck.removeCardFromDeck();
            CARDS.add(card);
        }
    }
    /**
     * Adds a card to the end of the parade.
     *
     * @param card The card to be added.
     */
    public void addCard(Card card) {
        CARDS.add(card);
    }

    /**
     * Checks if the parade is empty.
     *
     * @return true if there are no cards in the parade, false otherwise.
     */
    public boolean isEmpty() {
        return CARDS.isEmpty();
    }

    /**
     * Removes and returns a card from a specific index in the parade.
     *
     * @param index The index of the card to remove.
     * @return The removed card if the index is valid, otherwise returns null.
     */
    public Card removeCard(int index) {
        if (index >= 0 && index < CARDS.size()) {
            return CARDS.remove(index);
        }
        return null; // Invalid index
    }

    /**
     * Displays the current parade of cards.
     */
    public void showParade() {
        System.out.print("Current Parade: ");
        for (Card card : CARDS) {
            System.out.print(card + " "); // Uses color formatting from toString()
        }
        System.out.println();
    }

    /**
     * Retrieves the list of cards in the parade.
     *
     * @return The ArrayList of cards in the parade.
     */
    public ArrayList<Card> getCards() {
        return CARDS;
    }
}
