package game.gameplay.managers;

import game.core.*;
import java.util.List;
public class ScoreCalculator {
    public void calculateFinalScores(List<Player> players) {
        players.forEach(p -> p.calculateScore());
    }
}