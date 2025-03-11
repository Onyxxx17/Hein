// package app;
// import coreclasses.*;
// import exceptions.InvalidPlayerCountException;
// import java.util.*;
// import utils.GameUtil;
// public class Main {

//     public static void main(String[] args) {
//         GameUtil scanner = new GameUtil();
//         scanner.welcomeMessage();
//         int playerCount = scanner.askForNumberOfPlayers();

//         // Game start message
//         System.out.println("Starting the game with " + playerCount + " players...");

//         //List of players (Human vs Computer)
//         ArrayList<Player> players = GameUtil.createPlayers(playerCount);
//         // Start the game
//         Deck deck = new Deck();
//         deck.shuffle();
//         Parade parade = new Parade();

//         //Give initial 5 cards to each player
//         for (Player player : players) {
//             player.InitialClosedCards(deck);
//         }

//         parade.initialParade(deck);

//         for (Player player : players) {
//             parade.showParade();
//             if(player instanceof Human) {
//                 ((Human) player).showCards();
//             }
//             player.playCard(parade);
//             player.drawCardsFromParade(parade);
//             player.drawCardFromDeck(deck);
//         }
//         // Close the scanner
//         scanner.close();
//     }
// }
