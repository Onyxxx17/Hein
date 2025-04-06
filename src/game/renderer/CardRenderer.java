package game.renderer;
import game.core.Card;
import game.utils.Helper;

/**
 * Console-based card renderer implementation using static methods.
 */
public class CardRenderer {

    private static final String RESET = "\u001B[0m";
    private static boolean simpleDisplayMode = false;  // Make simpleDisplayMode static
    //private static boolean guiDisplayMode = false;
    public static void setDisplayMode(boolean simpleMode) {
        simpleDisplayMode = simpleMode;
    }
    
    public static String renderToString(Card card) {
        return simpleDisplayMode ? 
               renderSimple(card) : 
               renderAsciiArt(card);
    }

    private static String renderSimple(Card card) {
        return String.format("%s [%s%s %d%s]",
                colorToEmoji(card.getColor()), 
                Helper.getColorCode(card.getColor()), 
                card.getColor(), 
                card.getValue(), 
                RESET);
    }

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