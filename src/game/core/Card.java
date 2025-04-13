package game.core;
import game.renderer.CardUI;
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

    // ============================ Getters============================
    public int getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }
    // ============================ Setters============================

    //Mainly for testing purposes
    public void setValue(int value) {
        this.value = value;
    }

    // ============================ Display Methods ============================
    /**
     * Returns a string representation of the card.
     *
     * @return The string representation, either simple format or ASCII art.
     */
    @Override
    public String toString() {
        return CardUI.renderToString(this);
    }
}