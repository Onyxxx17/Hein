package game.renderer;

import game.core.*;
import game.utils.Constants;
import game.utils.Helper;
import java.util.*;

public class GameFlowRenderer {

    // Static utility method: Called once, independent of GameRenderer instance
    public static void showFlippedCards(Map<Player, List<Card>> flippedCards, List<Player> players) {
        for (Player p : players) {
            System.out.println("\n" + p.getName() + " open cards after flipping:");
            for (String color : Constants.COLORS) {
                List<Card> openCards = p.getOpenCards().getOrDefault(color, new ArrayList<>());
                System.out.print(color + " cards: ");
                if (openCards.isEmpty()) {
                    System.out.print("No Cards");
                }
                for (Card card : openCards) {
                    boolean contains = flippedCards.getOrDefault(p, new ArrayList<>()).contains(card);
                    if (card.getValue() == 1 && contains) {
                        System.out.print("[" + color + "] ");
                    } else {
                        System.out.print(card + " ");
                    }
                }
                System.out.println();
            }
        }
    }

    public static void displayMaxPlayersForColor(String color, List<Player> maxPlayers) {
        String colorCode = Helper.getColorCode(color);
        if (maxPlayers.isEmpty()) {
            displayNoMaxPlayersForColor(color);
            return;
        }
        displayMaxPlayersForColorList(color, maxPlayers, colorCode);
    }

    public static void displayNoMaxPlayersForColor(String color) {
        System.out.println("🎭 Player(s) with most " + Helper.getColorCode(color)
                + color + Constants.RESET + " cards: "
                + Constants.BOLD + "None" + Constants.RESET + "\n");
    }

    public static void displayMaxPlayersForColorList(String color, List<Player> maxPlayers, String colorCode) {
        System.out.print("🎉 Player(s) that will flip " + colorCode
                + color + Constants.RESET + " cards: ");
        for (int i = 0; i < maxPlayers.size(); i++) {
            System.out.print(Constants.BOLD + maxPlayers.get(i).getName() + Constants.RESET);
            if (i != maxPlayers.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(" 🎉\n");
        Helper.sleep(1200);
    }

    public static void showMaxCardsForColor(String color, int max) {
        System.out.println("Max cards for " + color + " is " + max);
    }

    public static void showNoFlippingDueToTie(String color) {
        System.out.println("All players have the same number of cards. No cards flipped for " + color);
    }

    public static void showTieBreaker(List<Player> potentialWinners) {
        // Initial dramatic pause before announcement
        Helper.sleep(800);
        System.out.println("⚔️  A tie has been detected between " + potentialWinners.size() + " players!");
        Helper.sleep(500);

        // List tied players with slight delay between each
        System.out.println("🔝 Players with the highest score: ");
        for (Player p : potentialWinners) {
            Helper.sleep(300);
            System.out.println(" - " + p.getName());
        }

        // Pause before rules explanation
        Helper.sleep(800);
        System.out.println("\n🏆 To break the tie and determine the winner, we will consider:\n");
        Helper.sleep(500);

        // Animated countdown of tiebreaker rules
        System.out.println("1️⃣ Fewest number of total cards collected.");
        Helper.sleep(500);
        System.out.println("2️⃣ Fewest number of different colors collected.");
        Helper.sleep(500);
        System.out.println("3️⃣ Final dice roll if still tied!");
        Helper.sleep(500);

        // Dramatic lead-in to details
        System.out.println("\nLet the tiebreaker begin!");
        Helper.sleep(600);
        System.out.println("\n📊 Analyzing player collections...\n");
        Helper.sleep(1000);

        // Display details with typing effect
        for (Player p : potentialWinners) {
            System.out.println("🔹 " + p.getName() + " collected:");
            Helper.sleep(300);

            System.out.print("   - Total cards: ");
            Helper.typewrite(String.valueOf(p.getTotalOpenCards()), 50);

            System.out.print("\n   - Total colors: ");
            Helper.typewrite(String.valueOf(p.getOpenCards().size()), 50);
            System.out.println();

            Helper.sleep(400); // Pause between players
        }

        // Final dramatic pause before continuing
        Helper.sleep(1000);
    }

    public static void show2PlayerRules() {
        System.out.println("The difference between the two players is not enough to flip cards. It needs to be at least 2.");
    }

    public static void showDeckSize(Deck deck) {
        System.out.println("Current deck size: " + deck.size());
    }

    public static void showGameStart(Player firstPlayer) {
        System.out.print("\n🎮 " + firstPlayer.getName() + " is shuffling the deck");
        Helper.loading();
        System.out.println("\n✅ Done!");
    }

    public static void showCardDealing() {
        System.out.print("\n🎴 Cards are being dealt");
        Helper.loading();
        System.out.println("\n💫 5 cards have been dealt to each player\n");
    }

    public static void showParadeInitialization() {
        System.out.print("✨ Initializing Parade");
        Helper.loading();
        System.out.println("\n🎉 Parade has been initialized with 6 cards!\n");
    }

    public static void showTurnHeader(String playerName) {
        Helper.printBox("🌟 " + playerName + "'s Turn");
    }

    public static void showGameOver() {
        System.out.println("Game Over!");
    }

    public static void showCardDraw(Player player) {
        System.out.println("\n" + player.getName() + " draws one card from the deck.");
    }

    public static void showDrawCardFromDeck(Player player) {
        System.out.println("\n" + player.getName() + " draws one card from the deck.");
    }

    public static void showDeckEmpty() {
        System.out.println();
        Helper.sleep(1000);
        Helper.printBox("‼️ No more cards are left in the deck ‼️\n" + playLastRound());
    }

    public static void showAllColorsCollected(Player player) {
        System.out.println();
        Helper.sleep(1000);
        Helper.printBox("‼️ " + player.getName()
                + " has collected all 6 color cards ‼️\n" + playLastRound());
    }

    private static String playLastRound() {
        return ("‼️ Every player plays one last round ‼️");
    }

    public static void displayOpenCards(List<Player> players) {
        for (Player player : players) {
            PlayerRenderer.showOpenCards(player);
        }
    }

    public static void showQuitOption() {
        System.out.print("\n🛑 Type anything to play your round or 'quit' to exit> ");
    }

    public static void confirmQuit() {
        System.out.print("\nAre you sure you want to quit? (y/n): ");
    }

    public static void showPlayerRound(Player player, List<Player> players, Parade parade, Deck deck) {
        GameFlowRenderer.showDeckSize(deck);
        GameFlowRenderer.displayOpenCards(players);
        ParadeRenderer.showParade(parade);
        GameFlowRenderer.showTurnHeader(player.getName());
    }

    public static void showGameOverOnlyOnePlayerLeft() {
        System.out.println("\n==============================================");
        System.out.println("║ 🎮 " + Constants.BOLD + "\t\t   GAME OVER!" + Constants.RESET + " \t\t   🎮║");
        System.out.println("==============================================");
        System.out.println("║ There is only " + Constants.BOLD + "one player" + Constants.RESET + " left in the game! ║");
        System.out.println("==============================================\n");
    }

    public static void showGameOverNoHumansLeft() {
        System.out.println("\n====================================================");
        System.out.println("║ 🎮 " + Constants.BOLD + "\t\t  GAME OVER!" + Constants.RESET + " \t\t\t 🎮║");
        System.out.println("====================================================");
        System.out.println("║ There are no more " + Constants.BOLD + "human players" + Constants.RESET + " left in the game!║");
        System.out.println("====================================================\n");
    }
}
