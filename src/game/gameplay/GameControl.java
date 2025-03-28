package game.gameplay;

import game.core.Deck;
import game.core.Human;
import game.core.Parade;
import game.core.Player;
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
    private final Parade parade = new Parade();
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
    }

    /**
     * Starts and manages the game loop until an end condition is reached. Each
     * player takes turns until the game ends, then handles final game
     * procedures.
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
                    if (player instanceof Human) {
                        scanner.nextLine();
                    }
                    GameUtil.pressEnterToContinue(scanner);
                    Helper.flush();
                    break;
                }
                if (player instanceof Human) {
                    scanner.nextLine();
                }
                GameUtil.pressEnterToContinue(scanner);
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
        System.out.println("Current Deck: " + deck.getCards().size() + " cards");

        // Decide starting player
        Player firstPlayer = GameUtil.decideStartingPlayer(players);
        GameUtil.rearrangePlayersList(players, firstPlayer);

        // scanner.nextLine();// Resolves the issue with the input buffer dice rolling animation
        GameUtil.pressEnterToContinue(scanner);
        Helper.flush();

        // Shuffle the deck
        System.out.print(firstPlayer.getName() + " is shuffling the deck");
        Helper.loading();
        System.out.println("\nDone!");
        deck.shuffle();

        // Deal 5 cards to each player
        dealCardstoPlayers();

        // Initialize Parade
        parade.initializeParade(deck);

        // Show Parade
        System.out.println("\nParade has been initialized with 6 cards!\n");
        parade.showParade();
        GameUtil.pressEnterToContinue(scanner);
    }

    /**
     * Processes a single player's turn, including playing a card, drawing cards
     * from the parade, and drawing a card from the deck.
     *
     * @param player The player whose turn is being processed
     */
    public static void printTurn(String player_name) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("🌟 " + player_name + "'s Turn");
        System.out.println("=".repeat(40));
        System.out.println();
    }

    public void playTurn(Player player) {
        Helper.flush();
        System.out.println("Current Deck: " + deck.getCards().size() + " cards");
        parade.showParade();
        printTurn(player.getName());

        if (player instanceof Human) {
            ((Human) player).showClosedCards();
        }

        player.playCard(parade, scanner);
        try {
            Thread.sleep(800);
        } catch (Exception e) {
        }
        player.drawCardsFromParade(parade);
        player.drawCardFromDeck(deck);
        player.showOpenCards();

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
            printTurn(player.getName());
            if (player instanceof Human human) {
                human.showClosedCards();
            }
            player.playCard(parade, scanner);
            player.drawCardsFromParade(parade);
            player.showOpenCards();
            if (player instanceof Human) {
                scanner.nextLine();
            }
            GameUtil.pressEnterToContinue(scanner);
            Helper.flush();
        }

        finalPlay();
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
     * Processes the final plays where each player adds remaining cards to their
     * open cards. This is the last phase before concluding the game.
     */
    private void finalPlay() {
        System.out.println("Adding two cards to open cards for final play.\n");
        for (Player player : players) {
            printTurn(player.getName());
            player.finalPlay(parade, scanner);
            player.showOpenCards();
            if (player instanceof Human) {
                scanner.nextLine();
            }
            GameUtil.pressEnterToContinue(scanner);
        }
        for (Player player : players) {
            player.showOpenCards();
        }
        concludeGame();
    }

    /**
     * Concludes the game by flipping cards based on majority rules, calculating
     * final scores, determining the winner, and displaying results. Closes the
     * scanner when done.
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
        scanner.close();
    }

}
