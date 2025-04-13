package game.core;

import game.exceptions.InvalidInputException;
import game.renderer.CardUI;
import game.renderer.PlayerRenderer;
import game.utils.Constants;
import java.util.*;

/**
 * Represents a human player in the game. The human player can play cards from
 * their hand into the parade and perform final play moves.
 */
public class Human extends Player {

    // ============================ Constructor ============================
    public Human(String name) {
        super(name);
    }

    // ============================ Gameplay Methods ============================
    /**
     * Allows the human player to select and play a card from their hand into
     * the parade.
     *
     * @param parade The parade where the selected card will be added.
     * @param scanner Scanner used for capturing the player's card selection
     * input.
     * @throws IllegalStateException if the player's hand is empty when
     * attempting to play.
     */
    @Override
    public void playCard(Parade parade, Scanner scanner) {

        // Will never be empty but kept for consistency
        if (closedCards.isEmpty()) {
            throw new IllegalStateException(name + " has no cards left to play!");
        }

        int cardIndex = getValidCardSelection(scanner, closedCards.size());
        Card selectedCard = closedCards.remove(cardIndex - 1);
        parade.addCard(selectedCard);

        PlayerRenderer.showPlayedCard(selectedCard, name);
    }

    /**
     * Allows the human player to select and move cards from their closed cards
     * to open cards during the final play phase of the game.
     *
     * @param scanner Scanner used to capture user input for card selection.
     */

    @Override
    public void finalPlay(Scanner scanner) {
        for (int selection = 1; selection <= Constants.FINAL_PLAY_MOVES; selection++) {
            PlayerRenderer.showClosedCards(this);
            int cardIndex = getValidCardSelection(scanner, closedCards.size());
            Card selectedCard = closedCards.remove(cardIndex - 1);

            CardUI.setSimpleDisplayMode(true);
            openCards.computeIfAbsent(selectedCard.getColor(), key -> new ArrayList<>()).add(selectedCard);
            PlayerRenderer.showPlayedCard(selectedCard, name);
            PlayerRenderer.showCardAddedToOpenCards(name, selectedCard);
        }
    }

    /**
     * Determines if the player is a human player.
     *
     * @return true, indicating that this player is human.
     */
    @Override
    public boolean isHuman() {
        return true;
    }

    // ============================= Helpers ===============================
    /**
     * Handles and validates user input for selecting a card.
     */
    private int getValidCardSelection(Scanner scanner, int maxCards) {
        while (true) {
            System.out.print("Enter the number of the card to play (1-" + maxCards + "): ");
            try {
                String input = scanner.nextLine();
                int index = Integer.parseInt(input);  // Throws NumberFormatException

                if (index >= 1 && index <= maxCards) {
                    return index;
                } else {
                    throw new InvalidInputException();
                }
            } catch (NumberFormatException | InvalidInputException e) {
                System.out.println("❌ Invalid input! Enter a number a number (1-" + maxCards + ").\n");
            }
        }
    }
}
