package game.core;

import game.renderer.PlayerRenderer;
import game.utils.Constants;
import java.util.*;

public class Computer extends Player {
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
     * @param scanner Scanner object (not used, but kept for consistency with
     * Human player).
     */
    @Override
    public void playCard(Parade parade, Scanner scanner) {

        // Not necessary but kept for consistency
        if (closedCards.isEmpty()) {
            throw new IllegalStateException(name + " has no cards left to play!");
        }

        // Select a random card from closedCards
        int index = Constants.RANDOM.nextInt(closedCards.size());
        Card selectedCard = closedCards.remove(index);

        // Add the selected card to the parade
        PlayerRenderer.showComputerThinking(name);
        PlayerRenderer.showPlayedCard(selectedCard, name);

        parade.addCard(selectedCard);
    }

    /**
     * Allows the computer to randomly select two cards from its hand to move to
     * open cards before scoring.
     *
     * @param parade The parade object (not used here but passed for
     * consistency).
     * @param scanner Scanner object (not used for the computer player).
     */
    @Override
    public void finalPlay(Scanner scanner) {
        for (int i = 0; i < Constants.FINAL_PLAY_MOVES; i++) {

            // Not necessary but kept for consistency
            if (closedCards.isEmpty()) {
                throw new IllegalStateException(name + " has no cards left to play!");
            }

            // Select a random card
            int index = Constants.RANDOM.nextInt(closedCards.size());
            Card selectedCard = closedCards.remove(index);

            // Display and add to open cards
            PlayerRenderer.showComputerThinking(name);
            PlayerRenderer.showPlayedCard(selectedCard, name);
            PlayerRenderer.showCardAddedToOpenCards(name, selectedCard);

            openCards.computeIfAbsent(selectedCard.getColor(), key -> new ArrayList<>()).add(selectedCard);
        }
    }
}
