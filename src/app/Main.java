package app;

import game.core.*;
import game.gameplay.*;
import game.renderer.*;
import game.setup.*;
import game.utils.*;
import java.util.*;
public class Main {

    public static void main(String[] args) {
        // Create a scanner object for user input
        Scanner sc = new Scanner(System.in);
    
        // Display the welcome message to the user
        GameState.welcomeMessage(sc);
    
        // Ask the user for the number of players and store the input
        int playerCount = PlayerSetup.askForNumberOfPlayers(new Scanner(System.in));
    
        // Create the list of players based on the input number and pass the scanner for player setup
        ArrayList<Player> players = PlayerSetup.createPlayers(playerCount, sc);
    
    
        // Wait for the user to press Enter to continue to the next step
        Helper.pressEnterToContinue(sc);
    
        // Clear the screen (or any other action defined in Helper.flush())
        Helper.flush();
    
        // Create a new deck of cards for the game
        Deck deck = new Deck();
    
        // Set up the game manager, which holds the game state and flow
        GameManager gameManager = new GameManager(players, deck);
    
        // Create a Game object to manage the game flow using the game manager and scanner for user input
        Game game = new Game(gameManager, sc);
    
        // Start the game by calling the startGame method
        game.startGame();
    
        // Close the scanner to prevent resource leakage
        sc.close();
    }
    
}
