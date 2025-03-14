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
        ArrayList<Player> players = GameUtil.createPlayers(playerCount, sc);
        decideStartingPlayer(players);

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
        }

        System.out.println("Adding two cards to open cards for final play.\n");
        for (Player player : players) {
            System.out.println(player.getName() + "'s Turn");
            player.finalPlay(parade, sc);
            player.showOpenCards();
            GameUtil.pressEnterToContinue(sc);
        }

        Helper.flush();
        for (Player player : players) {
            player.showOpenCards();
        }
        // Close the scanner
        sc.close();
    }

    static void decideStartingPlayer(ArrayList<Player> players) {

        int[] rolls = new int[players.size()];

        // Each player / computer rolls

        for (int i = 0; i < players.size(); i++) {
            try {
                RollDice.animateRoll(players.get(i).getName());
                int roll = RollDice.roll();
                System.out.println(RollDice.getDiceFace(roll));
                System.out.println(players.get(i).getName() + " rolled " + roll);
                rolls[i] = roll;

            } catch (InterruptedException e) {
                System.out.println("Animation interrupted for player: " + players.get(i).getName());
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Find highest roll

        ArrayList<Player> winners = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < rolls.length; i++) {
            if (rolls[i] > max) {
                max = rolls[i];
            }
        }

        for (int i = 0; i < rolls.length; i++) {
            if (rolls[i] == max) {
                winners.add(players.get(i));
            }
        }

        // Handle multiple winners
        while (winners.size() > 1) {
            System.out.println("Tie! Rerolling...");
            rolls = new int[players.size()];
            winners.clear();
            
            for (int i = 0; i < players.size(); i++) {
                try {
                    RollDice.animateRoll(players.get(i).getName());
                    int roll = RollDice.roll();
                    System.out.println(RollDice.getDiceFace(roll));
                    System.out.println(players.get(i).getName() + " rolled " + roll);
                    rolls[i] = roll;
                } catch (InterruptedException e) {
                    System.out.println("Animation interrupted for player: " + players.get(i).getName());
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        
            // finds highest value in the rolls array and assigns it to max. If rolls is empty, max will be set to 0.
            max = Arrays.stream(rolls).max().orElse(0);
            for (int i = 0; i < rolls.length; i++) {
                if (rolls[i] == max) {
                    winners.add(players.get(i));
                }
            }
        }

        Player startingPlayer = winners.get(0);
        System.out.println(startingPlayer.getName() + " goes first! ✨");

        // call
        // gameManager.playerTurn(startingPlayer)
    }
}
