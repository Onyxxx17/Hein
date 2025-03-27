package app;

import java.util.*;

import game.core.Deck;
import game.core.Player;
import game.gameplay.GameControl;
import game.gameplay.GameManager;
import game.utils.GameUtil;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int playerCount = GameUtil.askForNumberOfPlayers(new Scanner(System.in));

        // Preparing to start game by assigning players and creating a deck of cards
        ArrayList<Player> players = GameUtil.createPlayers(playerCount, sc);
        Deck deck = new Deck();

        // Setting up the game flow
        GameManager gameManager = new GameManager(players, deck);
        GameControl game = new GameControl(gameManager,sc);

        // Starting the game
        game.startGame();
        sc.close();
    }
}
