package gameplay;
import java.util.ArrayList;
import java.util.List;

import coreclasses.*;

public class GameManager {
    private ArrayList<Player> players;
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
            return true;
        }

        for (Player p : players) {
            boolean haveAllColour = false;
            for (List<Card> list : p.getOpenCards().values()) { 
                if (list.isEmpty()) { 
                    haveAllColour = false;
                    break;
                } 
            } 

            if (haveAllColour) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Player> checkPlayerWithMaxCards(String colour) {
        int max = 0;
        ArrayList<Player> playersOut = new ArrayList<Player>();
        Player hasMaxCards = null;
        for (Player p : players) {
            int numCards = p.getOpenCards().get(colour).size();
            if (players.size() == 2)  {
                if (numCards >= max + 2) {
                    max = numCards;
                    hasMaxCards = p;
                }
            } else {
                if (numCards >= max) {
                    max = numCards;
                    hasMaxCards = p;
                }
            }
            playersOut.add(hasMaxCards);
        }
        return playersOut;
    }

    public void flipCards() {
        String[] colors = { "Blue", "Green", "Grey", "Orange", "Purple", "Red" };

        for (String c : colors) {
            ArrayList<Player> maxPlayers = checkPlayerWithMaxCards(c);
            for (Player p : maxPlayers) {
                for (Card card : p.getOpenCards().get(c)) {
                    card.setValue(1);
                }
            }
            
        }
    }

    public void calcScore(ArrayList<Player> player) {
        // looping through array list
        for (Player p : player) {
            int totalScoreEachPlayer = 0;
            for (Card c : p.getClosedCards()) {
                totalScoreEachPlayer += c.getValue();
            }
            p.setScore(totalScoreEachPlayer);
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