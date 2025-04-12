package app;

import game.gameplay.GameMenu;
import game.renderer.GamePhaseRenderer;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GamePhaseRenderer.welcomeMessage(scanner);
        boolean playAnotherGame;
        do {
            GameMenu menu = new GameMenu(scanner);
            menu.launch();
            playAnotherGame = menu.askForAnotherGame();
        } while (playAnotherGame);
        scanner.close();
    }

}
