package game.gameplay;

import game.core.*;
import game.renderer.*;
import game.setup.Dice;
import game.utils.Constants;
import java.util.*;

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
            GameRenderer.showDeckEmpty();
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
                    GameRenderer.showAllColorsCollected(p);
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

        GameRenderer.logMaxCardsForColor(color, max);

        // Special rule for two-player game
        if (players.size() == 2) {
            check2PlayerConditions(color, playersOut);
        }

        // No flipping if all players have the same number of cards
        if (isAllSameNoOfCards) {
            GameRenderer.logNoFlippingDueToTie(color);
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
            GameRenderer.show2PlayerRules();
            playersOut.clear();
        }
    }

    /**
     * Flips cards for players with the majority of each color according to the
     * game rules.
     */
    public Map<Player, ArrayList<Card>> flipCards() { // void flipCards() {
        Map<Player, ArrayList<Card>> flippedCards = new HashMap<>();

        for (String color : Constants.COLORS) {
            List<Player> maxPlayers = checkPlayerWithMaxCards(color);
            GameRenderer.displayMaxPlayersForColor(color, maxPlayers);

            for (Player player : maxPlayers) {
                List<Card> cardsToFlip = player.getOpenCards().get(color);
                flippedCards.putIfAbsent(player, new ArrayList<>());
                for (Card card : cardsToFlip) {
                    card.setValue(FLIPPED_CARD_VALUE);
                    flippedCards.get(player).add(card);
                }
            }
        }
        return flippedCards;
    }

    /**
     * Calculates final scores for all players.
     */
    public void calculateFinalScores() {
        for (Player player : players) {
            player.calculateScore();;
        }
    }

     private Player determineFirstWinnerByDice(ArrayList<Player> tiedPlayers) {
        if (tiedPlayers == null || tiedPlayers.isEmpty()) {
            return null; // Or handle this error case appropriately
        }

        Dice dice = new Dice();
        Player winner = null;
        int highestRoll = -1;

        System.out.println("\nInitiating dice roll to determine the initial winner among tied players...\n");

        for (Player player : tiedPlayers) {
            System.out.println(player.getName() + " will roll the dice...");
            int rollResult = dice.roll();
            dice.animateRoll(player.getName(), rollResult);
            System.out.println(player.getName() + " rolled a " + rollResult + "!");

            if (rollResult > highestRoll) {
                highestRoll = rollResult;
                winner = player;
            } else if (rollResult == highestRoll) {
                // Handle ties in the dice roll if needed (e.g., re-roll)
                System.out.println("It's a tie in the dice roll between " + winner.getName() + " and " + player.getName() + "! The current leader remains.");
            }
            System.out.println();
        }

        System.out.println("The initial winner determined by the dice roll is: " + winner.getName() + "!");
        return winner;
    }

    /**
     * Determines the winner based on scores and tiebreaker rules.
     */
    public Player determineWinner() {
        Collections.sort(players, new PlayerComparator());
        ArrayList<Player> potentialWinners = addPotentialWinners();

        if (potentialWinners.size() > 1) {
            GameRenderer.showTieBreaker(potentialWinners);

            // If there's still a tie after showing the initial info,
            // use the dice to determine the "first" winner.
            if (potentialWinners.size() > 1) {
                Player diceWinner = determineFirstWinnerByDice(potentialWinners);
                // Now, effectively, the 'diceWinner' is the one who should be at index 0
                // if we were to re-sort or create a new list.
                // For simplicity, we can just return this diceWinner.
                return diceWinner;
            }
        }

        return potentialWinners.get(0);
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
     * Rearranges players list to put the ended player first
     */
    public static void rearrangePlayersList(List<Player> players, Player startingPlayer) {

        int startIndex = players.indexOf(startingPlayer);
        List<Player> rearranged = new ArrayList<>(players.size());

        // Add players from starting index to end
        rearranged.addAll(players.subList(startIndex, players.size()));
        // Add players from beginning to starting index
        rearranged.addAll(players.subList(0, startIndex));

        players.clear();
        players.addAll(rearranged);
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
