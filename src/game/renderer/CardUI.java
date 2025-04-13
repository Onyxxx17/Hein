package game.renderer;

import game.core.Card;
import game.utils.Constants;
import game.utils.Helper;

/**
 * Console-based card renderer that displays cards either as ASCII art or a one-line string.
 */
public class CardUI {
    // ============================ Static Variables ============================
    private static final String RESET = Constants.RESET;
    private static boolean simpleDisplayMode = false;

    public static void setSimpleDisplayMode(boolean simpleMode) {
        simpleDisplayMode = simpleMode;
    }

    /**
     * Returns a string representation of the given card,
     * based on the current display mode.
     *
     * @param card the card to render
     * @return the rendered card string
     */
    public static String renderToString(Card card) {
        return simpleDisplayMode
                ? renderSimple(card)
                : renderAsciiArt(card);
    }

    // Renders a one-line string with color and value info.
    private static String renderSimple(Card card) {
        return String.format("%s [%s%s %d%s]",
                colorToEmoji(card.getColor()),
                Helper.getColorCode(card.getColor()),
                card.getColor(),
                card.getValue(),
                RESET);
    }

    // Renders the card as a multi-line ASCII art block.
    private static String renderAsciiArt(Card card) {
        String color = card.getColor();
        int value = card.getValue();
        String colorCode = Helper.getColorCode(color);

        String cardTop = colorCode + "┌─────────┐" + RESET;
        String cardBottom = colorCode + "└─────────┘" + RESET;
        String cardMiddleTop = String.format(colorCode + "│ %-7d │" + RESET, value);
        String cardMiddleBottom = String.format(colorCode + "│ %7d │" + RESET, value);
        String animalArt = getAnimalArt(color);

        return String.join("\n",
                cardTop,
                cardMiddleTop,
                colorCode + "│         │" + RESET,
                animalArt,
                colorCode + "│         │" + RESET,
                cardMiddleBottom,
                cardBottom
        );
    }

    // Maps color names to corresponding emojis.
    private static String colorToEmoji(String color) {
        return switch (color.toLowerCase()) {
            case "red" -> "🔴";
            case "green" -> "🟢";
            case "purple" -> "🟣";
            case "grey" -> "🔘";
            case "orange" -> "🟠";
            case "blue" -> "🔵";
            default -> "🃏";
        };
    }

    // Returns the animal art line for a given card color.
    private static String getAnimalArt(String color) {
        String colorCode = Helper.getColorCode(color);
        return switch (color.toLowerCase()) {
            case "red" -> colorCode + "│   🦊    │" + RESET;
            case "blue" -> colorCode + "│   🐳    │" + RESET;
            case "green" -> colorCode + "│   🐢    │" + RESET;
            case "orange" -> colorCode + "│   🦁    │" + RESET;
            case "purple" -> colorCode + "│   🦄    │" + RESET;
            case "grey" -> colorCode + "│   🐺    │" + RESET;
            default -> colorCode + "│   ❓    │" + RESET;
        };
    }
}
