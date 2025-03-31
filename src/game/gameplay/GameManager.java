package game.gameplay;

import game.core.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the core game rules, win conditions, and state for the Parade card
 * game. Handles logic for determining game end, card flipping, and winner
 * selection.
 */
public class GameManager {

    private final ArrayList<Player> players;
    private final Deck deck;
    private static final int TOTAL_COLORS = 6;
    private static final int MIN_DIFFERENCE_FOR_TWO_PLAYERS = 2;
    private static final int FLIPPED_CARD_VALUE = 1;

    /**
     * Creates a new GameManager with the specified players and deck.
     *
     * @param players The list of players participating in the game
     * @param deck The deck of cards to be used in the game
     */
    public GameManager(ArrayList<Player> players, Deck deck) {
        this.players = players;
        this.deck = deck;
    }

    /**
     * Checks if the game has reached an end condition. The game ends when
     * either: 1. The deck is empty, or 2. A player has collected at least one
     * card of each color
     *
     * @return true if the game should end, false otherwise
     */
    public boolean checkEndGame() {
        if (deck == null || deck.getCards() == null || deck.getCards().isEmpty()) {
            System.out.println("No more cards are left in the deck");
            return true;
        }
        for (Player p : players) {
            Map<String, ArrayList<Card>> openCards = p.getOpenCards();
            if (openCards.size() == TOTAL_COLORS) {
                boolean haveAllColour = true;
                for (List<Card> list : p.getOpenCards().values()) {
                    if (list.isEmpty()) {
                        haveAllColour = false;
                        break;
                    }
                }
                if (haveAllColour) {
                    System.out.println(p.getName() + " has collected all 6 colour cards!!");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines which player(s) have the maximum number of cards of a given
     * color. Implements special rules for the two-player game variant where the
     * difference in card count must be at least 2 for card flipping to occur.
     *
     * @param colour The color to check
     * @return A list of players who have the maximum number of cards of the
     * specified color, or an empty list if no player qualifies for card
     * flipping
     */
    public ArrayList<Player> checkPlayerWithMaxCards(String color) {
        int max = 0;
        ArrayList<Player> playersOut = new ArrayList<>();
        Boolean isAllSameNoOfCards = true;
        int firstCards = 0;
        if (players.get(0).getOpenCards() != null && players.get(0).getOpenCards().containsKey(color)) {
            firstCards = players.get(0).getOpenCards().get(color).size();
        }
        // Find players with maximum cards of the given color
        for (Player p : players) {
            int numCards = 0;
            if (p.getOpenCards() != null && p.getOpenCards().containsKey(color)) {
                numCards = p.getOpenCards().get(color).size();
            }
            if (firstCards != numCards) {
                isAllSameNoOfCards = false;
            }
            if (numCards > max) {
                max = numCards;
                playersOut.clear(); // Reset as a new max is found
                playersOut.add(p);
            } else if (numCards == max) {
                playersOut.add(p);
            }
        }
        System.out.println("Max cards for " + color + " is " + max);
        // Special rule for two-player game
        if (players.size() == 2) {
            check2PlayerConditions(color, playersOut);
        }
        // No flipping if all players have the same number of cards
        if (isAllSameNoOfCards) {
            System.out.println("All players have the same number of cards. No cards flipped for " + color);
            playersOut.clear();
        }
        return playersOut;
    }

    /**
     * Applies special rules for the two-player game variant to determine card
     * flipping eligibility. In a two-player game, cards are only flipped if the
     * difference in card count is at least 2, with special handling for cases
     * where one or both players have no cards of a color.
     *
     * @param colour The color being evaluated
     * @param playersOut The list of players who currently qualify for card
     * flipping (may be modified by this method)
     */
    public void check2PlayerConditions(String color, ArrayList<Player> playersOut) {
        Boolean player1Contains = players.get(0).getOpenCards().containsKey(color);
        Boolean player2Contains = players.get(1).getOpenCards().containsKey(color);
        // Handle various cases for the two-player variant
        if (!player1Contains && !player2Contains) {
            System.out.println("Neither player has any " + color + " cards.");
            playersOut.clear();
        } else if (!player1Contains || !player2Contains) {
            // One player has no cards of the given color
            if (!player1Contains && players.get(1).getOpenCards().get(color).size() == 1) {
                System.out.println(
                        "Player 1 has no " + color + " cards, and Player 2 has only 1 card. Difference is not enough.");
                playersOut.clear();
            } else if (!player2Contains && players.get(0).getOpenCards().get(color).size() == 1) {
                System.out.println(
                        "Player 2 has no " + color + " cards, and Player 1 has only 1 card. Difference is not enough.");
                playersOut.clear();
            }
        } else {
            // Both players have cards of the given colour
            int cards0 = players.get(0).getOpenCards().get(color).size();
            int cards1 = players.get(1).getOpenCards().get(color).size();
            int difference = Math.abs(cards0 - cards1);
            if (difference < MIN_DIFFERENCE_FOR_TWO_PLAYERS && difference != 0) {
                System.out.println(
                        "The difference between the two players is not enough to flip cards. It needs to be at least 2.");
                playersOut.clear();
            }
        }
    }

    /**
     * Flips cards for players with the majority of each color according to the
     * game rules. When a player's card is flipped, its value is reduced to 1
     * point. After flipping, displays each player's open cards with their
     * updated values.
     */
    public void flipCards() {
        // For each color, find players with the most cards and flip those cards
        Map<Player, ArrayList<Card>> flippedCards = new HashMap<>();

        for (String color : Deck.CARD_COLORS) {
            ArrayList<Player> maxPlayers = checkPlayerWithMaxCards(color);

            displayMaxPlayersForColor(color, maxPlayers);

            // Flip cards (set value to 1) for players with most cards of this color
            for (Player player : maxPlayers) {
                ArrayList<Card> cardsToFlip = player.getOpenCards().get(color);
                // Initialize the list for the player if not already present
                if (!flippedCards.containsKey(player)) {
                    flippedCards.put(player, new ArrayList<>());
                }
                // Add the flipped cards to the player's list
                for (Card card : cardsToFlip) {
                    // System.out.println(card + "'s value is set to 1");
                    card.setValue(FLIPPED_CARD_VALUE);
                    flippedCards.get(player).add(card);
                }
            }
        }
        showFlippedCards(flippedCards);
    }

    public void showFlippedCards(Map<Player, ArrayList<Card>> flippedCards) {
        for (Player p : players) {
            System.out.println("\n" + p.getName() + " open cards after flipping:");
            for (String color : Deck.CARD_COLORS) {
                List<Card> openCards = p.getOpenCards().getOrDefault(color, new ArrayList<>());
                System.out.print(color + " cards: ");
                if (openCards.isEmpty()) {
                    System.out.print("No Cards");
                }
                for (int i = 0; i < openCards.size(); i++) {
                    Card card = openCards.get(i);
                    boolean contains = isCardFlipped(card, flippedCards);
                    if (card.getValue() == 1 && contains) {
                        System.out.print("[" + color + "] ");
                    } else {
                        System.out.print(card);
                        if (i != openCards.size() - 1) {
                            System.out.print(", ");
                        }
                    }

                    if (i != openCards.size() - 1) {
                        System.out.print(contains ? " -- " : "");
                    }
                }
                System.out.println();
            }
        }
    }

    public boolean isCardFlipped(Card cardToCheck, Map<Player, ArrayList<Card>> flippedCards) {
        for (ArrayList<Card> cards : flippedCards.values()) {
            if (cards.contains(cardToCheck)) {
                return true; // Card found in the HashMap
            }
        }
        return false; // Card not found
    }

    public static void displayMaxPlayersForColor(String color, List<Player> maxPlayers) {
        String colorCode = Card.getColorCode(color); // Get the color code for the given color

        // If there are no players with the most cards of this color
        if (maxPlayers.isEmpty()) {
            System.out.println("🎭 Player(s) with most " + colorCode + color + " cards: \u001B[0mNone\n");
            return;
        }

        // Display the players with the most cards of this color
        System.out.print("🎉 Player(s) with most " + colorCode + color + " cards: \u001B[0m");

        for (int i = 0; i < maxPlayers.size(); i++) {
            System.out.print("\u001B[1m" + maxPlayers.get(i).getName() + "\u001B[0m"); // Bold player name

            // Add a comma if there are more players
            if (i != maxPlayers.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(" 🎉\n"); // End with an emoji for a fun effect
    }

    public void calculateFinalScores() {
        // Consolidated scoring logic
        for (Player p : players) {
            p.calculateScore();
            System.out.println(p.getName() + " final score: " + p.getScore());
        }
    }
    
    public void determineWinner() {
        Collections.sort(players, new PlayerComparator());
        List<Player> potentialWinners = new ArrayList<>();

        // Add the first player (highest score after sorting)
        potentialWinners.add(players.get(0));

        if (potentialWinners.size() > 1) {
            System.out.println("There are " + potentialWinners.size() + " players with the same highest score");
            System.out.println("The winner will be decided based on 2 conditions ");
            System.out.println("1. The player with the least collected cards");
            System.out.println(
                    "2. If the number of cards are the same, the player with the least number of colors will win\n");
            for (Player p : players) {
                System.out.println(p.getName() + "collected");
                System.out.println("Total cards : " + p.getTotalOpenCards() + " cards");
                System.out.println("Total colors : " + p.getOpenCards().size() + " colors\n");
            }

        }
    }

    /**
     * Gets the list of players in the game.
     *
     * @return ArrayList of players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Gets the deck being used in the game.
     *
     * @return The game deck
     */
    public Deck getDeck() {
        return deck;
    }

}
