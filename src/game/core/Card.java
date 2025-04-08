package game.core;

import game.renderer.CardRenderer;
import java.util.Objects;

/**
 * Represents a playing card with a color and numerical value.
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

    // ============================ Display Methods ============================
    /**
     * Returns a string representation of the card.
     *
     * @return The string representation, either simple format or ASCII art.
     */
    @Override
    public String toString() {
        return CardRenderer.renderToString(this);
    }
}