package app;

import game.gameplay.*;
import game.renderer.*;
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
