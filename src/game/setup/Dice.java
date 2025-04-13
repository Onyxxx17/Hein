package game.setup;

import game.utils.AsciiArt;
import game.utils.Helper;
import java.util.Random;

/**
 * The Dice class represents a six-sided dice and provides methods for rolling
 * the dice and animating the dice roll.
 */
public class Dice {

    private static final Random rand = new Random();

    // rolls the dice and returns a random value between 1 and 6
    public int roll() {
        return rand.nextInt(6) + 1;
    }

    // returns the dice face as a string
    public String getDiceFace(int value) {
        return AsciiArt.DICE_FACES[value - 1];
    }

    // animates the dice roll
    public void animateRoll(String playername, int index) {
        System.out.print("\n🎲 " + playername + " is rolling the dice");
        Helper.loading();
        System.out.println(getDiceFace(index)); // Display one random dice face
    }
}
