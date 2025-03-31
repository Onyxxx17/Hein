package game.core;

import java.util.*;

public class Human extends Player {

    /**
     * Constructor for the Human player.
     *
     * @param name The name of the player.
     */
    public Human(String name) {
        super(name);
    }

    /**
     * Allows the human player to play a card from their hand into the parade.
     *
     * @param parade The parade where the card will be added.
     * @param scanner Scanner object for user input.
     */
    @Override
    public void playCard(Parade parade, Scanner scanner) {
        // Ensure the player has cards to play
        if (closedCards.isEmpty()) {
            System.out.println("No cards left to play!");
            return;
        }

        int index = 0;
        System.out.println();
        while (true) {
            try {
                // Prompt the player to select a card
                System.out.print("Enter the number of the card to play (1-" + closedCards.size() + "): ");
                index = scanner.nextInt();

                // Validate input range
                if (index >= 1 && index <= closedCards.size()) {
                    break; // Valid choice, exit loop
                } else {
                    System.out.println("Invalid choice! Enter a number between 1 and " + closedCards.size() + ".\n");
                }
            } catch (InputMismatchException e) {
                // Handle non-integer input
                System.out.println("Invalid input! Please enter a number.\n");
                scanner.next(); // Consume the invalid input to prevent an infinite loop
            }
        }

        // Remove the selected card from the player's hand and add it to the parade
        Card selectedCard = closedCards.remove(index - 1);
        parade.addCard(selectedCard);
        System.out.println(name + " played: ");
        System.out.println(selectedCard);
    }

    /**
     * Allows the player to choose two cards from their hand to be moved to open
     * cards before scoring.
     *
     * @param parade The parade object (not used here but passed for
     * consistency).
     * @param scanner Scanner object for user input.
     */
    @Override
    public void finalPlay(Parade parade, Scanner scanner) {
        // Display the player's hand before making selections
        showClosedCards();

        System.out.println("\nBefore we go to score calculation, choose two cards from your hand to add to open cards for scoring!\n");

        int selectedCount = 0; // Track the number of selected cards

        while (selectedCount < 2) {
            try {
                // Show the updated hand after the first selection
                if (selectedCount == 1) {
                    showClosedCards();
                }

                // Prompt the player to select a card
                System.out.print("Enter the number of the card you want to add (1-" + closedCards.size() + "): ");
                int index = scanner.nextInt();

                // Validate input range
                if (index >= 1 && index <= closedCards.size()) {
                    Card.setDisplayMode(true);
                    // Remove the selected card and add it to open cards
                    Card selectedCard = closedCards.remove(index - 1);
                    openCards.computeIfAbsent(selectedCard.getColor(), key -> new ArrayList<>()).add(selectedCard);
                    System.out.println(selectedCard + " is added to " + name + "'s Open Cards!\n");
                    selectedCount++; // Increase the count
                } else {
                    System.out.println("Invalid choice! Enter a number between 1 and " + closedCards.size() + ".");
                }
            } catch (InputMismatchException e) {
                // Handle non-integer input
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); // Consume the invalid input to prevent an infinite loop
            }
        }
    }

    /**
     * Displays the player's closed cards in ASCII art format arranged
     * horizontally, with selection numbers above each card.
     */
    public void showClosedCards() {
        Card.setDisplayMode(false); // Ensure ASCII art mode

        System.out.println("🎴 Your hand:");

        // Print selection numbers above each card
        for (int i = 0; i < closedCards.size(); i++) {
            System.out.printf("    [%d]      ", i + 1);
        }
        System.out.println();

        // Prepare card lines (7 lines for standard ASCII card)
        StringBuilder[] cardLines = new StringBuilder[7];
        for (int i = 0; i < cardLines.length; i++) {
            cardLines[i] = new StringBuilder();
        }

        // Populate each line with card parts
        for (Card card : closedCards) {
            String[] cardParts = card.toString().split("\n");
            for (int i = 0; i < cardParts.length; i++) {
                cardLines[i].append(cardParts[i]).append("   ");
            }
        }

        // Print the assembled card lines
        for (StringBuilder line : cardLines) {
            System.out.println(line.toString());
        }
    }
}
