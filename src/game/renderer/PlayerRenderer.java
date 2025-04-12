package game.renderer;

import game.core.*;
import game.utils.Helper;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerRenderer {

    /**
     * Displays cards received by a player in ASCII art format.
     */
    public static void showReceivedCards(Player player, ArrayList<Card> receivedCards) {
        if (receivedCards.isEmpty()) {
            System.out.printf("%s receives no cards this round.%n", player.getName());
            return;
        }

        System.out.printf("%s receives:%n", player.getName());
        showCardGroup(receivedCards);
    }

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
    private static void showCardGroup(ArrayList<Card> cards) {
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

    public static void showCardDraw(Player player) {
        Helper.sleep(1000);
        System.out.println("\n💎 " + player.getName() + " draws one card from the deck.");
    }

    public static void showCardAddedToOpenCards(String name, Card card) {
        CardUI.setSimpleDisplayMode(true);
        System.out.println(card + " is added to " + name + "'s Open Cards!\n");
    }

    public static void showComputerThinking(String name) {
        System.out.print(name + " is thinking");
        Helper.loading();
    }

    /**
     * Displays the human player's closed cards in ASCII art format.
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

        displayCardsHorizontally(closedCards);
    }

    /**
     * Helper method to display a list of cards horizontally in ASCII art.
     */
    private static void displayCardsHorizontally(List<Card> cards) {
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

    /**
     * Handles and validates user input for selecting a card.
     */
    public static int getValidCardSelection(boolean isHuman, Scanner scanner, int maxCards) {
        if (isHuman) {
            while (true) {
                System.out.print("Enter the number of the card to play (1-" + maxCards + "): ");
                if (scanner.hasNextInt()) {
                    int index = scanner.nextInt();
                    if (index >= 1 && index <= maxCards) {
                        return index;
                    }
                } else {
                    scanner.next(); // Consume invalid input
                }
                System.out.println("Invalid input! Enter a number between 1 and " + maxCards + ".\n");
            }
        }
        return 0;
    }
}
