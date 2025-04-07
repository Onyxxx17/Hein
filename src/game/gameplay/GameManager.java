package game.gameplay;

import game.core.*;
import game.renderer.*;
import game.setup.Dice;
import game.utils.Constants;
import game.utils.Helper;
import java.util.*;

/**
 * Manages the core game rules, win conditions, and state for the Parade card
 * game. Handles logic for determining game end, card flipping, and winner
 * selection.
 */
public class GameManager {

    private final ArrayList<Player> players; // List of players participating in the game
    private final Deck deck; // The deck of cards used in the game

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
            GameFlowRenderer.showDeckEmpty();
            return true;
        }
        for (Player p : players) {
            Map<String, ArrayList<Card>> openCards = p.getOpenCards();
            if (openCards.size() == Constants.TOTAL_COLORS) {
                boolean haveAllColors = true;
                for (List<Card> list : openCards.values()) {
                    if (list.isEmpty()) {
                        haveAllColors = false;
                        break;
                    }
                }
                if (haveAllColors) {
                    GameFlowRenderer.showAllColorsCollected(p);
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
    public void check2PlayerConditions(String color, ArrayList<Player> playersOut) {
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

    /**
     * Flips cards for players with the majority of each color according to the
     * game rules.
     */
    public Map<Player, ArrayList<Card>> flipCards() {
        Map<Player, ArrayList<Card>> flippedCards = new HashMap<>();

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
     * Calculates final scores for all players.
     */
    public void calculateFinalScores() {
        for (Player player : players) {
            player.calculateScore();
        }
    }

    private Player determineFirstWinnerByDice(ArrayList<Player> tiedPlayers) {
        if (tiedPlayers == null || tiedPlayers.isEmpty()) {
            return null;
        }
    
        Dice dice = new Dice();
        Map<Player, Integer> diceResults = new HashMap<>();
    
        // Simulate dice roll
        System.out.println("💫 There's still tie between " + tiedPlayers.size() + " players!");
        System.out.println("\n🎲 Initiating dice roll to determine the winner among tied players\n");
        Helper.loading();
    
        // Roll the dice and record results
        for (Player player : tiedPlayers) {
            int rollResult = dice.roll();
            dice.animateRoll(player.getName(), rollResult);
            System.out.println(player.getName() + " rolled a " + rollResult + "!\n");
    
            diceResults.put(player, rollResult);
        }
    
        // Sort tiedPlayers based on their dice roll result (descending order)
        tiedPlayers.sort((p1, p2) -> diceResults.get(p2) - diceResults.get(p1));
    
        // Show sorted dice results
        System.out.println("📊 Dice roll results (highest to lowest):");
        for (Player player : tiedPlayers) {
            System.out.println(player.getName() + ": " + diceResults.get(player));
        }
    
        Player winner = tiedPlayers.get(0);
        System.out.println("\n🏆 The winner determined by the highest dice roll is: " + winner.getName() + "!");
        return winner;
    }
    

    /**
     * Determines the winner based on scores and tiebreaker rules.
     */
    public Player determineWinner() {
        // Step 1: Sort players using deterministic rules
        Collections.sort(players, new PlayerComparator());

        // Step 2: Get the top player and find all who are tied with them
        Player topPlayer = players.get(0);
        ArrayList<Player> potentialWinners =addPotentialWinners();
        if (potentialWinners.size() > 1) {
            GameFlowRenderer.showTieBreaker(potentialWinners);
        }

        potentialWinners.clear();
        potentialWinners.add(topPlayer);
        // Step 2: Get the top player and find all who are tied with them in all conditions
        for (int i = 1; i < players.size(); i++) {
            Player current = players.get(i);
            if (new PlayerComparator().compare(current, topPlayer) == 0) {
                potentialWinners.add(current);
            } else {
                break;
            }
        }

        // Step 3: If there are ties, roll dice
        if (potentialWinners.size() > 1) {
    
            // Step 4: Sort tied players by dice roll
            Player diceWinner = determineFirstWinnerByDice(potentialWinners);
    
            // Step 5: Update the main players list
            // Remove old tied players from top and insert sorted ones based on dice roll
            players.removeAll(potentialWinners);
            players.addAll(0, potentialWinners); // potentialWinners is now sorted by dice in the dice method
    
            return diceWinner;
        }

        return topPlayer;
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
