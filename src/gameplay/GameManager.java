package gameplay;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import coreclasses.*;

public class GameManager {
    private ArrayList<Player> players;
    private Map<String, ArrayList<Card>> openCards;
    private Deck deck ;
    

    public GameManager(ArrayList<Player> players) {
        this.players = players;
        this.deck = new Deck();
        
    }

    public void dealCards() {
        for (int i = 0; i < players.size(); i++) {
            for (Player p : players) {
                p.drawCardFromDeck(deck);
            } 
        }
        
    }

    public boolean checkEndGame() { // false by default --> boolean checkEndGame = false,, if true, then trigger end game
        //System.out.println("Checking end game..."); // check if method is working
        if (deck.isEmpty()) {
            System.out.println("No more cards are left in the deck");
            return true;
        }

        for (Player p : players) {
            openCards = p.getOpenCards();

            if (openCards.size() == 6) {
                boolean haveAllColour = true;

                for (List<Card> list : p.getOpenCards().values()) { 
                    if (list.isEmpty()) { 
                        haveAllColour = false;
                        break;
                    } 
                } 
    
                if (haveAllColour) {
                    System.out.println("A Player has collected all 6 colour cards!!");
                    return true;
                }
            }
            
        }

        return false;
    }

    public ArrayList<Player> checkPlayerWithMaxCards(String colour) {
        int max = 0;
        ArrayList<Player> playersOut = new ArrayList<Player>();

        for (Player p : players) {
            if (p == null || p.getOpenCards() == null || !p.getOpenCards().containsKey(colour)) {
                continue;
            }

            int numCards = p.getOpenCards().get(colour).size();
            if (players.size() == 2)  {
                if (numCards >= max + 2) {
                    max = numCards;
                    playersOut.clear(); // reset as a new max is found
                    playersOut.add(p); // add that player to the player with max card 
                }
            } else { // handling > 2 players (no special rule)
                if (numCards >= max) {
                    max = numCards;
                    playersOut.clear(); // reset as a new max is found
                    playersOut.add(p);
                } else if (numCards == max) {
                    playersOut.add(p);
                }
            }
        }
        return playersOut;
    }

    public void flipCards() {
        String[] colors = { "Blue", "Green", "Grey", "Orange", "Purple", "Red" };

        for (String c : colors) {
            ArrayList<Player> maxPlayers = checkPlayerWithMaxCards(c);

            if (maxPlayers.isEmpty()) { // if no player has the max (could be same # of cards / both don't have)
                continue;
            }

            for (Player p : maxPlayers) {
                if (p == null || p.getOpenCards() == null || !p.getOpenCards().containsKey(c)) {
                    continue;
                }
                for (Card card : p.getOpenCards().get(c)) {
                    card.setValue(1);
                }
            }
            
        }
    }

    public void calcScore(ArrayList<Player> player) {
        String[] colors = { "Blue", "Green", "Grey", "Orange", "Purple", "Red" };        

        for (Player p : player) {
            int totalScore = 0;

            for (String color : colors) {
                int colorScore = 0;
                List<Card> openCards = p.getOpenCards().getOrDefault(color, new ArrayList<>());
                // getOrDefault: if the map contains the key, returns the value associated with it
                // if the code does not contain the color as key (ie. player does not have that color card), it will return a new empty list instead of null

                for (Card c : openCards) {
                    if(c.getValue() == 1) {
                        colorScore += 1;
                    } else {
                        colorScore += c.getValue();
                    }
                }

                totalScore += colorScore;
            }

            for (Card c : p.getClosedCards()) {
                totalScore += c.getValue();
            }

            p.setScore(totalScore);
            System.out.println("\n" + p.getName() + " has " + totalScore + " score!");
        }
    }

    public Player decideWinner() {

        // creating a variable to hold min score
        int leastScore = players.get(0).getScore();

        //creating result object
        Player winner = players.get(0);

        // looping to find least score
        for (Player p : players) {
            if (p.getScore() < leastScore) {
                leastScore = p.getScore();
                winner = p;
            }
        }
        return winner;
    }
}