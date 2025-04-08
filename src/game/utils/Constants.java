package game.utils;

public class Constants {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREY = "\u001B[37m";
    public static final String ORANGE = "\u001B[38;5;214m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String BLACK = "\u001B[30m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";
    public static final String DEFAULT_COLOR= "\u001B[0m";

    public static final int DELAY_DURATION = 45;
    public static int CARDS_PER_LINE = 7;
    public static final int INITIAL_CARDS_OF_PARADE = 6;
    public static final int TOTAL_COLORS = 6;
    public static final String[] COLORS = {"Blue", "Green", "Grey", "Orange", "Purple", "Red"};
    public static final int FINAL_PLAY_MOVES = 2;
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 6;
    public static final int MIN_DIFFERENCE_FOR_TWO_PLAYERS = 2; // Minimum card count difference for two-player flipping
    public static final int FLIPPED_CARD_VALUE = 1; // Value assigned to flipped cards
    public static final int MAX_CARDS_PER_COLOUR = 10;
    public static final int PODIUM_SIZE = 3;

    public static final String[] DICE_FACES = {
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
}
