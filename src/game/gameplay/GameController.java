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
        while (!gameEnds) {
        
            Iterator<Player> iterator = players.iterator();
            while (iterator.hasNext()) {

                // Check if there's only one player or no human players
                if (players.size() == 1 || quitHandler.countHumans() == 0) {
                    gameEnds = true;
                    break;
                }
                Player player = iterator.next();
                Helper.flush();
                GameFlowRenderer.showPlayerRound(player, players, parade, deck);
        
                // Check if the player decides to quit
                if (quitHandler.checkForQuit(player, player.isHuman(), iterator)) {
                    Helper.pressEnterToContinue(scanner);
                    Helper.flush();
                    break;
                }
        
                // Play the player's turn
                playTurn(player);
                player.drawCardFromDeck(deck);
                PlayerRenderer.showCardDraw(player);
        
                // Check if the game should end
                if (gameManager.checkEndGame()) {
                    gameEnds = true;
                    int currentIndex = players.indexOf(player);
                    Player nextPlayer = players.get((currentIndex + 1) % players.size());
                    gameManager.rearrangePlayers(nextPlayer);
                    if(player.isHuman()) {
                       scanner.next();
                    }
                    Helper.pressEnterToContinue(scanner);
                    Helper.flush();
                    break;
                }
        
                // Allow human player to press Enter to continue
                if (player.isHuman()) {
                    scanner.nextLine();
                }
                Helper.pressEnterToContinue(scanner);
            }
        }
        if(!(players.size() == 1) && !(quitHandler.countHumans() == 0)) {
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
        Helper.sleep(500);

        deck.shuffle();
        GameFlowRenderer.showCardDealing();
        Helper.sleep(1000);
        dealCardsToPlayers();
        Helper.sleep(500);

        parade.initializeParade();
        GameFlowRenderer.showParadeInitialization();
        Helper.sleep(1000);
        ParadeRenderer.showParade(parade);
        Helper.sleep(1000);

        Helper.pressEnterToContinue(scanner);
    }

    public void playTurn(Player player) {
        player.playCard(parade, scanner);
        Helper.sleep(800);
        ArrayList<Card> drawnCards = player.drawCardsFromParade(parade);
        PlayerRenderer.displayReceivedCards(player, drawnCards);
    }

    public void handleEndGame() {
        for (Player player : players) {
            GamePhaseRenderer.showFinalRound();

            GameFlowRenderer.showPlayerRound(player, players, parade, deck);

            playTurn(player);
            if (player.isHuman()) {
                scanner.nextLine();
            }
            Helper.pressEnterToContinue(scanner);
        }
        addFinalTwoCards();
    }

    private void addFinalTwoCards() {
        for (Player player : players) {
            Helper.flush();
            GamePhaseRenderer.showFinalPhase();
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
        Helper.sleep(1000);

        GameFlowRenderer.displayOpenCards(players);
        GamePhaseRenderer.showFlippingPhase();
        Helper.sleep(1000);

        Map<Player, List<Card>> flippedCards = gameManager.flipCards();
        GameFlowRenderer.showFlippedCards(flippedCards, players);
        Helper.typewrite("\n✅ Final Scores Have Been Calculated! ✅\n", 30);
        Helper.pressEnterToContinue(scanner);

        Helper.flush();
        gameManager.calculateScores();
        Player winner = gameManager.determineWinner();
        Podium.displayPodium(players, winner);
    }
}

