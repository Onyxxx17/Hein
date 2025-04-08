package game.gameplay;

import game.core.*;
import game.gameplay.managers.CardFlipper;
import game.gameplay.managers.EndGameChecker;
import game.gameplay.managers.PlayerManager;
import game.gameplay.managers.ScoreCalculator;
import game.gameplay.managers.WinnerDeterminer;
import java.util.*;

public class GameManager {
    private final EndGameChecker endGameChecker;
    private final CardFlipper cardFlipper;
    private final WinnerDeterminer winnerDeterminer;
    private final PlayerManager playerManager;
    private final ScoreCalculator scoreCalculator;
    private final Deck deck;

    public GameManager(List<Player> players, Deck deck) {
        this.deck = deck;
        this.playerManager = new PlayerManager(players);
        this.endGameChecker = new EndGameChecker(players, deck);
        this.cardFlipper = new CardFlipper(players);
        this.winnerDeterminer = new WinnerDeterminer(players);
        this.scoreCalculator = new ScoreCalculator();
    }

    public boolean checkEndGame() {
        return endGameChecker.checkEndGame();
    }

    public Map<Player, List<Card>> flipCards() {
        return cardFlipper.flipCards();
    }

    public Player determineWinner() {
        return winnerDeterminer.determineWinner();
    }

    public void rearrangePlayers(Player startingPlayer) {
        playerManager.rearrangePlayers(startingPlayer);
    }

    public void calculateScores() {
        scoreCalculator.calculateFinalScores(playerManager.getPlayers());
    }

    // Getters
    public Deck getDeck() { return deck; }
    public List<Player> getPlayers() { return playerManager.getPlayers(); }
}