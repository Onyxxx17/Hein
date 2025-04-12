package game.gameplay.managers;

import game.core.*;
import game.renderer.GameFlowRenderer;
import game.utils.Constants;
import java.util.*;

public class CardFlipper {
    private final List<Player> players;

    public CardFlipper(List<Player> players) {
        this.players = players;
    }

    public Map<Player, List<Card>> flipCards() {
        Map<Player, List<Card>> flippedCards = new HashMap<>();

        for (String color : Constants.COLORS) {
            List<Player> maxPlayers = checkPlayerWithMaxCards(color);
            GameFlowRenderer.displayMaxPlayersForColor(color, maxPlayers);

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

    /**
     * @return A list of players with the maximum number of cards of the
     * specified color
     */
    public List<Player> checkPlayerWithMaxCards(String color) {
        int max = 0;
        List<Player> playersOut = new ArrayList<>();
        boolean isAllSameNoOfCards = true;
        int firstPlayerCards = 0;

        if (players.get(0).getOpenCards() != null && players.get(0).getOpenCards().containsKey(color)) {
            firstPlayerCards = players.get(0).getOpenCards().get(color).size();
        }

        // Find players with the most cards of the given color
        for (Player p : players) {
            int numCardsOfPlayer = p.getOpenCards().getOrDefault(color, new ArrayList<>()).size();
            if (firstPlayerCards != numCardsOfPlayer) {
                isAllSameNoOfCards = false;
            }
            if (numCardsOfPlayer > max) {
                max = numCardsOfPlayer;
                playersOut.clear(); // Reset as a new max is found
                playersOut.add(p);
            } else if (numCardsOfPlayer == max) {
                playersOut.add(p);
            }
        }

        GameFlowRenderer.logMaxCardsForColor(color, max);

        // Special rule for two-player game
        if (players.size() == 2) {
            check2PlayerConditions(color, playersOut);
        }

        // No flipping if all players have the same number of cards
        if (isAllSameNoOfCards) {
            GameFlowRenderer.logNoFlippingDueToTie(color);
            playersOut.clear();
        }

        return playersOut;
    }

    /**
     * Applies special rules for two-player games when determining card
     * flipping. Cards are only flipped if the difference in count is at least
     * 2.
     *
     * @param color The color being evaluated
     * @param playersOut The list of players who may qualify for flipping
     */
    public void check2PlayerConditions(String color, List<Player> playersOut) {
        Player player1 = players.get(0);
        Player player2 = players.get(1);

        int count1 = player1.getOpenCards().getOrDefault(color, new ArrayList<>()).size();
        int count2 = player2.getOpenCards().getOrDefault(color, new ArrayList<>()).size();
        int difference = Math.abs(count1 - count2);

        if (difference < Constants.MIN_DIFFERENCE_FOR_TWO_PLAYERS && difference != 0) {
            GameFlowRenderer.show2PlayerRules();
            playersOut.clear();
        }
    }
}
