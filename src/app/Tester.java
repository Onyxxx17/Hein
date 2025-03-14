package app;
import coreclasses.*;
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
        Helper.flush();
        //Can use the below in GameManager Class
        //Give initial 5 cards to each player
        // Start the game
        Deck deck = new Deck();
        deck.shuffle();
        Parade parade = new Parade();
        parade.add5Cards(deck);
        for (Player player : players) {
            player.InitialClosedCards(deck);
        }

        for (Player player : players) {
            parade.showParade();
            System.out.println("\n" + player.getName() + "'s Turn");
            if (player instanceof Human) {
                Human h = (Human) player;
                h.showClosedCards();
            }
            player.playCard(parade, sc);
            if (parade.getCards().isEmpty()) {
                parade.add5Cards(deck);
            }
            player.drawCardsFromParade(parade);
            player.drawCardFromDeck(deck);
            player.showOpenCards();
            GameUtil.pressEnterToContinue(sc);
            if (player instanceof Human) {
                sc.nextLine();
            }
            Helper.flush();
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
            Helper.flush();
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
            Helper.flush();
        }

        Helper.flush();
        for (Player player : players) {
            player.showOpenCards();
        }
        // Close the scanner
        sc.close();
    }
    
}
