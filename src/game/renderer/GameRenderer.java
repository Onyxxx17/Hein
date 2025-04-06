package game.renderer;

import game.core.*;
import game.utils.AsciiArt;
import game.utils.Constants;
import game.utils.Helper;
import java.util.*;

public class GameRenderer {// implements GameRendererInterface{
    private static final int DELAY_DURATION = 45;
    public GameRenderer() {};
    // Static utility method: Called once, independent of GameRenderer instance
    public static void welcomeMessage(Scanner scanner) {
        Helper.flush();
        Helper.progressBar();
        Helper.flush();
        AsciiArt.welcomeArt();
        String border = "****************************************";

        System.out.println("\n" + border);
        Helper.typewrite("🎉 WELCOME TO THE PARADE CARD GAME! 🎭", DELAY_DURATION);
        System.out.println(border + "\n");

        Helper.typewrite("🎴 Remember Players! The rule is simple.", DELAY_DURATION);
        Helper.typewrite("🏆 Score as LOW as possible. Good Luck! 🍀\n", DELAY_DURATION);

        System.out.println(border + "\n");

        Helper.pressEnterToContinue(scanner);
        Helper.flush();
    }

    public static void showFlippedCards(Map<Player, ArrayList<Card>> flippedCards, ArrayList<Player> players) {
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
        System.out.println("🎭 Player(s) with most " + Helper.getColorCode(color) + color + " cards: \u001B[0mNone\n");
    }

    public static void displayMaxPlayersForColorList(String color, List<Player> maxPlayers, String colorCode) {
        System.out.print("🎉 Player(s) that will flip " + colorCode + color + " cards: \u001B[0m");
        for (int i = 0; i < maxPlayers.size(); i++) {
            System.out.print("\u001B[1m" + maxPlayers.get(i).getName() + "\u001B[0m");
            if (i != maxPlayers.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println(" 🎉\n");
        Helper.sleep(1200);
    }

    public static void logMaxCardsForColor(String color, int max) {
        System.out.println("Max cards for " + color + " is " + max);
    }

    public static void logNoFlippingDueToTie(String color) {
        System.out.println("All players have the same number of cards. No cards flipped for " + color);
    }
    public static void showTieBreaker(ArrayList<Player> potentialWinners) {
        System.out.println("There is a tie between " + potentialWinners.size() + " players");
        System.out.println("Players with the highest score : " + potentialWinners.toString());
        System.out.println("\nWinner will be decided on two conditions:\n");
        System.out.println("1. Fewest number of cards collected");
        System.out.println("2. Fewest number of colors collected\n");

        for (Player p : potentialWinners) {
            System.out.println(p.getName() + " collected");
            System.out.println("Total cards : " + p.getTotalOpenCards() + " cards");
            System.out.println("Total colors : " + p.getOpenCards().size() + " colors\n");
        }
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
        System.out.print("\n✨ Initializing Parade");
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

    public static void showFinalPhase() {
        Helper.printBox("🎴 Add 2 Cards to Open Cards");
    }

    public static void showFlippingPhase() {
        Helper.printBox("🃏 Flipping Cards");
    }

    public static void showDrawCardFromDeck(Player player) {
        System.out.println("\n" + player.getName() + " draws one card from the deck.");
    }

    public static void showDeckEmpty() {
        System.out.println("‼️ No more cards are left in the deck. Game Over ‼️");
        playLastRound();
    }

    public static void showAllColorsCollected(Player player) {
        System.out.println("\n‼️" + player.getName() +
                " has collected all 6 color cards. Game Over ‼️\n");
        playLastRound();
    }

    private static void playLastRound() {
        System.out.println("‼️ Every player plays one last round ‼️");
    }

    public static void displayOpenCards(ArrayList<Player> players) {
        for (Player player : players) {
            PlayerRenderer.showOpenCards(player);
        }
    }
}
