package app;
import coreclasses.*;
import gameplay.*;
import java.util.*;
import utils.*;
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int playerCount = GameUtil.askForNumberOfPlayers(new Scanner(System.in));

        // List of players (Human vs Computer)
        ArrayList<Player> players = GameUtil.createPlayers(playerCount, sc);
        Deck deck = new Deck();
        GameManager gameManager = new GameManager(players, deck);
        GameControl game = new GameControl(gameManager,sc);
        try {
            game.startGame();
        } catch (InterruptedException e) {
            System.out.println("System interrupted!");
        }
        sc.close();
    }
}
