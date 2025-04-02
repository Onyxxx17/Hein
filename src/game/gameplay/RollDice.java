package game.gameplay;

import java.util.Random;

public class RollDice {

    private static final Random RAND = new Random();
    private static final String[] DICE_FACES = {
        """
        +-------+
        |       |
        |   ●   |
        |       |
        +-------+
        """,
        """
        +-------+
        | ●     |
        |       |
        |     ● |
        +-------+
        """,
        """
        +-------+
        | ●     |
        |   ●   |
        |     ● |
        +-------+
        """, """
        +-------+
        | ●   ● |
        |       |
        | ●   ● |
        +-------+
        """,
        """
        +-------+
        | ●   ● |
        |   ●   |
        | ●   ● |
        +-------+
        """,
        """
        +-------+
        | ●   ● |
        | ●   ● |
        | ●   ● |
        +-------+
        """
    };

    /**
     * Rolls a six-sided dice and returns a random number between 1 and 6.
     *
     * @return An integer value representing the result of the dice roll.
     */
    public static int roll() {
        // Range of values {1, 2, 3, 4, 5, 6}
        return RAND.nextInt(6) + 1;
    }

    /**
     * Retrieves the ASCII art representation of a dice face based on the given
     * value.
     *
     * @param value The integer value of the dice roll (1 to 6).
     * @return The string representation of the dice face if the value is valid;
     * otherwise, returns an empty space.
     */
    public static String getDiceFace(int value) {
        if (value < 1 || value > 6) {
            return " ";
        } else {
            return DICE_FACES[value - 1];
        }
    }

    /**
     * Simulates the animation of a dice roll for a given player and displays
     * the result.
     *
     * @param playername The name of the player rolling the dice.
     * @param index The index of the dice face to display (1-based).
     * @throws InterruptedException If the thread is interrupted while sleeping.
     */

    public static void animateRoll(String playername, int index) throws InterruptedException {
        System.out.println("\n🎲 " + playername + " is rolling the dice...");
        Thread.sleep(1500); // Delay for 1 second
        System.out.println(DICE_FACES[index - 1]); // Display one random dice face
    }
}
