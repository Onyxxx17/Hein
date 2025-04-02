package game.gameplay;

import game.core.*;
import game.utils.Helper;
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

    private final ArrayList<Player> players; // List of players participating in the game
    private final Deck deck; // The deck of cards used in the game
    private static final int TOTAL_COLORS = 6; // Number of unique colors in the game
    private static final int MIN_DIFFERENCE_FOR_TWO_PLAYERS = 2; // Minimum card count difference for two-player flipping
    private static final int FLIPPED_CARD_VALUE = 1; // Value assigned to flipped cards

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
     * Checks if the game has reached an end condition. The game ends when: 1.
     * The deck is empty 2. A player has collected at least one card of each
     * color
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
                boolean haveAllColors = true;
                for (List<Card> list : openCards.values()) {
                    if (list.isEmpty()) {
                        haveAllColors = false;
                        break;
                    }
                }
                if (haveAllColors) {
                    System.out.println("\n‼️" + p.getName() + " has collected all 6 color cards ‼️\n");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines which player(s) have the maximum number of cards of a given
     * color. Implements special rules for two-player games where the difference
     * in count must be at least 2 for flipping.
     *
     * @param color The color to check
     * @return A list of players with the maximum number of cards of the
     * specified color
     */
    public ArrayList<Player> checkPlayerWithMaxCards(String color) {
        int max = 0;
        ArrayList<Player> playersOut = new ArrayList<>();
        boolean isAllSameNoOfCards = true;
        int firstCards = 0;

        if (players.get(0).getOpenCards() != null && players.get(0).getOpenCards().containsKey(color)) {
            firstCards = players.get(0).getOpenCards().get(color).size();
        }

        // Find players with the most cards of the given color
        for (Player p : players) {
            int numCards = p.getOpenCards().getOrDefault(color, new ArrayList<>()).size();
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
     * Applies special rules for two-player games when determining card
     * flipping. Cards are only flipped if the difference in count is at least
     * 2.
     *
     * @param color The color being evaluated
     * @param playersOut The list of players who may qualify for flipping
     */
    public void check2PlayerConditions(String color, ArrayList<Player> playersOut) {
        Player player1 = players.get(0);
        Player player2 = players.get(1);

        int count1 = player1.getOpenCards().getOrDefault(color, new ArrayList<>()).size();
        int count2 = player2.getOpenCards().getOrDefault(color, new ArrayList<>()).size();
        int difference = Math.abs(count1 - count2);

        if (difference < MIN_DIFFERENCE_FOR_TWO_PLAYERS && difference != 0) {
            System.out.println("The difference between the two players is not enough to flip cards. It needs to be at least 2.");
            playersOut.clear();
        }
    }

    /**
     * Flips cards for players with the majority of each color according to the
     * game rules.
     */
    public void flipCards() {
        Map<Player, ArrayList<Card>> flippedCards = new HashMap<>();

        for (String color : Deck.CARD_COLORS) {
            ArrayList<Player> maxPlayers = checkPlayerWithMaxCards(color);
            displayMaxPlayersForColor(color, players);

            for (Player player : maxPlayers) {
                ArrayList<Card> cardsToFlip = player.getOpenCards().get(color);
                flippedCards.putIfAbsent(player, new ArrayList<>());
                for (Card card : cardsToFlip) {
                    card.setValue(FLIPPED_CARD_VALUE);
                    flippedCards.get(player).add(card);
                }
            }
        }
        showFlippedCards(flippedCards);
    }

    /**
     * Displays flipped cards after applying game rules.
     *
     * @param flippedCards A mapping of players to their flipped cards
     */
    public void showFlippedCards(Map<Player, ArrayList<Card>> flippedCards) {
        for (Player p : players) {
            System.out.println("\n" + p.getName() + " open cards after flipping:");
            for (String color : Deck.CARD_COLORS) {
                List<Card> openCards = p.getOpenCards().getOrDefault(color, new ArrayList<>());
                System.out.print(color + " cards: ");
                if (openCards.isEmpty()) {
                    System.out.print("No Cards");
                }
                for (Card card : openCards) {
                    boolean contains = flippedCards.getOrDefault(p, new ArrayList<>()).contains(card);
                    if (card.getValue() == 1 && contains) {
                        System.out.print("[" + color + "] ");
                    } else {
                        System.out.print(card + " ");
                    }
                }
                System.out.println();
            }
        }
    }

    public void displayMaxPlayersForColor(String color, List<Player> maxPlayers) {
        String colorCode = Card.getColorCode(color); // Get the color code for the given color

        if (maxPlayers.isEmpty()) {
            displayNoMaxPlayersForColor(color);
            return;
        }

        displayMaxPlayersForColorList(color, maxPlayers, colorCode);
    }

    public void displayNoMaxPlayersForColor(String color) {
        System.out.println("🎭 Player(s) with most " + Card.getColorCode(color) + color + " cards: \u001B[0mNone\n");
    }

    public void displayMaxPlayersForColorList(String color, List<Player> maxPlayers, String colorCode) {
        System.out.print("🎉 Player(s) that will flip " + colorCode + color + " cards: \u001B[0m");
        for (int i = 0; i < maxPlayers.size(); i++) {
            System.out.print("\u001B[1m" + maxPlayers.get(i).getName() + "\u001B[0m");
            if (i != maxPlayers.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(" 🎉\n");
        Helper.sleep(1200);
    }

    /**
     * Calculates final scores for all players.
     */
    public void calculateFinalScores() {
        for (Player player : players) {
            player.calculateScore();
        }
    }

    /**
     * Determines the winner based on scores and tiebreaker rules.
     */
    public void determineWinner() {
        Collections.sort(players, new PlayerComparator());
        ArrayList<Player> potentialWinners = addPotentialWinners();

        if (potentialWinners.size() > 1) {
            System.out.println("There are multiple players with the highest score. Tiebreaker rules apply.");
            for (Player p : players) {
                System.out.println(p.getName() + " collected");
                System.out.println("Total cards : " + p.getTotalOpenCards() + " cards");
                System.out.println("Total colors : " + p.getOpenCards().size() + " colors\n");
            }
        }
    }

    /**
     * Adds players with the highest scores to the list of potential winners.
     *
     * @return List of players who may be the winner
     */
    public ArrayList<Player> addPotentialWinners() {
        ArrayList<Player> potentialWinners = new ArrayList<>();
        potentialWinners.add(players.get(0));
        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).getScore() == players.get(0).getScore()) {
                potentialWinners.add(players.get(i));
            }
        }
        return potentialWinners;
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
