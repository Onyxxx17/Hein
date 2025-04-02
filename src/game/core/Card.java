package game.core;

import java.util.Objects;

/**
 * Represents a playing card with a color and numerical value. Cards can be
 * compared for equality and displayed in either simple or ASCII art format.
 */
public class Card {

    // ============================ Attributes ============================
    /**
     * The color of the card (e.g., "red", "blue").
     */
    private final String color;

    /**
     * The numerical value of the card.
     */
    private int value;

    /**
     * Flag to determine if cards should render in simple format (true) or ASCII
     * art (false).
     */
    private static boolean simpleDisplayMode = false;

    /**
     * ANSI escape code for resetting text formatting.
     */
    private static final String RESET = "\u001B[0m";

    // ============================ Constructor ============================
    /**
     * Creates a card with the specified color and value.
     *
     * @param color The color of the card (case-insensitive).
     * @param value The numerical value of the card.
     */
    public Card(String color, int value) {
        this.color = color;
        this.value = value;
    }

    // ============================ Getters & Setters ============================
    public int getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static void setDisplayMode(boolean simpleMode) {
        simpleDisplayMode = simpleMode;
    }

    // ============================ Core Methods ============================
    /**
     * Checks if two cards are equal based on their color and value.
     *
     * @param obj The object to compare against.
     * @return {@code true} if both color and value match, {@code false}
     * otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Card card)) {
            return false;
        }
        return value == card.value && Objects.equals(color.toLowerCase(), card.color.toLowerCase());
    }

    // ============================ Helper Methods ============================
    /**
     * Converts a color name to its corresponding Unicode emoji.
     *
     * @param color The color name to convert.
     * @return The emoji string representing the color.
     */
    private static String colorToEmoji(String color) {
        return switch (color.toLowerCase()) {
            case "red" ->
                "🔴";
            case "green" ->
                "🟢";
            case "purple" ->
                "🟣";
            case "grey" ->
                "🔘";
            case "orange" ->
                "🟠";
            case "blue" ->
                "🔵";
            default ->
                "🃏";
        };
    }

    /**
     * Returns the ANSI color code for a given color.
     *
     * @param color The color name.
     * @return The ANSI escape code for that color.
     */
    public static String getColorCode(String color) {
        return switch (color.toLowerCase()) {
            case "blue" ->
                "\u001B[34m";
            case "green" ->
                "\u001B[32m";
            case "grey" ->
                "\u001B[37m";
            case "orange" ->
                "\u001B[38;5;214m";
            case "purple" ->
                "\u001B[35m";
            case "red" ->
                "\u001B[31m";
            default ->
                RESET;
        };
    }

    /**
     * Gets the animal emoji associated with a card color.
     *
     * @param color The color of the card.
     * @return A string representing the animal emoji.
     */
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

    // ============================ Display Methods ============================
    /**
     * Returns a string representation of the card.
     *
     * @return The string representation, either simple format or ASCII art.
     */
    @Override
    public String toString() {
        return simpleDisplayMode ? getSimpleRepresentation() : getAsciiArtRepresentation();
    }

    /**
     * Returns the simple text representation of the card.
     *
     * @return A formatted string with emoji and color.
     */
    private String getSimpleRepresentation() {
        return String.format("%s [%s%s %d%s]",
                colorToEmoji(color), getColorCode(color), color, value, RESET);
    }

    /**
     * Returns the ASCII art representation of the card.
     *
     * @return A multi-line string representing the card.
     */
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
