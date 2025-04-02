package game.core;

import game.utils.Helper;
import java.util.*;

public class Computer extends Player {

    // ============================ Instance Variables ============================

    /** Random number generator for AI decision-making. */
    private static final Random random = new Random();

    // ============================ Constructor ============================

    /**
     * Constructor for the Computer player.
     *
     * @param name The name of the computer player.
     */
    public Computer(String name) {
        super(name);
    }

    // ============================ Gameplay Methods ============================

    /**
     * Allows the computer player to randomly play a card from its hand into the
     * parade.
     *
     * @param parade The parade where the card will be added.
     * @param scanner Scanner object (not used, but kept for consistency with Human player).
     */
    @Override
    public void playCard(Parade parade, Scanner scanner) {
        if (closedCards.isEmpty()) {
            System.out.println(name + " has no cards left to play!");
            return;
        }

        // Select a random card from closedCards
        int index = random.nextInt(closedCards.size());
        Card selectedCard = closedCards.remove(index);

        // Add the selected card to the parade
        System.out.print(name + " is thinking");
        Helper.loading();
        System.out.println("\n" + name + " played: ");
        System.out.println(selectedCard);

        parade.addCard(selectedCard);
    }

    /**
     * Allows the computer to randomly select two cards from its hand to move to
     * open cards before scoring.
     *
     * @param parade The parade object (not used here but passed for consistency).
     * @param scanner Scanner object (not used for the computer player).
     */
    @Override
    public void finalPlay(Parade parade, Scanner scanner) {
        for (int i = 0; i < 2; i++) {
            if (closedCards.isEmpty()) {
                System.out.println(name + " has no more cards to move to open cards.");
                return;
            }

            // Select a random card
            Card.setDisplayMode(false);
            int index = random.nextInt(closedCards.size());
            Card selectedCard = closedCards.remove(index);

            // Display and add to open cards
            System.out.print(name + " is thinking");
            Helper.loading();
            System.out.println("\n" + name + " played: ");
            System.out.println(selectedCard);

            Card.setDisplayMode(true);
            System.out.println(selectedCard + " is added to " + name + "'s Open Cards!\n");
            openCards.computeIfAbsent(selectedCard.getColor(), key -> new ArrayList<>()).add(selectedCard);
        }
    }
}
