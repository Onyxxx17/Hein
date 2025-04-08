package game.core;

import game.renderer.CardRenderer;
import game.renderer.HumanRenderer;
import game.utils.Constants;
import java.util.*;


public class Human extends Player {

    public Human(String name) {
        super(name);
    }

    @Override
    public void playCard(Parade parade, Scanner scanner) {
        if (closedCards.isEmpty()) {
            System.out.println("No cards left to play!");
            return;
        }

        HumanRenderer.showClosedCards(this);
        int cardIndex = HumanRenderer.getValidCardSelection(scanner, closedCards.size());
        Card selectedCard = closedCards.remove(cardIndex - 1);
        parade.addCard(selectedCard);

        System.out.println(name + " played: ");
        System.out.println(selectedCard);
    }

    @Override
    public void finalPlay(Parade parade, Scanner scanner) {
        for (int selection = 1; selection <= Constants.FINAL_PLAY_MOVES; selection++) {
            HumanRenderer.showClosedCards(this);
            int cardIndex = HumanRenderer.getValidCardSelection(scanner, closedCards.size());
            Card selectedCard = closedCards.remove(cardIndex - 1);

            CardRenderer.setDisplayMode(true);
            openCards.computeIfAbsent(selectedCard.getColor(), key -> new ArrayList<>()).add(selectedCard);
            System.out.println(selectedCard + " is added to " + name + "'s Open Cards!\n");
        }
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}
