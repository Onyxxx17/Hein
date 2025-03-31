package game.core;

/**
 * Represents a playing card with a color and numerical value. Cards can be
 * compared for equality and formatted with color-specific emojis for display.
 */
public class Card {

    // ============================ Attributes ============================
    /**
     * The color of the card (e.g., "red", "blue").
     */
    public String color;

    /**
     * The numerical value of the card.
     */
    public int value;

    /**
     * Flag to determine if cards should render in simple format (true) or ASCII
     * art (false)
     */
    private static boolean simpleDisplayMode = false;
    private static final String RESET = "\u001B[0m";
    // ============================ Constructor ============================
    /**
     * Creates a card with the specified color and value.
     *
     * @param color The color of the card (case-sensitive).
     * @param value The numerical value of the card.
     */
    public Card(String color, int value) {
        this.color = color;
        this.value = value;
    }

    // ============================ Getter Methods ============================
    /**
     * Returns the numerical value of the card.
     *
     * @return The card's value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the color of the card.
     *
     * @return The card's color as a string.
     */
    public String getColor() {
        return color;
    }

    // ============================ Setter Methods ============================
    /**
     * Updates the numerical value of the card.
     *
     * @param value The new value to assign.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Sets the display mode for all cards.
     *
     * @param simpleMode true for color+value+emoji, false for ASCII art
     */
    public static void setDisplayMode(boolean simpleMode) {
        simpleDisplayMode = simpleMode;
    }

    // ============================ Core Methods ============================
    /**
     * Compares this card with another for equality based on color and value.
     *
     * @param c The card to compare against.
     * @return {@code true} if both color and value match, {@code false}
     * otherwise.
     */
    public boolean equals(Card c) {
        return this.color.equals(c.getColor()) && this.value == c.getValue();
    }

    // ============================ Helper Methods ============================
    /**
     * Converts a color name to its corresponding Unicode emoji
     * (case-insensitive). Unrecognized colors return a joker emoji (🃏).
     *
     * @param color The color name to convert.
     * @return The emoji string representing the color.
     */
    private String colorToEmoji(String color) {
        switch (color.toLowerCase()) {
            case "red":
                return "🔴";
            case "green":
                return "🟢";
            case "purple":
                return "🟣";
            case "grey":
                return "🔘";
            case "orange":
                return "🟠";
            case "blue":
                return "🔵";
            default:
                return "🃏";
        }
    }
    public static String getColorCode(String color) {
        switch (color.toLowerCase()) {
            case "blue": return "\u001B[34m"; // Blue
            case "green": return "\u001B[32m"; // Green
            case "grey": return "\u001B[37m"; // Grey
            case "orange": return "\u001B[38;5;214m"; // Orange (ANSI extended color code)
            case "purple": return "\u001B[35m"; // Purple
            case "red": return "\u001B[31m"; // Red
            default: return "\u001B[0m"; // Reset (Default color)
        }
    }
    private String getAnimalArt(String color) {
        String colorCode = getColorCode(color);
        return switch (color.toLowerCase()) {
            case "red" ->
                colorCode + "│   🦊    │" + RESET;
            case "blue" ->
                colorCode + "│   🐳    │" + RESET;
            case "green" ->
                colorCode + "│   🐢    │" + RESET;
            case "orange" ->
                colorCode + "│   🦁    │" + RESET;
            case "purple" ->
                colorCode + "│   🦄    │" + RESET;
            case "grey" ->
                colorCode + "│   🐺    │" + RESET;
            default ->
                colorCode + "│   ❓    │" + RESET;
        };
    }

    // ============================ Enhanced toString() ============================
    @Override
    public String toString() {
        if (simpleDisplayMode) {
            return getSimpleRepresentation();
        } else {
            return getAsciiArtRepresentation();
        }
    }

// ============================ Display Helpers ============================
    private String getSimpleRepresentation() {
        return colorToEmoji(color) + " ["
                + getColorCode(color) + color + " " + value + "]\033[0m";
    }

    private String getAsciiArtRepresentation() {
        String colorCode = getColorCode(color);
        String reset = "\033[0m";

        String cardTop = colorCode + "┌─────────┐" + reset;
        String cardBottom = colorCode + "└─────────┘" + reset;
        String cardMiddleTop = String.format(colorCode + "│ %-7d │" + reset, value);
        String cardMiddleBottom = String.format(colorCode + "│ %7d │" + reset, value);
        String animalArt = getAnimalArt(color);

        return String.join("\n",
                cardTop,
                cardMiddleTop,
                colorCode + "│         │" + reset,
                animalArt,
                colorCode + "│         │" + reset,
                cardMiddleBottom,
                cardBottom
        );
    }

}
