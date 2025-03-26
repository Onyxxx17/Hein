package gameplay;

import coreclasses.Deck;
import coreclasses.Human;
import coreclasses.Parade;
import coreclasses.Player;
import java.util.ArrayList;
import java.util.Scanner;
import utils.GameUtil;
import utils.Helper;

public class GameControl {

    public static void print_turn(String player_name) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("🌟 " + player_name + "'s Turn");
        System.out.println("=".repeat(40));
        System.out.println();
    }
    
     public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        // GameUtil.welcomeMessage();
        // GameUtil.pressEnterToContinue(sc);
        int playerCount = GameUtil.askForNumberOfPlayers(sc);

        // Game start message
        System.out.println("Starting the game with " + playerCount + " players...");

        // List of players (Human vs Computer)
        ArrayList<Player> players = GameUtil.createPlayers(playerCount, sc);
        Helper.flush();
        Player firstPlayer = GameUtil.decideStartingPlayer(players);
        GameUtil.rearrangePlayersList(players, firstPlayer);

        // Resolves the issue with the input buffer dice rolling animation
        sc.nextLine();
        GameUtil.pressEnterToContinue(sc);
        // Helper.flush();
        // Give initial 5 cards to each player: = dealCards() in gameManager
        // Start the game

        Deck deck = new Deck();
        deck.shuffle();
        System.out.println("Deck size before initializing Parade: " + deck.size());
        Parade parade = new Parade(deck);
        System.out.println("Deck size after initializing Parade: " + deck.size());
        GameManager gameManager = new GameManager(players,deck);
        System.out.println("Currrent Deck : " + deck.getCards().size() + " cards");
        for (Player player : players) {
            player.initializeClosedCards(deck);
        }

        Boolean gameEnds = false;

        while (!gameEnds) {
            for (Player player : players) {
                // Check if the game should end before a player takes a turn
                // System.out.println("Game ends:" + gameManager.checkEndGame());
                // if (gameManager.checkEndGame()) {
                //     gameEnds = true;
                //     System.out.println("Game Over!");
                //     return; // Exit the loop immediately
                // }

                // Display current game state
                System.out.println("Current Deck: " + deck.getCards().size() + " cards");
                parade.showParade();
                print_turn(player.getName());

                if (player instanceof Human) {
                    ((Human) player).showClosedCards();
                }

                // Player actions
                player.playCard(parade, sc);
                player.drawCardsFromParade(parade);
                player.drawCardFromDeck(deck);
                player.showOpenCards();

                // Wait for user input (if human)
                if (player instanceof Human) {
                    GameUtil.pressEnterToContinue(sc);
                }

                // Check again if the game should end after the turn
                if (gameManager.checkEndGame()) {
                    gameEnds = true;
                    System.out.println("Game Over!");
                    break;
                }
            }
        }

        // Code below should happen when the end game is reached
        System.out.println("\nEnd Game Condition Triggered!!! Everyone plays one last round!\n");
        for (Player player : players) {
            parade.showParade();
            print_turn(player.getName());
            if (player instanceof Human) {
                Human h = (Human) player;
                h.showClosedCards();
            }
            player.playCard(parade, sc);
            player.drawCardsFromParade(parade);
            player.showOpenCards();
            GameUtil.pressEnterToContinue(sc);
            if (player instanceof Human) {
                sc.nextLine();
            }
            // Helper.flush();
        }

        System.out.println("Adding two cards to open cards for final play.\n");
        for (Player player : players) {
            print_turn(player.getName());
            player.finalPlay(parade, sc);
            player.showOpenCards();
            GameUtil.pressEnterToContinue(sc);
            if (player instanceof Human) {
                sc.nextLine();
            }
            // Helper.flush();
        }

        // Helper.flush();
        // for (Player player : players) {
        // player.showOpenCards();
        // }
        for (Player player : players) {
            player.showOpenCards();
        }

        System.out.println("Flipping cards based on majority rules...");
        gameManager.flipCards();
        // Helper.flush()

        System.out.println("\nCalculating Final Scores... \n"); // add in animation, repeat blinking at the "..."
        // add in loading spinner?

        Player winner = gameManager.decideWinner();
        System.out.println("\n" + winner.getName() + " is the Final Winner!!");

        // Close the scanner
        sc.close();
    }
}
