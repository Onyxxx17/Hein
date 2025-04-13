package game.gameplay.managers;

import game.core.*;
import java.util.List;

/**
 * ScoreCalculator is responsible for calculating the final scores of all players.
 * It iterates through each player and calls their calculateScore method.
 */
public class ScoreCalculator {

    /**
     * Calculates the final scores for all players.
     *
     * @param players List of players.
     */
    public void calculateFinalScores(List<Player> players) {
        players.forEach(p -> p.calculateScore());
    }
}