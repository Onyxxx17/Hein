package app;
import coreclasses.*;
import java.util.*;
import utils.*;
public class Tester {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // GameUtil.welcomeMessage();
        // GameUtil.pressEnterToContinue(sc);
        int playerCount = GameUtil.askForNumberOfPlayers(sc);

        // Game start message
        System.out.println("Starting the game with " + playerCount + " players...");

        //List of players (Human vs Computer)
        ArrayList<Player> players = GameUtil.createPlayers(playerCount,sc);


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
            System.out.println("\n"+player.getName() + "'s Turn");
            if(player instanceof Human) {
                Human h = (Human) player;
                h.showCards();
            }
            player.playCard(parade,sc);
            if(parade.getCards().isEmpty()){
                parade.add5Cards(deck);
            }
            player.drawCardsFromParade(parade);
            player.drawCardFromDeck(deck);
            System.out.println(player.getName() + "'s open cards :" + player.getOpenCards());
            GameUtil.pressEnterToContinue(sc);
           
        }

        //Code below should happen when the end game is reached

        System.out.println("\nEnd Game Condition Triggered!!! Everyone plays one last round!\n");

        for (Player player : players) {
            parade.showParade();
            System.out.println(player.getName() + "'s Turn");
            if(player instanceof Human) {
                Human h = (Human) player;
                h.showCards();
            }
            player.playCard(parade, sc);
            player.drawCardsFromParade(parade);
            System.out.println(player.getName() + "'s open cards :" + player.getOpenCards());
            GameUtil.pressEnterToContinue(sc);
        }

        System.out.println("\n");
        for(Player player:players){
            System.out.println();
            System.out.println(player.getName() + "'s Turn");
            player.finalPlay(parade, sc);
            GameUtil.pressEnterToContinue(sc);
        }

        Helper.flush();
        for (Player player : players) {
            
            System.out.println("\n"+player.getName() + "'s open cards :" + player.getOpenCards());
        }
        // Close the scanner
        sc.close();
    }
}
