package game.gameplay;

import game.core.*;
import game.gameplay.managers.*;
import java.util.*;

public class GameManager {

    private final EndGameChecker endGameChecker;
    private final CardFlipper cardFlipper;
    private final WinnerDeterminer winnerDeterminer;
    private final PlayerManager playerManager;
    private final ScoreCalculator scoreCalculator;
    private final Deck deck;

    // ============================ Constructor ============================
    /**
     * Constructs a GameManager with the given players and deck.
     *
     * @param players The list of players in the game.
     * @param deck    The deck used in the game.
     */
    public GameManager(List<Player> players, Deck deck) {
        this.deck = deck;
        this.playerManager = new PlayerManager(players);
        this.endGameChecker = new EndGameChecker(players, deck);
        this.cardFlipper = new CardFlipper(players);
        this.winnerDeterminer = new WinnerDeterminer(players);
        this.scoreCalculator = new ScoreCalculator();
    }

    // ============================ Instance Methods (Game Logic) ============================

    /**
     * Checks if the game should end, either due to an empty deck or a player collecting all colors.
     */
    public boolean checkEndGame() {
        return endGameChecker.checkEndGame();
    }

    /**
     * Flips cards based on the current game state.
     *
     * @return A map of players to the cards they flipped.
     */
    public Map<Player, List<Card>> flipCards() {
        return cardFlipper.flipCards();
    }

    /**
     * Determines the final winner of the game after evaluating scores and applying tiebreakers.
     *
     * @return The winning player.
     */
    public Player determineWinner() {
        return winnerDeterminer.determineWinner();
    }

    /**
     * Calculates the final scores for all players.
     */
    public void calculateScores() {
        scoreCalculator.calculateFinalScores(playerManager.getPlayers());
    }

    /**
     * Reorders players so the specified player starts the next round.
     *
     * @param startingPlayer The player to start the round.
     */
    public void rearrangePlayers(Player startingPlayer) {
        playerManager.rearrangePlayers(startingPlayer);
    }

    // ============================ Getters ============================

    public Deck getDeck() {
        return deck;
    }

    public List<Player> getPlayers() {
        return playerManager.getPlayers();
    }
}
