package game.gameplay.managers;

import game.core.*;
import game.renderer.GameFlowRenderer;
import game.utils.Constants;
import java.util.*;
public class EndGameChecker {
    private final List<Player> players;
    private final Deck deck;

    public EndGameChecker(List<Player> players, Deck deck) {
        this.players = players;
        this.deck = deck;
    }

    public boolean checkEndGame() {
        if (isDeckEmpty()) {
            GameFlowRenderer.showDeckEmpty();
            return true;
        }
        return checkAllColorsCollected();
    }

    private boolean isDeckEmpty() {
        return deck == null || deck.getCards() == null || deck.getCards().isEmpty();
    }

    private boolean checkAllColorsCollected() {
        for (Player p : players) {
            if (hasAllColors(p)) {
                GameFlowRenderer.showAllColorsCollected(p);
                return true;
            }
        }
        return false;
    }

    private boolean hasAllColors(Player player) {
        Map<String, List<Card>> openCards = player.getOpenCards();
        if (openCards.size() != Constants.TOTAL_COLORS) return false;
        
        for (List<Card> cards : openCards.values()) {
            if (cards.isEmpty()) return false;
        }
        return true;
    }
}