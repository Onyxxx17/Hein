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
     * Returns the last card played to the parade.
     * @throws IllegalStateException if the parade is empty.
     */
    public Card getLastPlayedCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Parade is empty.");
        }
        return cards.get(cards.size() - 1);
    }

    /**
     * Retrieves cards eligible for removal based on the last played card.
     * @param playedCard The card triggering the removal logic.
     */
    public List<Card> getEligibleCards(Card playedCard) {
        int toCount = Math.max(cards.size() - playedCard.getValue() - 1, 0);
        List<Card> eligibleCards = new ArrayList<>();

        for (int i = 0; i < toCount; i++) {
            Card card = cards.get(i);
            if (card.getColor().equals(playedCard.getColor()) || playedCard.getValue() >= card.getValue()) {
                eligibleCards.add(card);
            }
        }
        return eligibleCards;
    }

    /**
     * Removes specified cards from the parade.
     * @param cardsToRemove The list of cards to remove.
     */
    public void removeCards(List<Card> cardsToRemove) {
        cards.removeAll(cardsToRemove);
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
