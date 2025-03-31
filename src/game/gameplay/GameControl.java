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
                player.drawCardFromDeck(deck);
                System.out.println(player.getName() + " draws one card from the deck.");
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
        // Decide starting player
        Player firstPlayer = GameUtil.decideStartingPlayer(players);
        GameUtil.rearrangePlayersList(players, firstPlayer);

        // Wait for user input before proceeding
        GameUtil.pressEnterToContinue(scanner);
        Helper.flush();

        // Show who is shuffling the deck
        System.out.print("\n🎮 " + firstPlayer.getName() + " is shuffling the deck");
        Helper.loading();    // Assuming Helper.loading handles a loading animation or dots
        Thread.sleep(1000);  // Pause for dramatic effect
        System.out.println("\n✅ Done!");
        Thread.sleep(500);   // Short pause after shuffling

        // Shuffle the deck
        deck.shuffle();

        // Deal 5 cards to each player
        System.out.println("\n🎴 Cards are being dealt...");
        Thread.sleep(1000); // Pause before dealing
        dealCardstoPlayers(); // Deal cards to players
        Thread.sleep(500); // Pause after dealing

        // Initialize Parade
        System.out.println("\n✨ Initializing Parade...");
        Thread.sleep(1000); // Pause for dramatic effect
        parade.initializeParade(deck);

        // Show Parade
        System.out.println("\n🎉 Parade has been initialized with 6 cards!\n");
        parade.showParade();
        Thread.sleep(1000); // Pause to give players a moment to admire the parade

        // Wait for user to continue to next stage
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
        System.out.println("Current Deck: " + deck.getCards().size() + " cards\n");
        showCards(players);
        parade.showParade();
        printTurn(player.getName());

        if (player instanceof Human) {
            ((Human) player).showClosedCards();
        }

        player.playCard(parade, scanner);
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
        }
        player.drawCardsFromParade(parade);

    }

    /**
     * Manages the end game sequence when an end condition has been triggered.
     * Each player plays one last round without drawing from the deck.
     */
    public void handleEndGame() {
        System.out.println("\nEnd Game Condition Triggered!!! Everyone plays one last round!\n");
        //Last round without drawing from deck
        for (Player player : players) {
            playTurn(player);
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
            Helper.flush();
            showCards(players);
            printTurn(player.getName());
            player.finalPlay(parade, scanner);
            if (player instanceof Human) {
                scanner.nextLine();
            }
            GameUtil.pressEnterToContinue(scanner);
        }
        concludeGame();
    }

    /**
     * Concludes the game by flipping cards based on majority rules, calculating
     * final scores, determining the winner, and displaying results. Closes the
     * scanner when done.
     */
    // In GameControl
    private void concludeGame() {
        showCards(players);
        System.out.println("Final scoring phase...");

        // Delegate ALL scoring to GameManager
        gameManager.calculateFinalScores();
        gameManager.determineWinner();

        try {
            Podium.displayPodium(players);
        } catch (InterruptedException e) {
        }
        
    }

    public void showCards(ArrayList<Player> players) {
        for (Player player : players) {
            player.showOpenCards();
        }
    }
}
