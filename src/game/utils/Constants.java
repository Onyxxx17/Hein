package game.utils;

import java.util.Random;

public class Constants {

    // ============================ COLOR CODES ============================
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String GREY = "\u001B[37m";
    public static final String ORANGE = "\u001B[38;5;214m";
    public static final String BOLD = "\u001B[1m";
    public static final String RED = "\u001B[91m";

    // ============================ GAME CONSTANTS ============================
    public static final String YES_NO_REGEX = "^(yes|no|y|n)$";
    public static final String MENU_OPTION_REGEX = "^[1-3]$";
    public static final int NORMAL_DELAY_TIME = 500;
    public static final Random RANDOM = new Random();
    public static final int TYPEWRITE_DURATION = 45;
    public static int CARDS_PER_LINE = 7;
    public static final int INITIAL_CARDS_OF_PARADE = 6;
    public static final int INITIAL_HAND_SIZE = 5;
    public static final int CARDS_TO_DEAL = 5;
    public static final int TOTAL_COLORS = 6;
    public static final String[] COLORS = {"Blue", "Green", "Grey", "Orange", "Purple", "Red"};
    public static final int FINAL_PLAY_MOVES = 2;
    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 6;
    public static final int MIN_DIFFERENCE_FOR_TWO_PLAYERS = 2;
    public static final int FLIPPED_CARD_VALUE = 1;
    public static final int MAXVALUEOFCARD = 10;
    public static final int MINVALUEOFCARD = 0;
    public static final int PODIUM_SIZE = 3;
}
