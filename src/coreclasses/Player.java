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
        // parade.showParade();
        ArrayList<Card> currentCardsInParade = parade.getCards();
        Card cardPlayedByPlayer = currentCardsInParade.get(currentCardsInParade.size() - 1);

        // Create a list to store cards to be removed (prevents ConcurrentModificationException)
        ArrayList<Card> cardsToRemove = new ArrayList<>();

        // Check the value of the player's card in parade
        // If it is larger than the total number of cards in parade, no cards will be removed from parade
        int cardNumber = cardPlayedByPlayer.getValue();
        int toCount = (currentCardsInParade.size() - cardNumber - 1) > 0 ? (currentCardsInParade.size() - cardNumber - 1) : 0;

        // Print out the safe cards (those that can be neglected based on the rules)
        //Remove later
        // System.out.println("Safe cards:");
        // for (int i = toCount; i < currentCardsInParade.size() - 1; i++) {
        //     System.out.println(currentCardsInParade.get(i));
        // }
        // Process the cards to remove (based on color and value comparisons)
        if (toCount > 0) {
            if (cardPlayedByPlayer.getValue() == 0) {
                for (int i = 0; i < toCount; i++) {
                    cardsToRemove.add(currentCardsInParade.get(i));
                }
            } else {
                for (int i = 0; i < toCount; i++) {
                    Card card = currentCardsInParade.get(i);
                    //Player will receive cards that have the same colour as the played card or cards that have lower values
                    if ((card.getColor().equals(cardPlayedByPlayer.getColor()) || cardPlayedByPlayer.getValue() >= card.getValue()) && !card.equals(cardPlayedByPlayer)) {
                        // Add card to the removal list
                        cardsToRemove.add(card);
                    }
                }
            }

            // if (!cardsToRemove.isEmpty()) {
            //     System.out.println(name + " gets " + cardsToRemove);
            // } else {
            //     System.out.println(name + " gets no cards.");
            // }
            // Remove the cards safely from the parade and add them to openCards
            currentCardsInParade.removeAll(cardsToRemove);
            for (Card card : cardsToRemove) {
                openCards.computeIfAbsent(card.getColor(), key -> new ArrayList<>()).add(card);
            }
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
            return;
        }
    
        System.out.println(name + "'s Open Cards:");
        for (Map.Entry<String, ArrayList<Card>> entry : openCards.entrySet()) {
            String color = entry.getKey();
            List<Card> cards = entry.getValue();
    
            // Print color name first
            System.out.print(color + " cards: ");
    
            // Print all cards for this color
            for (int i = 0; i < cards.size(); i++) {
                System.out.print(cards.get(i)); 
                if (i < cards.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }
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
