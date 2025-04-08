package game.setup;

import game.core.*;
import game.exceptions.*;
import game.renderer.GameState;
import java.util.*;

public class PlayerSetup {

    private final Scanner scanner;

    public PlayerSetup(Scanner scanner) {
        this.scanner = scanner;
    }

    public int askForNumberOfPlayers() {
        int playerCount = 0;
        GameState.gameSetUp();

        while (true) {
            try {
                System.out.print("👥 Enter the number of players (2-6): ");
                playerCount = scanner.nextInt();

                if (playerCount < 2 || playerCount > 6) {
                    throw new InvalidPlayerCountException(
                        "❌ Invalid player count! This game requires 2 to 6 players.\n"
                    );
                }

                System.out.println("\n✅ Player count: " + playerCount);
                break;

            } catch (InvalidPlayerCountException e) {
                System.out.println(e.getMessage());

            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.\n");
                scanner.next(); // clear buffer
            }
        }

        return playerCount;
    }

    public List<Player> createPlayers(int numPlayers) {
        List<Player> players = new ArrayList<>();
        List<String> names = new ArrayList<>();
        int humanCount = 0;
        int botIndex = 1;

        GameState.setUpPlayers();

        for (int i = 1; i <= numPlayers; i++) {
            String type = getPlayerType(i);
            boolean isHuman = type.equals("H") || type.equals("HUMAN");

            if (isHuman) {
                handleHumanPlayer(names, players);
                humanCount++;
            } else {
                if (humanCount == 0 && i == numPlayers) {
                    System.out.println("❌ There must be at least one human player!");
                    i--;
                } else {
                    handleComputerPlayer(players, names, botIndex);
                    botIndex++;
                }
            }
        }

        System.out.println("\n🎉 All players have been set up! Let’s start the game! 🎉");
        System.out.println("=".repeat(40));
        return players;
    }

    private String getPlayerType(int playerNumber) {
        while (true) {
            System.out.print("\n🎮 Is Player " + playerNumber + " (H)uman or (C)omputer? ");
            String type = scanner.next().toUpperCase();

            if (type.matches("H|C|HUMAN|COMPUTER")) {
                scanner.nextLine(); // Clear buffer
                return type;
            }
            System.out.println("❌ Invalid choice! Please enter ['H' or 'HUMAN'] or ['C' or 'COMPUTER'].\n");
        }
    }

    private void handleHumanPlayer(List<String> names, List<Player> players) {
        String name;
        while (true) {
            System.out.print("📝 Enter player name: ");
            name = scanner.nextLine().trim();

            if (!isValidName(name)) {
                System.out.println("❌ Name must be at least 3 characters long and contain one letter.\n");
                continue;
            }

            if (checkNameWithBot(name)) {
                System.out.println("❌ Name cannot start with 'bot'. If your name contains 'bot', it must be at least 6 characters long.\n");
                continue;
            }

            if (names.contains(name.toLowerCase())) {
                System.out.println("❌ Name already taken by another player. Please choose a different name.\n");
                continue;
            }

            break;
        }

        names.add(name.toLowerCase());
        players.add(new Human(name));
        System.out.println("✅ " + name + " has joined the game!");
    }

    private void handleComputerPlayer(List<Player> players, List<String> names, int botIndex) {
        String botName = "Bot " + botIndex;
        names.add(botName.toLowerCase());
        players.add(new Computer(botName));
        System.out.println("🤖 " + botName + " has joined the game!");
    }

    public boolean isValidName(String name) {
        if (name.length() < 3) return false;

        for (char c : name.toCharArray()) {
            if (Character.isLetter(c)) return true;
        }

        return false;
    }

    public boolean checkNameWithBot(String name) {
        String lower = name.toLowerCase();
        if (lower.startsWith("bot")) {
            return name.length() <= 5;
        }
        return false;
    }
}
