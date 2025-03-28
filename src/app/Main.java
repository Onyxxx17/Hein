package app;

import game.core.*;
import game.gameplay.*;
import game.utils.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        // Welcome message
        GameUtil.welcomeMessage(sc);

        int playerCount = GameUtil.askForNumberOfPlayers(new Scanner(System.in));

        // Preparing to start game by assigning players and creating a deck of cards
        ArrayList<Player> players = GameUtil.createPlayers(playerCount, sc);
        GameUtil.pressEnterToContinue(sc);
        sc.nextLine();
        Helper.flush();
        Deck deck = new Deck();

        // Setting up the game flow
        GameManager gameManager = new GameManager(players, deck);
        GameControl game = new GameControl(gameManager,sc);

        // Starting the game
        game.startGame();
        sc.close();
    }
}
