package game.renderer;

import game.utils.*;
import java.util.Scanner;

public class GamePhaseRenderer {

    public static void displayWelcomeMessage(Scanner scanner) {
        Helper.flush();
        Helper.progressBar();
        Helper.flush();
        AsciiArt.welcomeArt();
        String border = "****************************************";

        int duration = Constants.TYPEWRITE_DURATION;
        System.out.println("\n" + border);
        Helper.typewrite("🎉 WELCOME TO THE PARADE CARD GAME! 🎭", duration);
        System.out.println(border + "\n");

        Helper.typewrite("🎴 Remember Players! The rule is simple.", duration);
        Helper.typewrite("🏆 Score as LOW as possible. Good Luck! 🍀\n", duration);

        System.out.println(border + "\n");

        Helper.pressEnterToContinue(scanner);
        Helper.flush();
    }

    public static void displayGameSetup() {
        Helper.printBox("🎲 WELCOME TO THE GAME SETUP 🎲");
    }

    public static void displaySetUpPlayers() {
        Helper.printBox("🎭 PLAYER SETUP 🎭");
    }

    public static void displayGoodByeMessage() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║                                       ║");
        System.out.println("║     🌟 THANK YOU FOR PLAYING 🌟       ║");
        System.out.println("║             🎉  PARADE  🎉            ║");
        System.out.println("║                                       ║");
        System.out.println("╚═══════════════════════════════════════╝");
    }

    public static void displayLastRoundPhase() {
        Helper.flush();
        Helper.printBox("🚨 Last Round 🚨");
        Helper.sleep(Constants.SLOWDELAY);
    }

    public static void displayFinalPhase() {
        Helper.printBox("🎴 Add 2 Cards to Open Cards");
    }

    public static void displayFlippingPhase() {
        Helper.printBox("🃏 Flipping Cards");
    }

    public static void displayFinalResultPhase() {
        System.out.println("===============================");
        System.out.println("        FINAL RESULTS         ");
        System.out.println("===============================");
    }

    public static void displayMenuOptions() {

        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║          🎮 MAIN MENU 🎮              ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║ 1. Start New Game                     ║");
        System.out.println("║ 2. Learn How to Play                  ║");
        System.out.println("║ 3. Quit                               ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.print("Choose an option (1-3): ");
    }

    public static void displayInstructions() {
        Helper.flush();
        System.out.println("\n══════════════════════════════════════════");
        System.out.println(Constants.BOLD + "🌟✨ HOW TO PLAY PARADE ✨🌟" + Constants.RESET);
        System.out.println("══════════════════════════════════════════\n");

        System.out.println("🎯 " + Constants.BOLD + "GOAL:" + Constants.RESET + " Collect the LOWEST score by playing cards strategically!\n");

        System.out.println("🏁 " + Constants.BOLD + "SETUP:" + Constants.RESET);
        System.out.println("  - Each player starts with " + Constants.BOLD + "5 cards" + Constants.RESET + " 🃏");
        System.out.println("  - " + Constants.BOLD + "6 cards" + Constants.RESET + " form the starting parade 🚶‍♂️🚶‍♀️🚶\n");

        System.out.println("🔄 " + Constants.BOLD + "YOUR TURN:" + Constants.RESET);
        System.out.println("  1️⃣ Play " + Constants.BOLD + "1 card" + Constants.RESET + " to the END of the parade ➡️");
        System.out.println("  2️⃣ Cards might get removed based on rules:");
        System.out.println("     💥 If you play a card with a NUMBER (e.g., " + Constants.BOLD + "5" + Constants.RESET + "):");
        System.out.println("        - The first " + Constants.BOLD + "5 cards" + Constants.RESET + " are SAFE 🔒");
        System.out.println("        - Remove cards AFTER these if they:");
        System.out.println("          • Match your card's " + Constants.BOLD + "COLOR" + Constants.RESET + " 🎨");
        System.out.println("          • Have a value " + Constants.BOLD + "≤ your card's number" + Constants.RESET + " 🔢\n");

        System.out.println("🚨 " + Constants.BOLD + "GAME ENDS WHEN:" + Constants.RESET);
        System.out.println("  - The deck runs out ❌");
        System.out.println("  - Someone collects all " + Constants.BOLD + "6 colors" + Constants.RESET + " 🌈\n");

        System.out.println("🏆 " + Constants.BOLD + "FINAL ROUND:" + Constants.RESET);
        System.out.println("  - Everyone discards " + Constants.BOLD + "2 cards" + Constants.RESET + " to their collection 🗑️");
        System.out.println("  - The player with the LOWEST TOTAL score WINS! 🏅");
        System.out.println("     (Tiebreaker: Fewer cards → Fewer colors)\n");

        System.out.println("══════════════════════════════════════════");
    }
}
