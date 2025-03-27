package game.core;

import game.utils.Helper;

/**
 * Represents a playing card with a color and value.
 */
public class Card {
    /** The color of the card. */
    public String color;

    /** The value of the card. */
    public int value;

    /**
     * Creates a card with the given color and value.
     */
    public Card(String color, int value) {
        this.color = color;
        this.value = value;
    }

    // Get methods
    /** Returns the card's value. */
    public int getValue() {
        return value;
    }

    /* Returns the card's color. */
    public String getColor() {
        return color;
    }

    // Set methods
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Checks if this card is equal to another card based on color and value.
     * 
     * @param c the card to compare with
     * @return true if the cards have the same color and value, false otherwise
     */
    public boolean equals(Card c) {
        return this.color.equals(c.getColor()) && this.value == c.getValue();
    }

    // Maps colour to a corresponding colour emoji
    private String colorToEmoji (String color) {
        switch(color.toLowerCase()) {
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

    @Override
    public String toString() {
        return colorToEmoji(color) + Helper.getColorCode(color) + "[" + color + " " + value + "]" + "\u001B[0m";
    }
}