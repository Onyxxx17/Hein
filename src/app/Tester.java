package app;
import coreclasses.*;
import gameplay.*;
import java.util.*;
import utils.*;

public class Tester {

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        // GameUtil.welcomeMessage();
        // GameUtil.pressEnterToContinue(sc);
        int playerCount = GameUtil.askForNumberOfPlayers(sc);

        // Game start message
        System.out.println("Starting the game with " + playerCount + " players...");

        //List of players (Human vs Computer)
        ArrayList<Player> players = GameUtil.createPlayers(playerCount, sc);
        Helper.flush();
        Player firstPlayer = GameUtil.decideStartingPlayer(players);
        GameUtil.rearrangePlayersList(players, firstPlayer);
        
        //Resolves the issue with the input buffer dice rolling animation
        sc.nextLine();
        GameUtil.pressEnterToContinue(sc);
        //Helper.flush();
        //Give initial 5 cards to each player: = dealCards() in gameManager
        // Start the game

        Deck deck = new Deck();
        deck.shuffle();
        System.out.println("Deck size before initializing Parade: " + deck.size());
        Parade parade = new Parade(deck);
        System.out.println("Deck size after initializing Parade: " + deck.size());
        GameManager gameManager = new GameManager(players);
        System.out.println("Currrent Deck : "+ deck.getCards().size() + " cards");
        for (Player player : players) { 
            player.initializeClosedCards(deck);
        }

        while (!gameManager.checkEndGame()) { // while checkEndGame = false
            //System.out.println("------ Checking game state ------");
            // System.out.println("Deck size: " + deck.size());
            // System.out.println("End game check: " + gameManager.checkEndGame()); --> to double check 

            for (Player player : players) {
                System.out.println("Currrent Deck : "+ deck.getCards().size() + " cards");
                parade.showParade();
                System.out.println("\n --------- " + player.getName() + "'s Turn ---------");

                if (player instanceof Human) {
                    Human h = (Human) player;
                    h.showClosedCards();
                }
                player.playCard(parade, sc);
                player.drawCardsFromParade(parade);
                player.drawCardFromDeck(deck);
                player.showOpenCards();
                GameUtil.pressEnterToContinue(sc);
                if (player instanceof Human) {
                    sc.nextLine();
                }
                if(player.getOpenCards().size() == 6) {
                    break;
                }
                // Helper.flush();
            }    
        }

        //Code below should happen when the end game is reached
        System.out.println("\nEnd Game Condition Triggered!!! Everyone plays one last round!\n");
        for (Player player : players) {
            parade.showParade();
            System.out.println(player.getName() + "'s Turn");
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
            //Helper.flush();
        }

        System.out.println("Adding two cards to open cards for final play.\n");
        for (Player player : players) {
            System.out.println(player.getName() + "'s Turn");
            player.finalPlay(parade, sc);
            player.showOpenCards();
            GameUtil.pressEnterToContinue(sc);
            if (player instanceof Human) {
                sc.nextLine();
            }
            //Helper.flush();
        }

        //Helper.flush();
        // for (Player player : players) {
        //     player.showOpenCards();
        // }
        for(Player player : players) {
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
