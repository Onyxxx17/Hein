package game.core;

import java.util.*;

public abstract class Player {

    // ============================ Attributes ============================
    protected String name;
    protected ArrayList<Card> closedCards; // Cards in hand
    protected Map<String, ArrayList<Card>> openCards; // Open cards grouped by color
    protected int score = 0;

    // ============================ Constructor ============================
    public Player(String name) {
        this.name = name;
        this.closedCards = new ArrayList<>();
        this.openCards = new HashMap<>();
    }

    // ============================ Abstract Methods ============================
    public abstract void playCard(Parade parade, Scanner scanner);
    public abstract void finalPlay(Parade parade, Scanner scanner);

    // ============================ Card Initialization ============================
    /**
     * Initializes the player's closed cards by drawing 5 cards from the deck.
     *
     * @param deck The deck to draw cards from.
     */
    public void initializeClosedCards(Deck deck) {
        for (int i = 0; i < 5; i++) {
            closedCards.add(deck.removeCardFromDeck());
        }
    }

    // ============================ Parade Interaction ============================

    /**
     * Draws cards from the parade based on the last played card.
     * Cards are added to the player's open cards if they match the color
     * or have a lower value than the played card.
     *
     * @param parade The parade from which cards are drawn.
     */
    public void drawCardsFromParade(Parade parade) {
        ArrayList<Card> currentCardsInParade = parade.getCards();
        if (currentCardsInParade.isEmpty()) {
            System.out.println(name + " receives no cards this round (parade is empty).");
            return;
        }

        Card playedCard = currentCardsInParade.get(currentCardsInParade.size() - 1);
        ArrayList<Card> cardsToRemove = new ArrayList<>();

        // Calculate index range for card selection
        int toCount = Math.max(currentCardsInParade.size() - playedCard.getValue() - 1, 0);

        for (int i = 0; i < toCount; i++) {
            Card card = currentCardsInParade.get(i);
            if (card.getColor().equals(playedCard.getColor()) || playedCard.getValue() >= card.getValue()) {
                cardsToRemove.add(card);
            }
        }

        // Remove cards from parade and add them to openCards
        currentCardsInParade.removeAll(cardsToRemove);
        for (Card card : cardsToRemove) {
            openCards.computeIfAbsent(card.getColor(), key -> new ArrayList<>()).add(card);
        }

        // Display results
        if (!cardsToRemove.isEmpty()) {
            System.out.print(name + " receives: ");
            for (Card card : cardsToRemove) {
                System.out.print(card + " ");
            }
            System.out.println();
        } else {
            System.out.println(name + " receives no cards this round.");
        }
    }

    // ============================ Deck Interaction ============================
    /**
     * Draws a single card from the deck and adds it to closed cards.
     *
     * @param deck The deck from which the card is drawn.
     */
    public void drawCardFromDeck(Deck deck) {

        //Does nothing when the deck became empty
        if (!deck.getCards().isEmpty()) {
            closedCards.add(deck.removeCardFromDeck());
        } else{
            System.out.println("The deck is empty. It should end right now");
        }
    }

    // ============================ Display Methods ============================
    /**
     * Displays the player's open cards grouped by color.
     */
    public void showOpenCards() {
        if (openCards.isEmpty()) {
            System.out.println(name + " has no open cards.");
            return;
        }

        System.out.println(name + "'s Open Cards:");
        for (Map.Entry<String, ArrayList<Card>> entry : openCards.entrySet()) {
            System.out.print(entry.getKey() + " cards: ");
            for (int i = 0; i < entry.getValue().size(); i++) {
                System.out.print(entry.getValue().get(i));
                if (i < entry.getValue().size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // ============================ Score Calculation ============================
    /**
     * Calculates the player's score based on the total value of open cards.
     */
    public void calculateScore() {
        score = 0;
        for (ArrayList<Card> cards : openCards.values()) {
            for (Card card : cards) {
                score += card.getValue();
            }
        }
    }

    /**
     * Counts the total number of cards in the player's open cards.
     *
     * @return The total count of open cards.
     */
    public int getColorCount() {
        int totalCount = 0;
        for (ArrayList<Card> cards : openCards.values()) {
            totalCount += cards.size();
        }
        return totalCount;
    }

    // ============================ Getter Methods ============================
    public String getName() {
        return name;
    }

    public ArrayList<Card> getClosedCards() {
        return closedCards;
    }

    public Map<String, ArrayList<Card>> getOpenCards() {
        return openCards;
    }

    public int getScore() {
        return score;
    }

    public int getTotalOpenCards() {
        int totalCards = 0;
        for (List<Card> cards : this.getOpenCards().values()) {
            totalCards += cards.size();
        }
        return totalCards;
    }

    // ============================ Setter Methods ============================
    public void setScore(int score) {
        this.score = score;
    }
}
