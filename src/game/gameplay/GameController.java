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

    // ============================ Instance Variables ============================

    private final GameManager gameManager;
    private final Deck deck;
    private final Parade parade;
    private final List<Player> players;
    private final Scanner scanner;
    private final Dice dice;
    private final StartingPlayerDecider startingPlayerdecider;
    private final QuitHandler quitHandler;

    // ============================ Constructor ============================
    /**
     * Constructs a GameController with the given game manager and scanner.
     *
     * @param gameManager The game manager that manages the game state.
     * @param sc          The scanner for user input.
     */
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

    // ============================ Instance Methods ============================

    /**
     * Starts the game by initializing the game state, processing turns for
     * all players in sequence, and handling the game end condition.
     *
     * This method is the main entry point for game flow. It will loop until all
     * players have either collected all colors or the deck runs out of cards.
     * If the deck runs out of cards, the game will end and the final scores will
     * be calculated.
     *
     * During the game loop, the method will check for quit commands and
     * terminate the game early if all players have quit.
     */
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
        // If the game ends due to deck running out of cards or a player collecting all colors
        // and not due to early termination, handle the end game.
        if (!earlyTermination) {
            handleEndGame();
        }
    }

// ============================ Game Initialization ============================

    /**
     * Initializes the game by shuffling the deck, dealing cards to players,
     * and setting up the parade.
     */
    private void initializeGame() {
        Helper.pressEnterToContinue(scanner);
        Helper.flush();

        Player firstPlayer = startingPlayerdecider.decideStartingPlayer(players);
        gameManager.rearrangePlayers(firstPlayer);

        Helper.pressEnterToContinue(scanner);
        Helper.flush();

        GameFlowRenderer.showGameStart(firstPlayer);
        Helper.sleep(Constants.NORMAL_DELAY_TIME);

        System.out.println("\nDeck size: " + deck.getCards().size() + " cards");
        deck.shuffle();
        GameFlowRenderer.showCardDealing();
        dealCardsToPlayers();
        Helper.sleep(Constants.NORMAL_DELAY_TIME);

        parade.initializeParade();
        GameFlowRenderer.showParadeInitialization();
        Helper.sleep(Constants.NORMAL_DELAY_TIME);
        ParadeRenderer.showParade(parade);
        Helper.sleep(Constants.NORMAL_DELAY_TIME);

        Helper.pressEnterToContinue(scanner);
    }

// ============================ Turn Processing ============================

    /**
     * Processes a turn for a specific player.
     *
     * @param player The player whose turn it is.
     */
    private void playTurn(Player player) {
        if (player.isHuman()) {
            PlayerRenderer.showClosedCards(player);
        }
        player.playCard(parade, scanner);
        Helper.sleep(Constants.NORMAL_DELAY_TIME);
        List<Card> drawnCards = player.drawCardsFromParade(parade);
        PlayerRenderer.showReceivedCards(player, drawnCards);
    }

// ============================ Game Conclusion ============================

    /**
     * Handles the end of the game by processing turns for the final two rounds.
     */
    private void handleEndGame() {
        for (Player player : players) {
            GamePhaseRenderer.showLastRoundPhase();

            GameFlowRenderer.showPlayerRound(player, players, parade, deck);
            playTurn(player);
            Helper.pressEnterToContinue(scanner);
        }
        addFinalTwoCards();
    }

    /**
     * Adds the final two cards to each player's hand and concludes the game.
     */
    private void addFinalTwoCards() {
        for (Player player : players) {
            Helper.flush();
            GamePhaseRenderer.showFinalPhase();
            Helper.sleep(Constants.NORMAL_DELAY_TIME);
            GameFlowRenderer.showOpenCards(players);
            GameFlowRenderer.showTurnHeader(player.getName());
            player.finalPlay(scanner);
            Helper.pressEnterToContinue(scanner);
        }
        concludeGame();
    }

// ============================ Card Dealing and Finalization ============================

    /**
     * Deals cards to each player at the start of the game.
     *
     * Each player receives a specified number of cards from the deck.
     */
    private void dealCardsToPlayers() {
        for (Player player : players) {
            for (int i = 0; i < Constants.CARDS_TO_DEAL; i++) {
                player.drawCardFromDeck(deck);
            }
        }
    }

    /**
     * Concludes the game by flipping cards and calculating scores.
     *
     * This method displays the final scores and determines the winner of the game.
     */
    private void concludeGame() {
        Helper.flush();
        Helper.printBox("🐧 Open Cards Before Flipping");
        Helper.sleep(Constants.NORMAL_DELAY_TIME);

        GameFlowRenderer.showOpenCards(players);
        GamePhaseRenderer.showFlippingPhase();
        Helper.sleep(Constants.NORMAL_DELAY_TIME);

        Map<Player, List<Card>> flippedCards = gameManager.flipCards();
        GameFlowRenderer.showFlippedCards(flippedCards, players);
        Helper.typewrite("\n✅ Final Scores Have Been Calculated! ✅\n", Constants.TYPEWRITE_DURATION);
        Helper.pressEnterToContinue(scanner);

        Helper.flush();
        // GameFlowRenderer.showFlippedCards(flippedCards, players);
        GameFlowRenderer.showFlippedCards(flippedCards, players);
        gameManager.calculateScores();
        Player winner = gameManager.determineWinner();
        Podium.showPodium(players, winner);
    }
}
