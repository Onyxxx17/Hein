package gameplay;

import coreclasses.*;
import java.util.*;
import utils.*;

/**
 * Controls the game flow, manages game initialization, turn processing, and game conclusion.
 * This class orchestrates the entire lifecycle of a game session.
 */
public class GameControl {

    private final GameManager gameManager;
    private final Deck deck;
    private final Parade parade = new Parade();
    private final ArrayList<Player> players;
    private final Scanner sc;

    /**
     * Creates a new GameControl instance with the specified game manager and input scanner.
     *
     * @param gameManager The game manager that handles game rules and state
     * @param sc The scanner used for user input
     */
    public GameControl(GameManager gameManager, Scanner sc) {
        this.gameManager = gameManager;
        this.deck = gameManager.getDeck();
        this.players = gameManager.getPlayers();
        this.sc = sc;
    }

    /**
     * Starts and manages the game loop until an end condition is reached.
     * Each player takes turns until the game ends, then handles final game procedures.
     *
     * @throws InterruptedException If thread operations are interrupted
     */
    public void startGame() throws InterruptedException {
        initializeGame();
        boolean gameEnds = false;
        while (!gameEnds) {
            for (Player player : players) {
                playTurn(player);
                if (gameManager.checkEndGame()) {
                    gameEnds = true;
                    System.out.println("Game Over!");
                    break;
                }
            }
        }
        handleEndGame(); 
    }

    /**
     * Sets up the game by shuffling the deck, determining the starting player,
     * dealing initial cards, and initializing the parade.
     *
     * @throws InterruptedException If thread operations are interrupted
     */
    public void initializeGame() throws InterruptedException {
        System.out.println("Total players: " + players.size());
        deck.shuffle();
        System.out.println("Current Deck: " + deck.getCards().size() + " cards");

        // Decide starting player
        Player firstPlayer = GameUtil.decideStartingPlayer(players);
        GameUtil.rearrangePlayersList(players, firstPlayer);

        // Resolves the issue with the input buffer dice rolling animation
        sc.nextLine();
        GameUtil.pressEnterToContinue(sc);
        
        dealCardstoPlayers();

        // Initialize Parade
        parade.initializeParade(deck);
        System.out.println("Deck size after initializing Parade: " + deck.size());
        System.out.println("Current Deck: " + deck.getCards().size() + " cards");
        parade.showParade();
    }

    /**
     * Processes a single player's turn, including playing a card, drawing cards from 
     * the parade, and drawing a card from the deck.
     *
     * @param player The player whose turn is being processed
     */
    public void playTurn(Player player) {
        System.out.println("\n --------- " + player.getName() + "'s Turn ---------");

        if (player instanceof Human) {
            ((Human) player).showClosedCards();
        }

        player.playCard(parade, sc);
        player.drawCardsFromParade(parade);
        player.drawCardFromDeck(deck);
        player.showOpenCards();

        if (player instanceof Human) {
            GameUtil.pressEnterToContinue(sc);
        }
    }

    /**
     * Manages the end game sequence when an end condition has been triggered.
     * Each player plays one last round without drawing from the deck.
     */
    public void handleEndGame() {
        System.out.println("\nEnd Game Condition Triggered!!! Everyone plays one last round!\n");
        //Last round without drawing from deck
        for (Player player : players) {
            parade.showParade();
            System.out.println(player.getName() + "'s Turn");
            if (player instanceof Human human) {
                human.showClosedCards();
            }
            player.playCard(parade, sc);
            player.drawCardsFromParade(parade);
            player.showOpenCards();
            GameUtil.pressEnterToContinue(sc);
        }

        finalPlay();
    }

    /**
     * Processes the final plays where each player adds remaining cards to their open cards.
     * This is the last phase before concluding the game.
     */
    private void finalPlay() {
        System.out.println("Adding two cards to open cards for final play.\n");
        for (Player player : players) {
            System.out.println(player.getName() + "'s Turn");
            player.finalPlay(parade, sc);
            player.showOpenCards();
            GameUtil.pressEnterToContinue(sc);
        }
        concludeGame();
    }

    /**
     * Concludes the game by flipping cards based on majority rules,
     * calculating final scores, determining the winner, and displaying results.
     * Closes the scanner when done.
     */
    private void concludeGame() {
        System.out.println("Flipping cards based on majority rules...");
        gameManager.flipCards();

        System.out.println("\nCalculating Final Scores... \n");

        Player winner = gameManager.decideWinner();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Podium.displayPodium(players);
        sc.close();
    }
    
    /**
     * Deals 5 initial cards to each player from the deck.
     * Called during game initialization.
     */
    public void dealCardstoPlayers() {
        for (Player player : players) {
            for (int i = 0; i < 5; i++) {
                player.drawCardFromDeck(deck);
            }
        }
    }
}