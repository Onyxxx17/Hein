package game.gameplay.managers;

import game.core.*;
import game.renderer.GameFlowRenderer;
import game.utils.Constants;
import java.util.*;

/**
 * CardFlipper is responsible for flipping cards in the game.
 * It determines which players have the maximum number of cards of a specific color
 * and flips those cards.
 */
public class CardFlipper {
    // ============================ Instance Variables ============================
    /**
     * List of players in the game.
     */
    private final List<Player> players;

    // ============================ Constructor ============================

    /**
     * Constructs a CardFlipper with the list of players.
     *
     * @param players The list of players in the game.
     */
    public CardFlipper(List<Player> players) {
        this.players = players;
    }

    // ======================== Instance Methods (Flipping) ========================

    public Map<Player, List<Card>> flipCards() {
        Map<Player, List<Card>> flippedCards = new HashMap<>();

        for (String color : Constants.COLORS) {
            List<Player> maxPlayers = findPlayersWithMaxCards(color);
            GameFlowRenderer.showMaxPlayersForColor(color, maxPlayers);

            for (Player player : maxPlayers) {
                List<Card> cardsToFlip = player.getOpenCards().get(color);
                flippedCards.putIfAbsent(player, new ArrayList<>());
                for (Card card : cardsToFlip) {
                    card.setValue(Constants.FLIPPED_CARD_VALUE);
                    flippedCards.get(player).add(card);
                }
            }
        }
        return flippedCards;
    }

    // ========================== Helper Methods (Private Instance Methods) ===========================

    /**
     * @return A list of players with the maximum number of cards of the
     * specified color
     */
    private List<Player> findPlayersWithMaxCards(String color) {
        int max = 0;
        List<Player> maxPlayers = new ArrayList<>();
        boolean allPlayersTied = true;
        int firstPlayerCount = getCardCountForColor(players.get(0), color);

        for (Player player : players) {
            int count = getCardCountForColor(player, color);
            if (count != firstPlayerCount) allPlayersTied = false;

            if (count > max) {
                max = count;
                maxPlayers.clear();
                maxPlayers.add(player);
            } else if (count == max) {
                maxPlayers.add(player);
            }
        }

        GameFlowRenderer.showMaxCardsForColor(color,max);
        if (allPlayersTied) {
            GameFlowRenderer.showNoFlippingDueToTie(color);
            return Collections.emptyList();
        }

        if (players.size() == 2) {
            applyTwoPlayerRule(color, maxPlayers);
        }

        return maxPlayers;
    }

    /**
     * Returns the number of open cards of the specified color in the player's
     * hand.
     *
     * @param player The player whose open cards are being counted.
     * @param color  The color of the cards being counted.
     * @return The number of cards of the specified color in the player's hand.
     */
    private int getCardCountForColor(Player player, String color) {
        return player.getOpenCards().getOrDefault(color, Collections.emptyList()).size();
    }

    /**
     * If there are only two players and the difference in the number of open cards of the
     * specified color is less than MIN_DIFFERENCE_FOR_TWO_PLAYERS and not 0,
     * then this method applies the two player rule: it clears the list of max players and
     * shows the two player rule message.
     *
     * @param color      The color of the cards being counted.
     * @param maxPlayers The list of players with the max number of cards of the specified
     *                   color.
     */
    private void applyTwoPlayerRule(String color, List<Player> maxPlayers) {
        Player p1 = players.get(0);
        Player p2 = players.get(1);
        int difference = Math.abs(
            getCardCountForColor(p1, color) - getCardCountForColor(p2, color)
        );

        if (difference < Constants.MIN_DIFFERENCE_FOR_TWO_PLAYERS && difference != 0) {
            GameFlowRenderer.show2PlayerRules();
            maxPlayers.clear();
        }
    }
}
