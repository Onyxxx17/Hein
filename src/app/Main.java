package app;

import game.core.*;
import game.gameplay.*;
import game.renderer.*;
import game.setup.*;
import java.util.*;
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GameState.welcomeMessage(sc);

        PlayerSetup setup = new PlayerSetup(sc);
        int playerCount = setup.askForNumberOfPlayers();
        List<Player> players = setup.createPlayers(playerCount);

        Deck deck = new Deck();
        GameManager gameManager = new GameManager(players, deck);
        Game game = new Game(gameManager, sc);
        game.startGame();
    
        sc.close();
    }
    
}
