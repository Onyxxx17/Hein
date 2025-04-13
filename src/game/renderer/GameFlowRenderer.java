package game.renderer;

import game.core.*;
import game.utils.Constants;
import game.utils.Helper;
import java.util.*;

/**
 * GameFlowRenderer is responsible for rendering various game flow messages and
 * animations during the game. It includes methods for displaying game start
 * messages, turn headers, deck sizes, card drawing actions, and game over
 * messages.
 */
public class GameFlowRenderer {

    // ============================ Game Start & Initialization ============================
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

    // ============================ Decks & Cards ============================
    public static void showDeckSize(Deck deck) {
        System.out.println("Current deck size: " + deck.size());
    }

    public static void showCardDraw(Player player) {
        System.out.println("\n" + player.getName() + " draws one card from the deck.");
    }

    public static void showDrawCardFromDeck(Player player) {
        System.out.println("\n" + player.getName() + " draws one card from the deck.");
    }

    public static void showDeckEmpty() {
        System.out.println();
        Helper.sleep(Constants.NORMAL_DELAY_TIME);
        Helper.printBox("‼️ No more cards are left in the deck ‼️\n" + playLastRound());
    }

    public static void showAllColorsCollected(Player player) {
        System.out.println();
        Helper.sleep(Constants.NORMAL_DELAY_TIME);
        Helper.printBox("‼️ " + player.getName()
                + " has collected all 6 color cards ‼️\n" + playLastRound());
    }

    private static String playLastRound() {
        return ("‼️ Every player plays one last round ‼️");
    }

    // ============================ Turn Flow ============================
    public static void showTurnHeader(String playerName) {
        Helper.printBox("🌟 " + playerName + "'s Turn");
    }

    /**
     * Prints out the current state of the game before a player's turn. It
     * displays the size of the deck, the open cards of all players, the current
     * Parade, and then prints a box containing the player name and "Turn" to
     * indicate the start of the player's turn
     */
    public static void showPlayerRound(Player player, List<Player> players, Parade parade, Deck deck) {
        showDeckSize(deck);
        showOpenCards(players);
        ParadeRenderer.showParade(parade);
        showTurnHeader(player.getName());
    }

    public static void showOpenCards(List<Player> players) {
        for (Player player : players) {
            PlayerRenderer.showOpenCards(player);
        }
    }

    // ============================ Cards Flipping ============================
    /**
     * Displays the flipped cards for each player in the game.
     *
     * @param flippedCards A map of players to their flipped cards.
     * @param players The list of players in the game.
     */
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

    /**
     * Displays the flipped cards for a specific player.
     *
     * @param player The player whose flipped cards are to be displayed.
     * @param flippedCards The list of flipped cards for the player.
     */
    public static void showMaxPlayersForColor(String color, List<Player> maxPlayers) {
        String colorCode = Helper.getColorCode(color);
        if (maxPlayers.isEmpty()) {
            showNoMaxPlayersForColor(color);
            return;
        }
        showMaxPlayersForColorList(color, maxPlayers, colorCode);
    }

    public static void showNoMaxPlayersForColor(String color) {
        System.out.println("🎭 Player(s) with most " + Helper.getColorCode(color)
                + color + Constants.RESET + " cards: "
                + Constants.BOLD + "None" + Constants.RESET + "\n");
    }

    public static void showMaxPlayersForColorList(String color, List<Player> maxPlayers, String colorCode) {
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

    public static void show2PlayerRules() {
        System.out.println("The difference between the two players is not enough to flip cards. It needs to be at least 2.");
    }

    // ============================ Game Over ============================
    /**
     * Displays a message indicating a tie between players and lists the players
     * involved in the tie. It then explains the tiebreaker rules and shows the
     * number of total cards and colors collected by each player.
     *
     * @param potentialWinners List of players involved in the tie.
     */
    public static void showTieBreaker(List<Player> potentialWinners) {
        Helper.sleep(Constants.NORMAL_DELAY_TIME);
        System.out.println("⚔️  A tie has been detected between " + potentialWinners.size() + " players!");
        Helper.sleep(Constants.NORMAL_DELAY_TIME);

        System.out.println("🔝 Players with the highest score: ");
        for (Player p : potentialWinners) {
            Helper.sleep(300);
            System.out.println(" - " + p.getName());
        }

        Helper.sleep(Constants.NORMAL_DELAY_TIME);
        System.out.println("\n🏆 To break the tie and determine the winner, we will consider:\n");
        Helper.sleep(Constants.NORMAL_DELAY_TIME);

        System.out.println("1️⃣ Fewest number of total cards collected.");
        Helper.sleep(Constants.NORMAL_DELAY_TIME);

        System.out.println("2️⃣ Fewest number of different colors collected.");
        Helper.sleep(Constants.NORMAL_DELAY_TIME);

        System.out.println("3️⃣ Final dice roll if still tied!");
        Helper.sleep(Constants.NORMAL_DELAY_TIME);

        System.out.println("\nLet the tiebreaker begin!");
        Helper.sleep(Constants.NORMAL_DELAY_TIME);
        System.out.println("\n📊 Analyzing player collections...\n");
        Helper.sleep(Constants.NORMAL_DELAY_TIME);

        for (Player p : potentialWinners) {
            System.out.println("🔹 " + p.getName() + " collected:");
            Helper.sleep(200);
            System.out.print("   - Total cards: ");
            Helper.typewrite(String.valueOf(p.getTotalOpenCards()), Constants.TYPEWRITE_DURATION);

            System.out.print("\n   - Total colors: ");
            Helper.typewrite(String.valueOf(p.getOpenCards().size()), Constants.TYPEWRITE_DURATION);
            System.out.println();
            Helper.sleep(400);
        }

        Helper.sleep(Constants.NORMAL_DELAY_TIME);
    }

    public static void showGameOver() {
        System.out.println("Game Over!");
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

    // ============================ Quit Option ============================
    public static void showQuitOption() {
        System.out.print("\n🛑 Type anything to play your round or 'quit' to exit> ");
    }

    public static void confirmQuit() {
        System.out.print("\nAre you sure you want to quit? (y/n): ");
    }
}
