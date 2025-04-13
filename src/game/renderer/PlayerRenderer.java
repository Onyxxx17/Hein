package game.renderer;

import game.core.*;
import game.utils.Constants;
import game.utils.Helper;
import java.util.*;
import java.util.stream.Collectors;

/**
 * PlayerRenderer is responsible for rendering various player-related messages
 * and animations during the game. It includes methods for displaying received
 * cards, played cards, open cards, closed cards, and other player actions.
 */
public class PlayerRenderer {

    /**
     * Displays cards received by a player in ASCII art format.
     */
    public static void showReceivedCards(Player player, List<Card> receivedCards) {
        if (receivedCards.isEmpty()) {
            System.out.printf("%s receives no cards this round.%n", player.getName());
            return;
        }

        System.out.printf("%s receives:%n", player.getName());
        showCardGroup(receivedCards);
    }

    /**
     * Displays a card played by a player in ASCII art format.
     *
     * @param card The card played by the player.
     * @param name The name of the player who played the card.
     */
    public static void showPlayedCard(Card card, String name) {
        System.out.println(name + " played: ");
        CardUI.setSimpleDisplayMode(false);
        System.out.println(card);
    }

    /**
     * Displays a player's open cards grouped by color.
     */
    public static void showOpenCards(Player player) {
        Map<String, List<Card>> openCards = player.getOpenCards();

        if (openCards.isEmpty()) {
            System.out.printf("%s has no open cards.%n%n", player.getName());
            return;
        }

        CardUI.setSimpleDisplayMode(true);
        System.out.printf("🎴 %s's Open Cards:%n", player.getName());

        openCards.forEach((color, cards)
                -> System.out.printf("%s cards: %s%n",
                        color,
                        cards.stream()
                                .map(Card::toString)
                                .collect(Collectors.joining(", ")))
        );

        System.out.println();
    }

    /**
     * Shared helper for rendering a group of cards.
     */
    private static void showCardGroup(List<Card> cards) {
        CardUI.setSimpleDisplayMode(false);
        StringBuilder[] lines = new StringBuilder[7];

        // Initialize lines
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new StringBuilder();
        }

        // Build card display
        for (Card card : cards) {
            String[] parts = card.toString().split("\n");
            for (int i = 0; i < parts.length; i++) {
                lines[i].append(parts[i]).append("   ");
            }
        }

        // Print the assembled display
        for (StringBuilder line : lines) {
            System.out.println(line.toString().stripTrailing());
        }
    }

    /**
     * Announces that a player is drawing a card from the deck.
     *
     * @param player The player that is drawing the card.
     */
    public static void showCardDraw(Player player) {
        Helper.sleep(Constants.NORMAL_DELAY_TIME);
        System.out.println("\n💎 " + player.getName() + " draws one card from the deck.");
    }

    /**
     * Displays a message indicating that a card has been added to the player's open cards.
     *
     * @param name The name of the player.
     * @param card The card that was added.
     */
    public static void showCardAddedToOpenCards(String name, Card card) {
        CardUI.setSimpleDisplayMode(true);
        System.out.println(card + " is added to " + name + "'s Open Cards!\n");
    }

    /**
     * Displays a message indicating that a card has been added to the player's closed cards.
     *
     * @param name The name of the player.
     * @param card The card that was added.
     */
    public static void showComputerThinking(String name) {
        System.out.print(name + " is thinking");
        Helper.loading();
    }

    
    /**
     * Displays the player's closed cards (hand) in a horizontal layout, with
     * each card's index displayed above it.
     *
     * @param player The player whose closed cards are to be displayed.
     */
    public static void showClosedCards(Player player) {
        List<Card> closedCards = player.getClosedCards();
        if (closedCards.isEmpty()) {
            System.out.println("Your hand is empty.");
            return;
        }

        CardUI.setSimpleDisplayMode(false);
        System.out.println("🎴 Your hand:");

        for (int i = 0; i < closedCards.size(); i++) {
            System.out.printf("    [%d]      ", i + 1);
        }
        System.out.println();

        showCardsHorizontally(closedCards);
    }

    /**
     * Displays the player's closed cards (hand) in a horizontal layout, with
     * each card's index displayed above it.
     *
     * @param player The player whose closed cards are to be displayed.
     */
    private static void showCardsHorizontally(List<Card> cards) {
        StringBuilder[] cardLines = new StringBuilder[7];
        for (int i = 0; i < cardLines.length; i++) {
            cardLines[i] = new StringBuilder();
        }

        for (Card card : cards) {
            String[] cardParts = card.toString().split("\n");
            for (int i = 0; i < cardParts.length; i++) {
                cardLines[i].append(cardParts[i]).append("   ");
            }
        }

        for (StringBuilder line : cardLines) {
            System.out.println(line.toString().stripTrailing());
        }
    }
}
