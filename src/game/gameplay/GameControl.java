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
public class GameControl {

    private final GameManager gameManager;
    private final Deck deck;
    private final Parade parade;
    private final ArrayList<Player> players;
    private final Scanner scanner;

    /**
     * Creates a new GameControl instance with the specified game manager and
     * input scanner.
     *
     * @param gameManager The game manager that handles game rules and state
     * @param sc The scanner used for user input
     */
    public GameControl(GameManager gameManager, Scanner sc) {
        this.gameManager = gameManager;
        this.deck = gameManager.getDeck();
        this.players = gameManager.getPlayers();
        this.scanner = sc;
        this.parade = new Parade();
    }

    public void startGame() {
        initializeGame();
        boolean gameEnds = false;
        while (!gameEnds) {
            for (Player player : players) {
                Helper.flush();
                playTurn(player);
                player.drawCardFromDeck(deck);
                if (gameManager.checkEndGame()) {
                    gameEnds = true;
                    if (player instanceof Human) {
                        scanner.nextLine();
                    }
                    Player lastPlayer = player;
                    GameManager.rearrangePlayersList(players, lastPlayer);
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

    public void initializeGame() {
        Dice dice = new Dice();
        StartingPlayerDecider startingPlayerdecider = new StartingPlayerDecider(dice);
        Player firstPlayer = startingPlayerdecider.decideStartingPlayer(players);
        GameManager.rearrangePlayersList(players, firstPlayer);

        Helper.pressEnterToContinue(scanner);
        Helper.flush();

        GameRenderer.showGameStart(firstPlayer);
        Helper.sleep(500);

        deck.shuffle();

        GameRenderer.showCardDealing();
        Helper.sleep(1000);
        dealCardstoPlayers();
        Helper.sleep(500);

        parade.initializeParade(deck);
        GameRenderer.showParadeInitialization();
        Helper.sleep(1000);
        ParadeRenderer.showParade(parade);
        Helper.sleep(1000);

        Helper.pressEnterToContinue(scanner);
    }

    public void playTurn(Player player) {
        GameRenderer.showDeckSize(deck);
        GameRenderer.displayOpenCards(players);
        ParadeRenderer.showParade(parade);
        GameRenderer.showTurnHeader(player.getName());
        player.playCard(parade, scanner);
        Helper.sleep(800);
        ArrayList<Card> drawnCards = player.drawCardsFromParade(parade);
        PlayerRenderer.displayReceivedCards(player,drawnCards);
        GameRenderer.showCardDraw(player);
    }

    public void handleEndGame() {
        for (Player player : players) {
            Helper.flush();
            Helper.printBox("🚨 Last Round 🚨");
            Helper.sleep(1000);
            playTurn(player);
            if (player instanceof Human) {
                scanner.nextLine();
            }
            Helper.pressEnterToContinue(scanner);
        }
        addFinalTwoCards();
    }

    private void addFinalTwoCards() {
        for (Player player : players) {
            Helper.flush();
            GameRenderer.showFinalPhase();
            Helper.sleep(1000);
            GameRenderer.displayOpenCards(players);
            GameRenderer.showTurnHeader(player.getName());
            player.finalPlay(parade, scanner);
            if (player instanceof Human) {
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
        System.out.println("5 cards have been dealt to each player\n");
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
    // In GameControl
    private void concludeGame() {
        Helper.flush();
        Helper.printBox("🐧 Open Cards Before Flipping");
        Helper.sleep(1000);

        GameRenderer.displayOpenCards(players);

        GameRenderer.showFlippingPhase();
        Helper.sleep(1000);

        Map<Player, ArrayList<Card>> flippedCards = gameManager.flipCards();
        GameRenderer.showFlippedCards(flippedCards, players);
        Helper.typewrite("\n✅ Final Scores Have Been Calculated! ✅\n", 30);
        Helper.pressEnterToContinue(scanner);

        Helper.flush();
        gameManager.calculateFinalScores();
        Player winner = gameManager.determineWinner();

        Podium.displayPodium(players, winner);
    }
}
