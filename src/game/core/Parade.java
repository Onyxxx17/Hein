package game.core;

import game.utils.Constants;
import java.util.*;

/**
 * Parade class represents the parade of cards in the game. It provides methods
 * to initialize the parade, add cards, check if it's empty, and retrieve
 * eligible cards for removal based on the last played card.
 */
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

    // ======================== Parade Operations ==========================
    /**
     * Initializes the parade with a specified number of cards from the deck.
     * @throws IllegalStateException if there are not enough cards in the deck.
     */
    public void initializeParade() {
        for (int i = 0; i < Constants.INITIAL_CARDS_OF_PARADE; i++) {
            if (deck.isEmpty()) {
                throw new IllegalStateException("Not enough cards in the deck to initialize a Parade");
            }
            cards.add(deck.removeCardFromDeck());
        }
    }

    public void addCard(Card card) {
        cards.add(card);
    }

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

        // Count the number of cards that will not be eligible for removal
        int toCount = Math.max(cards.size() - playedCard.getValue() - 1, 0);
        List<Card> eligibleCards = new ArrayList<>();

        // Add cards that match the color or are less than the played card's value to eligibleCards
        for (int i = 0; i < toCount; i++) {
            Card card = cards.get(i);
            if (card.getColor().equals(playedCard.getColor()) || playedCard.getValue() >= card.getValue()) {
                eligibleCards.add(card);
            }
        }
        return eligibleCards;
    }

    public void removeCards(List<Card> cardsToRemove) {
        cards.removeAll(cardsToRemove);
    }

    // ========================= Getter Methods ============================

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
}
