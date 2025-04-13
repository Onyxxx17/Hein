package game.core;

import game.renderer.PlayerRenderer;
import game.utils.Constants;
import java.util.*;

/**
 * Represents a computer player in the game. The computer player can play cards
 * randomly from its hand into the parade and perform final play moves.
 */
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
     * Executes the computer player's final play moves by randomly selecting and
     * playing cards from its hand into the open cards.
     * @param scanner Scanner object (not used, but kept for consistency with
     * Human player).
     * @throws IllegalStateException if the computer player's hand is empty when
     * attempting to play.
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
