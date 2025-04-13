package game.core;

import game.exceptions.InvalidInputException;
import game.renderer.PlayerRenderer;
import game.renderer.CardUI;
import game.utils.Constants;
import java.util.*;

public class Human extends Player {

    public Human(String name) {
        super(name);
    }

    @Override
    public void playCard(Parade parade, Scanner scanner) {

        //Will never be empty but kept for consistency
        if (closedCards.isEmpty()) {
            throw new IllegalStateException(name + " has no cards left to play!");
        }

        int cardIndex = getValidCardSelection(scanner, closedCards.size());
        Card selectedCard = closedCards.remove(cardIndex - 1);
        parade.addCard(selectedCard);

        PlayerRenderer.showPlayedCard(selectedCard, name);
    }

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

    @Override
    public boolean isHuman() {
        return true;
    }

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
                } else{
                    throw new InvalidInputException();
                }
            } catch (NumberFormatException | InvalidInputException e) {
                System.out.println("❌ Invalid input! Enter a number a number (1-" + maxCards + ").\n");
            }
        }
    }
}
