package game.renderer;

import game.utils.AsciiArt;
import game.utils.Constants;
import game.utils.Helper;
import java.util.Scanner;

public class GameState {

    public static void welcomeMessage(Scanner scanner) {
        Helper.flush();
        Helper.progressBar();
        Helper.flush();
        AsciiArt.welcomeArt();
        String border = "****************************************";

        int duration = Constants.DELAY_DURATION;
        System.out.println("\n" + border);
        Helper.typewrite("🎉 WELCOME TO THE PARADE CARD GAME! 🎭", duration);
        System.out.println(border + "\n");

        Helper.typewrite("🎴 Remember Players! The rule is simple.", duration);
        Helper.typewrite("🏆 Score as LOW as possible. Good Luck! 🍀\n", duration);

        System.out.println(border + "\n");

        Helper.pressEnterToContinue(scanner);
        Helper.flush();
    }

    public static void gameSetUp(){
        Helper.printBox("🎲 WELCOME TO THE GAME SETUP 🎲");
    }

    public static void setUpPlayers(){
        Helper.printBox("🎭 PLAYER SETUP 🎭");
    }

    public static void goodbyeMessage() {
        System.out.println("\n\n");
        System.out.println("   ____                       _       ");
        System.out.println("  |  _ \\ __ _ _ __ __ _  __ _| | ___  ");
        System.out.println("  | |_) / _` | '__/ _` |/ _` | |/ _ \\ ");
        System.out.println("  |  __/ (_| | | | (_| | (_| | |  __/ ");
        System.out.println("  |_|   \\__,_|_|  \\__,_|\\__,_|_|\\___| ");
        System.out.println("\n");
        System.out.println("        🌟 THANK YOU FOR PLAYING 🌟");
        System.out.println("              🎉  PARADE  🎉");
        System.out.println("\n\n");
    }

    
}
