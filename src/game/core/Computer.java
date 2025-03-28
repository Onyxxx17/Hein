package game.core;
import java.util.*;

public class Computer extends Player {

    // Random number generator for AI decision-making
    private static final Random RANDOM = new Random(); // final should be uppercase?

    /**
     * Constructor for the Computer player.
     *
     * @param name The name of the computer player.
     */
    public Computer(String name) {
        super(name);
    }

    /**
     * Allows the computer player to randomly play a card from its hand into the parade.
     *
     * @param parade  The parade where the card will be added.
     * @param scanner Scanner object (not used, but kept for consistency with Human player).
     */
    @Override
    public void playCard(Parade parade, Scanner scanner) {
        // Check if there are cards left to play
        if (closedCards.isEmpty()) {
            System.out.println(name + " has no cards left to play!");
            return;
        }

        // Select a random card from closedCards
        int index = RANDOM.nextInt(closedCards.size());
        Card selectedCard = closedCards.remove(index); // Remove the selected card

        // Add the selected card to the parade
        parade.addCard(selectedCard);
        System.out.println(name + " played: " + selectedCard);
    }

    /**
     * Allows the computer to randomly select two cards from its hand to move to open cards before scoring.
     *
     * @param parade  The parade object (not used here but passed for consistency).
     * @param scanner Scanner object (not used for the computer player).
     */
    @Override
    public void finalPlay(Parade parade, Scanner scanner) {
        // Select two random cards from closedCards
        for (int i = 0; i < 2; i++) {
            if (closedCards.isEmpty()) {
                System.out.println(name + " has no more cards to move to open cards.");
                return;
            }
            
            int index = RANDOM.nextInt(closedCards.size());
            Card selectedCard = closedCards.remove(index); // Remove the selected card

            // Add the selected card to open cards
            System.out.println(selectedCard + " is added to " + name + "'s Open Cards!\n");
            openCards.computeIfAbsent(selectedCard.getColor(), key -> new ArrayList<>()).add(selectedCard);
        }
    }
}