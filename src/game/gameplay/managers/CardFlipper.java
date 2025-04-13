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
            List<Player> maxPlayers = findPlayersWithMaxCards(color);
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

    private int getCardCountForColor(Player player, String color) {
        return player.getOpenCards().getOrDefault(color, Collections.emptyList()).size();
    }

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