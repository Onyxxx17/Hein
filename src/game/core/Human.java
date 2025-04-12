package game.core;

import game.renderer.*;
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

        int cardIndex = PlayerRenderer.getValidCardSelection(isHuman(),scanner, closedCards.size());
        Card selectedCard = closedCards.remove(cardIndex - 1);
        parade.addCard(selectedCard);

        PlayerRenderer.showPlayedCard(selectedCard, name);
    }

    @Override
    public void finalPlay(Scanner scanner) {
        for (int selection = 1; selection <= Constants.FINAL_PLAY_MOVES; selection++) {
            int cardIndex = PlayerRenderer.getValidCardSelection(isHuman(),scanner, closedCards.size());
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
}
