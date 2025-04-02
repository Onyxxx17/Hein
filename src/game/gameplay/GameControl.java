package game.gameplay;

import game.core.*;
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
    public void startGame() {
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
     */
    public void initializeGame() {
        // Decide starting player
        Player firstPlayer = GameUtil.decideStartingPlayer(players);
        GameUtil.rearrangePlayersList(players, firstPlayer);

        // Wait for user input before proceeding
        GameUtil.pressEnterToContinue(scanner);
        Helper.flush();

        // Show who is shuffling the deck
        System.out.print("\n🎮 " + firstPlayer.getName() + " is shuffling the deck");
        Helper.loading();    // Assuming Helper.loading handles a loading animation or dots
        // Helper.sleep(1000);  // Pause for dramatic effect
        System.out.println("\n✅ Done!");
        Helper.sleep(500);   // Short pause after shuffling

        // Shuffle the deck
        deck.shuffle();

        // Deal 5 cards to each player
        System.out.print("\n🎴 Cards are being dealt");
        Helper.loading();
        Helper.sleep(1000); // Pause before dealing
        dealCardstoPlayers(); // Deal cards to players
        Helper.sleep(500); // Pause after dealing

        // Initialize Parade
        System.out.print("\n✨ Initializing Parade");
        Helper.loading();
        Helper.sleep(1000); // Pause for dramatic effect
        parade.initializeParade(deck);

        // Show Parade
        System.out.println("\n🎉 Parade has been initialized with 6 cards!\n");
        parade.showParade();
        Helper.sleep(1000); // Pause to give players a moment to admire the parade

        // Wait for user to continue to next stage
        GameUtil.pressEnterToContinue(scanner);
    }

    /**
     * Processes a single player's turn, including playing a card, drawing cards
     * from the parade, and drawing a card from the deck.
     *
     * @param player The player whose turn is being processed
     */
    public static void printTurn(String playerName) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("🌟 " + playerName + "'s Turn");
        System.out.println("=".repeat(40));
        System.out.println();
    }

    public void playTurn(Player player) {
        Helper.flush();
        System.out.println("Current Deck: " + deck.getCards().size() + " cards\n");
        displayOpenCards(players);
        parade.showParade();
        printTurn(player.getName());
        player.playCard(parade, scanner);
        Helper.sleep(800);
        player.drawCardsFromParade(parade);

    }

    /**
     * Manages the end game sequence when an end condition has been triggered.
     * Each player plays one last round without drawing from the deck.
     */
    public void handleEndGame() {
        
        //Last round without drawing from deck
        for (Player player : players) {
            Helper.printBox("🚨 Last Round 🚨");
            Helper.sleep(1000);
            playTurn(player);
            if (player instanceof Human) {
                scanner.nextLine();
            }
            GameUtil.pressEnterToContinue(scanner);
            Helper.flush();
        }

        addFinalTwoCards();
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
    private void addFinalTwoCards() {

        for (Player player : players) {
            Helper.flush();
            Helper.printBox("🎴 Add 2 Cards to Open Cards");
            Helper.sleep(1000);
            displayOpenCards(players);
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
        Helper.flush();
        Helper.printBox("🐧 Open Cards Before Flipping");
        Helper.sleep(1000);

        displayOpenCards(players);

        Helper.printBox("🃏 Flipping Cards");
        Helper.sleep(1000);

        gameManager.flipCards();
        Helper.typewrite("\n✅ Final Scores Have Been Calculated! ✅\n", 30);
        GameUtil.pressEnterToContinue(scanner);
        Helper.flush();

        gameManager.calculateFinalScores();
        gameManager.determineWinner();
        Podium.displayPodium(players);
    }

    public void displayOpenCards(ArrayList<Player> players) {
        for (Player player : players) {
            player.showOpenCards();
        }
    }
}
