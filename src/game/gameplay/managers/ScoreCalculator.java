package game.gameplay.managers;

import game.core.*;
import java.util.List;
public class ScoreCalculator {
    public void calculateFinalScores(List<Player> players) {
        players.forEach(p -> p.calculateScore());
        // Map<String, List<Card>> openCards = generateRandomOpenCards();
        // players.forEach(p -> p.setOpenCards(openCards));
    }

    //For Debugging purposes only
    // public static Map<String, List<Card>> generateRandomOpenCards() {
    //     // Define possible colors and card values
    //     List<String> colors = Arrays.asList("Red", "Blue", "Purple");
    //     Random random = new Random();

    //     Map<String, List<Card>> openCards = new HashMap<>();

    //     // Generate 8 random cards for each color
    //     for (String color : colors) {
    //         List<Card> cards = new ArrayList<>();
    //         for (int i = 0; i < 8; i++) {
    //             int value = random.nextInt(10); // Random value between 1 and 13
    //             cards.add(new Card(color, value));
    //         }
    //         openCards.put(color, cards);
    //     }

    //     return openCards;
    // }
}