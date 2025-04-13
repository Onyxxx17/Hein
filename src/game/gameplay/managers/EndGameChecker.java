package game.gameplay.managers;

import game.core.*;
import game.renderer.GameFlowRenderer;
import game.utils.Constants;
import java.util.*;

/**
 * Checks end game conditions, such as empty deck or player collecting all colors.
 */
public class EndGameChecker {
    // ============================ Instance Variables ============================
    /**
     * List of players in the game.
     */
    private final List<Player> players;

    /**
     * The deck used in the game.
     */
    private final Deck deck;

    // ============================ Constructor ============================

    /**
     * Constructs an EndGameChecker with the list of players and the deck.
     *
     * @param players The list of players in the game.
     * @param deck    The deck used in the game.
     */
    public EndGameChecker(List<Player> players, Deck deck) {
        this.players = players;
        this.deck = deck;
    }

    // ============================ Instance Method ============================

    /**
     * Checks whether the game should end, either because the deck is empty
     * or a player has collected all card colors.
     *
     * @return true if the game should end, false otherwise.
     */
    public boolean checkEndGame() {
        if (isDeckEmpty()) {
            GameFlowRenderer.showDeckEmpty();
            return true;
        }
        return checkAllColorsCollected();
    }

    // ============================ Private Helpers ============================

    /**
     * Checks if the deck is empty.
     *
     * @return true if the deck has no more cards, false otherwise.
     */
    private boolean isDeckEmpty() {
        return deck.size() == 0 || deck.isEmpty();
    }

    /**
     * Checks if any player has collected all available card colors.
     *
     * @return true if a player has all colors, false otherwise.
     */
    private boolean checkAllColorsCollected() {
        for (Player p : players) {
            if (hasAllColors(p)) {
                GameFlowRenderer.showAllColorsCollected(p);
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if a specific player has at least one card of every color.
     *
     * @param player The player to check.
     * @return true if the player has all card colors, false otherwise.
     */
    private boolean hasAllColors(Player player) {
        Map<String, List<Card>> openCards = player.getOpenCards();
        if (openCards.size() != Constants.TOTAL_COLORS) {
            return false;
        }

        for (List<Card> cards : openCards.values()) {
            if (cards.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
