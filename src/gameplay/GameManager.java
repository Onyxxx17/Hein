package gameplay;

import coreclasses.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameManager {

    private ArrayList<Player> players;
    private Map<String, ArrayList<Card>> openCards;
    private Deck deck;
    private static final String[] colors = {"Blue", "Green", "Grey", "Orange", "Purple", "Red"};

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
                    System.out.println(p.getName() + " has collected all 6 colour cards!!");
                    return true;
                }
            }

        }

        return false;
    }

    public ArrayList<Player> checkPlayerWithMaxCards(String colour) {
        int max = 0;
        ArrayList<Player> playersOut = new ArrayList<>();

        for (Player p : players) {
            if (p == null || p.getOpenCards() == null || !p.getOpenCards().containsKey(colour)) {
                continue;
            }

            int numCards = p.getOpenCards().get(colour).size();
            // handling > 2 players (no special rule)
            if (numCards > max) {
                max = numCards;
                playersOut.clear(); // reset as a new max is found
                playersOut.add(p);
            } else if (numCards == max) {
                playersOut.add(p);
            }
        }
        System.out.println("Max cards for " + colour + " is " + max);
        // Condition for two-player case
        if (players.size() == 2) {
            Boolean player1Contains = players.get(0).getOpenCards().containsKey(colour);
            Boolean player2Contains = players.get(1).getOpenCards().containsKey(colour);

            int cards0 = 0;
            int cards1 = 0;

            // Handle cases where one or both players don't have the colour
            if (!player1Contains && !player2Contains) {
                // Both players have no cards of the given colour
                System.out.println("Neither player has any " + colour + " cards.");
                playersOut.clear();
            } else if (!player1Contains || !player2Contains) {
                // One player has no cards of the given colour
                if (!player1Contains && players.get(1).getOpenCards().get(colour).size() == 1) {
                    System.out.println("Player 1 has no " + colour + " cards, and Player 2 has only 1 card. Difference is not enough.");
                    playersOut.clear();
                } else if (!player2Contains && players.get(0).getOpenCards().get(colour).size() == 1) {
                    System.out.println("Player 2 has no " + colour + " cards, and Player 1 has only 1 card. Difference is not enough.");
                    playersOut.clear();
                }
            } else {
                // Both players have cards of the given colour
                cards0 = players.get(0).getOpenCards().get(colour).size();
                cards1 = players.get(1).getOpenCards().get(colour).size();
                int difference = Math.abs(cards0 - cards1);

                if (difference < 2) {
                    System.out.println("The difference between the two players is not enough to flip cards. It needs to be at least 2.");
                    playersOut.clear();
                }
            }
        }
        return playersOut;
    }

    public void flipCards() {

        for (String c : colors) {
            ArrayList<Player> maxPlayers = checkPlayerWithMaxCards(c);

            if (maxPlayers.isEmpty()) { // if no player has the max (could be same # of cards / both don't have)
                continue;
            }

            for (Player p : maxPlayers) {
                // if (maxPlayers.size() > 1) {
                //     System.out.print(p.getName());
                //     if (p != maxPlayers.get(maxPlayers.size() - 1)) {
                //         System.out.println(", ");
                //     }
                // } else {
                //     System.out.print(p.getName() + " ");
                // }
                for (Card card : p.getOpenCards().get(c)) {
                    System.out.println(card + "'s value is set to 1");
                    card.setValue(1);
                }
            }
        }

        for (Player p : players) {
            System.out.println("\n" + p.getName() + " open cards after flipping:");
            for (String color : colors) {
                List<Card> openCards = p.getOpenCards().getOrDefault(color, new ArrayList<>());
                System.out.print(color + " cards: ");

                if (openCards.isEmpty()) {
                    System.out.print("No Cards");
                }
                
                for (int i = 0; i < openCards.size(); i++) {
                    Card card = openCards.get(i);
                    if(card.getValue() == 1) {
                        System.out.print("[" + color + "] ");

                        if (i != openCards.size() - 1){
                            System.out.print("-- ");
                        }
                    } else {
                        System.out.print("[" + color + " " + card.getValue() + "] ");

                        if (i != openCards.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                }

                System.out.println();

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

        for (Player player : players) {
            player.calculateScore();
            System.out.println(player.getName() + "'s score: " + player.getScore());
        }
        Collections.sort(players, new PlayerComparator());

        //Winner willl be at index 0
        Player winner = players.get(0);
        return winner;
    }
}
