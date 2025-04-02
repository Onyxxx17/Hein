package game.core;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Abstract base class representing a player in the game.
 * Handles card management, interactions with the parade, and score calculation.
 */
public abstract class Player {

    // ============================ Attributes ============================
    /**
     * The name of the player.
     */
    protected String name;
    
    /**
     * Cards in the player's hand (not visible to other players).
     */
    protected ArrayList<Card> closedCards;
    
    /**
     * Open cards grouped by color (visible to all players).
     */
    protected Map<String, ArrayList<Card>> openCards;
    
    /**
     * The player's current score.
     */
    protected int score;

    /**
     * Number of cards to draw during initialization.
     */
    private static final int INITIAL_HAND_SIZE = 5;

    // ============================ Constructor ============================
    /**
     * Creates a new player with the specified name.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.closedCards = new ArrayList<>();
        this.openCards = new HashMap<>();
        this.score = 0;
    }

    // ============================ Abstract Methods ============================
    /**
     * Plays a card from the player's hand to the parade.
     * Implementation will differ between human and AI players.
     *
     * @param parade The parade to play the card to.
     * @param scanner Scanner for user input (for human players).
     */
    public abstract void playCard(Parade parade, Scanner scanner);

    /**
     * Handles the player's final play when the game is ending.
     * Implementation will differ between human and AI players.
     *
     * @param parade The parade to play the card to.
     * @param scanner Scanner for user input (for human players).
     */
    public abstract void finalPlay(Parade parade, Scanner scanner);

    // ============================ Card Initialization ============================
    /**
     * Initializes the player's closed cards by drawing cards from the deck.
     *
     * @param deck The deck to draw cards from.
     */
    public void initializeClosedCards(Deck deck) {
        for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
            Card card = deck.removeCardFromDeck();
            if (card != null) {
                closedCards.add(card);
            }
        }
    }

    // ============================ Parade Interaction ============================
    /**
     * Draws cards from the parade based on the last played card. Cards are
     * added to the player's open cards if they match the color or have a lower
     * value than the played card.
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
        addCardsToOpenCards(cardsToRemove);

        // Display results
        displayReceivedCards(cardsToRemove);
    }
    
    /**
     * Adds a list of cards to the player's open cards, organizing by color.
     * 
     * @param cards List of cards to add to open cards.
     */
    private void addCardsToOpenCards(List<Card> cards) {
        for (Card card : cards) {
            openCards.computeIfAbsent(card.getColor(), key -> new ArrayList<>()).add(card);
        }
    }
    
    /**
     * Displays the cards received by the player in ASCII art format.
     * 
     * @param receivedCards The cards received by the player.
     */
    private void displayReceivedCards(List<Card> receivedCards) {
        if (receivedCards.isEmpty()) {
            System.out.println(name + " receives no cards this round.");
            return;
        }
        
        System.out.println(name + " receives:");

        // Set display mode to ASCII art for the received cards
        Card.setDisplayMode(false);

        // Prepare card lines (7 lines for standard ASCII card)
        StringBuilder[] cardLines = new StringBuilder[7];
        for (int i = 0; i < cardLines.length; i++) {
            cardLines[i] = new StringBuilder();
        }

        // Build each line of the card display
        for (Card card : receivedCards) {
            String[] cardParts = card.toString().split("\n");
            for (int i = 0; i < cardParts.length; i++) {
                cardLines[i].append(cardParts[i]).append("   ");
            }
        }

        // Print the assembled card lines
        for (StringBuilder line : cardLines) {
            System.out.println(line.toString());
        }
    }

    // ============================ Deck Interaction ============================
    /**
     * Draws a single card from the deck and adds it to closed cards.
     *
     * @param deck The deck from which the card is drawn.
     */
    public void drawCardFromDeck(Deck deck) {
        Card card = deck.removeCardFromDeck();
        if (card != null) {
            closedCards.add(card);
        }
    }

    // ============================ Display Methods ============================
    /**
     * Displays the player's open cards grouped by color.
     */
    public void showOpenCards() {
        if (openCards.isEmpty()) {
            System.out.println(name + " has no open cards.\n");
            return;
        }
        
        Card.setDisplayMode(true);
        System.out.println("🎴 " + name + "'s Open Cards:");
        
        openCards.forEach((color, cards)
                -> System.out.println(color + " cards: "
                        + cards.stream()
                                .map(Card::toString)
                                .collect(Collectors.joining(", "))
                )
        );
        
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
    /**
     * Gets the player's name.
     * 
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's closed cards (hand).
     * 
     * @return The list of cards in the player's hand.
     */
    public ArrayList<Card> getClosedCards() {
        return closedCards;
    }

    /**
     * Gets the player's open cards grouped by color.
     * 
     * @return Map of color to list of cards.
     */
    public Map<String, ArrayList<Card>> getOpenCards() {
        return openCards;
    }

    /**
     * Gets the player's current score.
     * 
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the total number of open cards the player has.
     * 
     * @return The total count of open cards.
     */
    public int getTotalOpenCards() {
        return openCards.values().stream()
                .mapToInt(List::size)
                .sum();
    }

    // ============================ Setter Methods ============================
    /**
     * Sets the player's score. (Typically for showing edge case scenarios)
     * 
     * @param score The new score value.
     */
    public void setScore(int score) {
        this.score = score;
    }
}