package coreclasses;

import java.util.ArrayList;

public class Parade {
    private ArrayList<Card> cards;
    /**
     * Constructor for Parade. Initializes the parade with 6 cards from the deck.
     *
     * @param deck The deck from which cards are drawn to form the initial parade.
     */
    public Parade() {
        this.cards = new ArrayList<>();
    }

    public void add5Cards(Deck deck){
        for (int i = 0; i <= 5; i++) { // Adds 6 cards to the parade
            Card card = deck.removeCardFromDeck();
            cards.add(card);
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

    /**
     * Displays the current parade of cards.
     */
    public void showParade() {
        System.out.println("\nCurrent Parade: " + cards);
    }

    /**
     * Retrieves the list of cards in the parade.
     *
     * @return The ArrayList of cards in the parade.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }
}