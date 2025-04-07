package game.renderer;
import game.core.Card;
import game.utils.Helper;

public class ComputerRenderer {

    public static void renderNoCardsLeft(String name) {
        System.out.println(name + " has no cards left to play!");
    }

    public static void renderComputerThinking(String name) {
        System.out.print(name + " is thinking");
        Helper.loading();
    }

    public static void renderComputerPlayedCard(String name, Card card) {
        System.out.println("\n" + name + " played: ");
        System.out.println(card);
    }

    public static void renderNoMoreCardsToMove(String name) {
        System.out.println(name + " has no more cards to move to open cards.");
    }

    public static void renderCardAddedToOpenCards(String name, Card card) {
        System.out.println(card + " is added to " + name + "'s Open Cards!\n");
    }
}