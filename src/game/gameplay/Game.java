package game.gameplay;

import game.core.*;
import game.renderer.*;
import game.setup.*;
import game.utils.*;
import java.util.*;

/**
 * Controls the game flow, manages game initialization, turn processing, and
 * game conclusion. This class orchestrates the entire lifecycle of a game
 * session.
 */
public class Game {

    private final GameManager gameManager;
    private final Deck deck;
    private final Parade parade;
    private final ArrayList<Player> players;
    private final Scanner scanner;

    /**
     * Creates a new Game instance with the specified game manager and
     * input scanner.
     *
     * @param gameManager The game manager that handles game rules and state
     * @param sc The scanner used for user input
     */
    public Game(GameManager gameManager, Scanner sc) {
        this.gameManager = gameManager;
        this.deck = gameManager.getDeck();
        this.players = gameManager.getPlayers();
        this.scanner = sc;
        this.parade = new Parade();
    }

    /**
     * Starts the game loop by initializing the game and processing turns for
     * each player. Continues until an end condition is met, at which point the
     * end game handling is triggered.
     */
    public void startGame() {
        initializeGame();
        boolean gameEnds = false;
        while (!gameEnds) {
            for (Player player : players) {
                Helper.flush();
                playTurn(player);
                player.drawCardFromDeck(deck);
                PlayerRenderer.showCardDraw(player);
                if (gameManager.checkEndGame()) {
                    gameEnds = true;
                    if (player.isHuman()) {
                        scanner.nextLine();
                    }
                    int currentIndex = players.indexOf(player);
                    Player nextPlayer = players.get((currentIndex + 1) % players.size());
                    GameManager.rearrangePlayersList(players, nextPlayer);
                    Helper.pressEnterToContinue(scanner);
                    Helper.flush();
                    break;
                }
                if (player instanceof Human) {
                    scanner.nextLine();
                }
                Helper.pressEnterToContinue(scanner);
            }
        }
        handleEndGame();
    }

    /**
     * Initializes the game by shuffling the deck, dealing cards to each player,
     * initializing the Parade, and displaying the initial game state.
     */
    public void initializeGame() {
        // Wait for the user to press Enter to continue to the next step
        Helper.pressEnterToContinue(scanner);

        // Clear the screen (or any other action defined in Helper.flush())
        Helper.flush();

        Dice dice = new Dice();
        StartingPlayerDecider startingPlayerdecider = new StartingPlayerDecider(dice);
        Player firstPlayer = startingPlayerdecider.decideStartingPlayer(players);
        GameManager.rearrangePlayersList(players, firstPlayer);

        Helper.pressEnterToContinue(scanner);
        Helper.flush();

        GameFlowRenderer.showGameStart(firstPlayer);
        Helper.sleep(500);

        deck.shuffle();

        GameFlowRenderer.showCardDealing();
        Helper.sleep(1000);
        dealCardstoPlayers();
        Helper.sleep(500);

        parade.initializeParade(deck);
        GameFlowRenderer.showParadeInitialization();
        Helper.sleep(1000);
        ParadeRenderer.showParade(parade);
        Helper.sleep(1000);

        Helper.pressEnterToContinue(scanner);
    }

    /**
     * Executes a single turn for the given player by displaying the current
     * game state, allowing the player to play a card, and handling card drawing
     * from the parade. Displays relevant game information and pauses between
     * actions for better user experience.
     *
     * @param player The player whose turn is being processed.
     */
    public void playTurn(Player player) {
        GameFlowRenderer.showDeckSize(deck);
        GameFlowRenderer.displayOpenCards(players);
        ParadeRenderer.showParade(parade);
        GameFlowRenderer.showTurnHeader(player.getName());
        player.playCard(parade, scanner);
        Helper.sleep(800);
        ArrayList<Card> drawnCards = player.drawCardsFromParade(parade);
        PlayerRenderer.displayReceivedCards(player, drawnCards);
    }

    /**
     * Handles the end game phase by allowing each player to play a final turn.
     * Displays end game prompts and provides a pause for human players to
     * acknowledge each step. After the last round, prompts players to add two
     * final cards to their open collections for scoring.
     */
    public void handleEndGame() {
        for (Player player : players) {
            Helper.flush();
            Helper.printBox("🚨 Last Round 🚨");
            Helper.sleep(1000);
            playTurn(player);
            if (player.isHuman()) {
                scanner.nextLine();
            }
            Helper.pressEnterToContinue(scanner);
        }
        addFinalTwoCards();
    }

    /**
     * Handles the final two card selection phase for each player in the game.
     * Allows each player to add two more cards to their open collection before
     * scoring and game conclusion.
     */
    private void addFinalTwoCards() {
        for (Player player : players) {
            Helper.flush();
            GameFlowRenderer.showFinalPhase();
            Helper.sleep(1000);
            GameFlowRenderer.displayOpenCards(players);
            GameFlowRenderer.showTurnHeader(player.getName());
            player.finalPlay(parade, scanner);
            if (player.isHuman()) {
                scanner.nextLine();
            }
            Helper.pressEnterToContinue(scanner);
        }
        concludeGame();
    }

    /**
     * Deals 5 initial cards to each player from the deck. Called during game
     * initialization.
     */
    public void dealCardstoPlayers() {
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                player.drawCardFromDeck(deck);
            }
        }
    }

    /**
     * Concludes the game by flipping cards based on majority rules, calculating
     * final scores, determining the winner, and displaying results. Closes the
     * scanner when done.
     */
    private void concludeGame() {
        Helper.flush();
        Helper.printBox("🐧 Open Cards Before Flipping");
        Helper.sleep(1000);

        GameFlowRenderer.displayOpenCards(players);

        GameFlowRenderer.showFlippingPhase();
        Helper.sleep(1000);

        Map<Player, ArrayList<Card>> flippedCards = gameManager.flipCards();
        GameFlowRenderer.showFlippedCards(flippedCards, players);
        Helper.typewrite("\n✅ Final Scores Have Been Calculated! ✅\n", 30);
        Helper.pressEnterToContinue(scanner);

        Helper.flush();
        gameManager.calculateFinalScores();
        Player winner = gameManager.determineWinner();

        Podium.displayPodium(players, winner);
    }
}
