package game.utils;

public class Constants {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static int CARDS_PER_LINE = 7;
    public static final int INITIAL_CARDS_OF_PARADE = 6;

    public static final String[] COLORS = {"Blue", "Green", "Grey", "Orange", "Purple", "Red"};

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 6;

    public static final int MAX_CARDS_PER_COLOUR = 10;

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
