package game.setup;

import game.utils.Constants;
import game.utils.Helper;
import java.util.Random;

/**
 * A utility class to handle dice operations in the game. This class combines
 * both dice logic (random number generation) and presentation (ASCII art
 * visualization) for simplicity, as these functions are tightly coupled in the
 * context of this game.
 */
public class Dice {

    private static final Random rand = new Random();
    
    /**
     * Rolls a six-sided dice and returns a random number between 1 and 6.
     *
     * @return An integer value representing the result of the dice roll.
     */
    public int roll() {
        // Range of values {1, 2, 3, 4, 5, 6}
        return rand.nextInt(6) + 1;
    }

    /**
     * Retrieves the ASCII art representation of a dice face based on the given
     * value.
     *
     * @param value The integer value of the dice roll (1 to 6).
     * @return The string representation of the dice face if the value is valid;
     * otherwise, returns an empty space.
     */
    public String getDiceFace(int value) {
        return Constants.DICE_FACES[value - 1];
    }

    /**
     * Simulates the animation of a dice roll for a given player and displays
     * the result.
     *
     * @param playername The name of the player rolling the dice.
     * @param index The index of the dice face to display (1-based).
     */
    public void animateRoll(String playername, int index) {
        System.out.println("\n🎲 " + playername + " is rolling the dice...");
        Helper.sleep(1500); // Delay for 1 second
        System.out.println(getDiceFace(index)); // Display one random dice face
    }
}
