package coreclasses;

import java.util.*;

public abstract class Player {

    protected String name;
    protected ArrayList<Card> closedCards;
    protected Map<String, ArrayList<Card>> openCards;
    protected int score = 0;

    public Player(String name) {
        this.name = name;
        this.closedCards = new ArrayList<>();
        this.openCards = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public abstract void playCard(Parade parade, Scanner scanner);

    public abstract void finalPlay(Parade parade, Scanner scanner);

    //Initilize closedCards
    //Not sure if this can be applied in GameManager. This is Temporary
    public void InitialClosedCards(Deck d) {
        for (int i = 0; i < 5; i++) {
            Card card = d.removeCardFromDeck();
            closedCards.add(card);
        }
    }

    // Method to draw cards to openCards from the parade based on playedCard(Last Card in Parade)'s card
    public void drawCardsFromParade(Parade parade) {
        ArrayList<Card> currentCardsInParade = parade.getCards();
        if (currentCardsInParade.isEmpty()) {
            System.out.println(name + " receives no cards this round (parade is empty).");
            return;
        }
    
        Card cardPlayedByPlayer = currentCardsInParade.get(currentCardsInParade.size() - 1);
    
        // Create a list to store cards to be removed (prevents ConcurrentModificationException)
        ArrayList<Card> cardsToRemove = new ArrayList<>();
    
        // Check the value of the player's card in parade
        int cardNumber = cardPlayedByPlayer.getValue();
        int toCount = (currentCardsInParade.size() - cardNumber - 1) > 0 ? (currentCardsInParade.size() - cardNumber - 1) : 0;
    
        // Process the cards to remove (based on color and value comparisons)
        if (toCount > 0) {
            if (cardPlayedByPlayer.getValue() == 0) {
                // If the played card is 0, remove all cards up to toCount
                for (int i = 0; i < toCount; i++) {
                    cardsToRemove.add(currentCardsInParade.get(i));
                }
            } else {
                // Otherwise, remove cards based on color and value
                for (int i = 0; i < toCount; i++) {
                    Card card = currentCardsInParade.get(i);
                    if (card.getColor().equals(cardPlayedByPlayer.getColor()) || cardPlayedByPlayer.getValue() >= card.getValue()) {
                        cardsToRemove.add(card);
                    }
                }
            }
        }
    
        // Print the result of the card removal
        if (!cardsToRemove.isEmpty()) {
            System.out.print(name + " receives: ");
            for (Card card : cardsToRemove) {
                System.out.print(card + " ");
            }
            System.out.println();
        } else {
            System.out.println(name + " receives no cards this round.");
        }
    
        // Remove the cards from the parade and add them to openCards
        currentCardsInParade.removeAll(cardsToRemove);
        for (Card card : cardsToRemove) {
            openCards.computeIfAbsent(card.getColor(), key -> new ArrayList<>()).add(card);
        }
    }
    // Draw a single card from the deck

    public void drawCardFromDeck(Deck deck) {
        Card card = deck.removeCardFromDeck();
        closedCards.add(card);
    }

    public void showOpenCards() {
        if (openCards.isEmpty()) {
            System.out.println(name + " has no open cards.");
        } else {
            System.out.println(name + "'s Open Cards:");
            for (Map.Entry<String, ArrayList<Card>> entry : openCards.entrySet()) {
                String color = entry.getKey();
                List<Card> cards = entry.getValue();
    
                // Print color name first
                System.out.print(color + " cards: ");
    
                // Print all cards for this color
                for (int i = 0; i < cards.size(); i++) {
                    System.out.print(cards.get(i));
                    if (i < cards.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
        }
        System.out.println();
    }
//Calculation Methods

    // Calculate score based on openCards
    public void calculateScore() {
        for (Map.Entry<String, ArrayList<Card>> entry : openCards.entrySet()) {
            for (Card card : entry.getValue()) {
                score += card.getValue();
            }
        }
    }

    // Count total number of cards in openCards
    public int getColorCount() {

        int totalCount = 0;
        for (ArrayList<Card> cards : openCards.values()) {
            totalCount += cards.size();
        }
        return totalCount;
    }
//Getter methods

    public ArrayList<Card> getClosedCards() {
        return closedCards;
    }

    public Map<String, ArrayList<Card>> getOpenCards() {
        return openCards;
    }

}
