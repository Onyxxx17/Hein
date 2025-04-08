package app;

import game.gameplay.*;
import game.renderer.*;
import java.util.*;
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GameState.welcomeMessage(sc);
        GameMenu menu = new GameMenu(sc);
        menu.launch();
        sc.close();
    }
    
}
