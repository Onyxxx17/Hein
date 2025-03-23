package gameplay;

import coreclasses.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Manages the core game rules, win conditions, and state for the Parade card
 * game. Handles logic for determining game end, card flipping, and winner
 * selection.
 */
public class GameManager {

    private ArrayList<Player> players;
    private Deck deck;
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
    public ArrayList<Player> checkPlayerWithMaxCards(String colour) {
        int max = 0;
        ArrayList<Player> playersOut = new ArrayList<>();
        Boolean isAllSameNoOfCards = true;

        int firstCards = 0;
        if (players.get(0).getOpenCards() != null && players.get(0).getOpenCards().containsKey(colour)) {
            firstCards = players.get(0).getOpenCards().get(colour).size();
        }

        // Find players with maximum cards of the given color
        for (Player p : players) {
            if (p == null || p.getOpenCards() == null || !p.getOpenCards().containsKey(colour)) {
                continue;
            }

            int numCards = p.getOpenCards().get(colour).size();

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
        System.out.println("Max cards for " + colour + " is " + max);

        // Special rule for two-player game
        if (players.size() == 2) {
            check2PlayerConditions(colour, playersOut);
        }

        // No flipping if all players have the same number of cards
        if (isAllSameNoOfCards) {
            System.out.println("All players have the same number of cards. No cards flipped for " + colour);
            playersOut.clear();
        }
        return playersOut;
    }

    /**
 * Applies special rules for the two-player game variant to determine card flipping eligibility.
 * In a two-player game, cards are only flipped if the difference in card count is at least 2,
 * with special handling for cases where one or both players have no cards of a color.
 *
 * @param colour The color being evaluated
 * @param playersOut The list of players who currently qualify for card flipping (may be modified by this method)
 */
public void check2PlayerConditions(String colour, ArrayList<Player> playersOut) {
    Boolean player1Contains = players.get(0).getOpenCards().containsKey(colour);
    Boolean player2Contains = players.get(1).getOpenCards().containsKey(colour);

    // Handle various cases for the two-player variant
    if (!player1Contains && !player2Contains) {
        System.out.println("Neither player has any " + colour + " cards.");
        playersOut.clear();
    } else if (!player1Contains || !player2Contains) {
        // One player has no cards of the given colour
        if (!player1Contains && players.get(1).getOpenCards().get(colour).size() == 1) {
            System.out.println("Player 1 has no " + colour + " cards, and Player 2 has only 1 card. Difference is not enough.");
            playersOut.clear();
        } else if (!player2Contains && players.get(0).getOpenCards().get(colour).size() == 1) {
            System.out.println("Player 2 has no " + colour + " cards, and Player 1 has only 1 card. Difference is not enough.");
            playersOut.clear();
        }
    } else {
        // Both players have cards of the given colour
        int cards0 = players.get(0).getOpenCards().get(colour).size();
        int cards1 = players.get(1).getOpenCards().get(colour).size();
        int difference = Math.abs(cards0 - cards1);

        if (difference < MIN_DIFFERENCE_FOR_TWO_PLAYERS) {
            System.out.println("The difference between the two players is not enough to flip cards. It needs to be at least 2.");
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
        for (String c : Deck.CARD_COLORS) {
            ArrayList<Player> maxPlayers = checkPlayerWithMaxCards(c);

            if (maxPlayers.isEmpty()) {
                continue;
            }

            // Flip cards (set value to 1) for players with most cards of this color
            for (Player p : maxPlayers) {
                for (Card card : p.getOpenCards().get(c)) {
                    System.out.println(card + "'s value is set to 1");
                    card.setValue(FLIPPED_CARD_VALUE);
                }
            }
        }

        // Display all players' cards after flipping
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
                    if (card.getValue() == 1) {
                        System.out.print("[" + color + "] ");

                        if (i != openCards.size() - 1) {
                            System.out.print("-- ");
                        }
                    } else {
                        System.out.print("[" + color + " " + card.getValue() + "] ");

                        if (i != openCards.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                }
                System.out.println();
            }
        }
    }

    /**
     * Determines the winner of the game based on player scores. Calculates each
     * player's score and sorts players using a PlayerComparator.
     *
     * @return The winning player (player with the highest score)
     */
    public Player decideWinner() {
        for (Player player : players) {
            player.calculateScore();
        }
        // Sort players using PlayerComparator (presumably sorts by score)
        Collections.sort(players, new PlayerComparator());

        // Winner will be at index 0 after sorting
        Player winner = players.get(0);
        return winner;
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
