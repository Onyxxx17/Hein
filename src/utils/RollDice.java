package utils;
import java.util.Random;

public class RollDice {
    private static final Random rand = new Random();
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
        ""","""
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

    public static int roll() {
        // Range of values {1, 2, 3, 4, 5, 6}
        return rand.nextInt(6) + 1;
    }

    public static String getDiceFace(int value) {
        if (value < 1 || value > 6) {
            return " ";
        } else {
            return DICE_FACES[value - 1];
        }
    }

    public static void animateRoll(String playername) throws InterruptedException {
        System.out.println("\n🎲 " + playername + " is rolling the dice...");
        Thread.sleep(1000); // Delay for 1 second
        System.out.println(DICE_FACES[rand.nextInt(6)]); // Display one random dice face
    }
}