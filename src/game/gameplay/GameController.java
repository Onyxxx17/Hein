package game.gameplay;

import game.core.*;
import game.gameplay.managers.QuitHandler;
import game.renderer.*;
import game.setup.*;
import game.utils.*;
import java.util.*;

/**
 * Controls the game flow, manages game initialization, turn processing, and
 * game conclusion. This class orchestrates the entire lifecycle of a game
 * session.
 */
public class GameController {

    private final GameManager gameManager;
    private final Deck deck;
    private final Parade parade;
    private final List<Player> players;
    private final Scanner scanner;
    private final Dice dice;
    private final StartingPlayerDecider startingPlayerdecider;
    private final QuitHandler quitHandler;

    public GameController(GameManager gameManager, Scanner sc) {
        this.gameManager = gameManager;
        this.deck = gameManager.getDeck();
        this.players = gameManager.getPlayers();
        this.scanner = sc;
        this.parade = new Parade(deck);
        this.dice = new Dice();
        this.startingPlayerdecider = new StartingPlayerDecider(dice);
        this.quitHandler = new QuitHandler(players, scanner);
    }

    public void startGame() {
        initializeGame();
        boolean gameEnds = false;
        boolean earlyTermination = false;

        while (!gameEnds) {
            Iterator<Player> iterator = players.iterator();
            while (iterator.hasNext() && !gameEnds) {
                // Check for early termination before processing turns
                if (players.size() == 1 || quitHandler.countHumans() == 0) {
                    gameEnds = true;
                    earlyTermination = true;
                    break;
                }

                Player player = iterator.next();
                Helper.flush();
                GameFlowRenderer.showPlayerRound(player, players, parade, deck);

                // Check for quit command
                if (quitHandler.checkForQuit(player, player.isHuman(), iterator)) {
                    Helper.pressEnterToContinue(scanner);
                    Helper.flush();
                    continue;
                }

                // Process turn
                playTurn(player);
                player.drawCardFromDeck(deck);
                PlayerRenderer.showCardDraw(player);

                // Check normal game end condition
                if (gameManager.checkEndGame()) {
                    gameEnds = true;
                    int currentIndex = players.indexOf(player);
                    Player nextPlayer = players.get((currentIndex + 1) % players.size());
                    gameManager.rearrangePlayers(nextPlayer);
                    Helper.pressEnterToContinue(scanner);
                    Helper.flush();
                    break;
                }

                Helper.pressEnterToContinue(scanner);
            }
        }

        if (!earlyTermination) {
            handleEndGame();
        }
    }

    public void initializeGame() {
        Helper.pressEnterToContinue(scanner);
        Helper.flush();

        Player firstPlayer = startingPlayerdecider.decideStartingPlayer(players);
        gameManager.rearrangePlayers(firstPlayer);

        Helper.pressEnterToContinue(scanner);
        Helper.flush();

        GameFlowRenderer.showGameStart(firstPlayer);
        Helper.sleep(Constants.FASTDELAY);

        System.out.println("\nDeck size: " + deck.getCards().size() + " cards");
        deck.shuffle();
        GameFlowRenderer.showCardDealing();
        dealCardsToPlayers();
        Helper.sleep(Constants.FASTDELAY);

        parade.initializeParade();
        GameFlowRenderer.showParadeInitialization();
        Helper.sleep(Constants.SLOWDELAY);
        ParadeRenderer.showParade(parade);
        Helper.sleep(Constants.SLOWDELAY);

        Helper.pressEnterToContinue(scanner);
    }

    public void playTurn(Player player) {
        if (player.isHuman()) {
            PlayerRenderer.showClosedCards(player);
        }
        player.playCard(parade, scanner);
        Helper.sleep(Constants.FASTDELAY);
        List<Card> drawnCards = player.drawCardsFromParade(parade);
        PlayerRenderer.showReceivedCards(player, drawnCards);
    }

    public void handleEndGame() {
        for (Player player : players) {
            GamePhaseRenderer.displayLastRoundPhase();

            GameFlowRenderer.showPlayerRound(player, players, parade, deck);
            playTurn(player);
            Helper.pressEnterToContinue(scanner);
        }
        addFinalTwoCards();
    }

    private void addFinalTwoCards() {
        for (Player player : players) {
            Helper.flush();
            GamePhaseRenderer.displayFinalPhase();
            Helper.sleep(Constants.FASTDELAY);
            GameFlowRenderer.displayOpenCards(players);
            GameFlowRenderer.showTurnHeader(player.getName());
            if (player.isHuman()) {
                PlayerRenderer.showClosedCards(player);
            }
            player.finalPlay(scanner);
            Helper.pressEnterToContinue(scanner);
        }
        concludeGame();
    }

    public void dealCardsToPlayers() {
        for (Player player : players) {
            for (int i = 0; i < Constants.CARDS_TO_DEAL; i++) {
                player.drawCardFromDeck(deck);
            }
        }
    }

    private void concludeGame() {
        Helper.flush();
        Helper.printBox("🐧 Open Cards Before Flipping");
        Helper.sleep(Constants.SLOWDELAY);

        GameFlowRenderer.displayOpenCards(players);
        GamePhaseRenderer.displayFlippingPhase();
        Helper.sleep(Constants.SLOWDELAY);

        Map<Player, List<Card>> flippedCards = gameManager.flipCards();
        GameFlowRenderer.showFlippedCards(flippedCards, players);
        Helper.typewrite("\n✅ Final Scores Have Been Calculated! ✅\n", Constants.TYPEWRITE_DURATION);
        Helper.pressEnterToContinue(scanner);

        Helper.flush();
        // GameFlowRenderer.showFlippedCards(flippedCards, players);
        gameManager.calculateScores();
        Player winner = gameManager.determineWinner();
        Podium.displayPodium(players, winner);
    }
}
