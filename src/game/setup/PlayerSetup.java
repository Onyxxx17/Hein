package game.setup;

import game.core.*;
import game.exceptions.*;
import game.renderer.GamePhaseRenderer;
import java.util.*;

public class PlayerSetup {

    private final Scanner scanner;

    public PlayerSetup(Scanner scanner) {
        this.scanner = scanner;
    }

    public int askForNumberOfPlayers() {
        int playerCount = 0;
        GamePhaseRenderer.showGameSetup();

        while (true) {
            try {
                System.out.print("👥 Enter the number of players (2-6): ");
                playerCount = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer

                if (playerCount < 2 || playerCount > 6) {
                    throw new InvalidInputException(
                            "❌ Invalid player count! This game requires 2 to 6 players.\n"
                    );
                }

                System.out.println("\n✅ Player count: " + playerCount);
                break;

            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());

            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a valid number.\n");
                scanner.next();
            }
        }

        return playerCount;
    }

    public List<Player> createPlayers(int numPlayers) {
        List<Player> players = new ArrayList<>();
        Set<String> names = new HashSet<>();
        int humanCount = 0;
        int botIndex = 1;

        GamePhaseRenderer.displaySetUpPlayers();

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
            try {
                System.out.print("🎮 Is Player " + playerNumber + " (H)uman or (C)omputer? ");
                String input = scanner.nextLine().trim().toUpperCase();

                if (input.matches("H|C|HUMAN|COMPUTER")) {
                    return input;
                } else {
                    throw new InvalidInputException("Invalid choice! Please enter ['H' or 'HUMAN'] or ['C' or 'COMPUTER'].");
                }
            } catch (InvalidInputException e) {
                System.out.println("❌ " + e.getMessage() + "\n");
            }
        }
    }

    private void handleHumanPlayer(Set<String> names, List<Player> players) {
        String name;
        while (true) {
            try {
                System.out.print("📝 Enter player name: ");
                name = scanner.nextLine().trim();

                validateName(name, names); // Validate the name (throws InvalidNameException if invalid)

                break; // Exit loop if no exception is thrown
            } catch (InvalidNameException e) {
                System.out.println("❌ " + e.getMessage() + "\n"); // Print the error message from the exception
            }
        }

        names.add(name.toLowerCase());
        players.add(new Human(name));
        System.out.println("✅ " + name + " has joined the game!\n");
    }

    private void handleComputerPlayer(List<Player> players, Set<String> names, int botIndex) {
        String botName = "Bot " + botIndex;
        names.add(botName.toLowerCase());
        players.add(new Computer(botName));
        System.out.println("🤖 " + botName + " has joined the game!\n");
    }

    public boolean isValidLength(String name) {
        return name.length() >= 3 && name.length() <= 10;
    }

    public boolean containLetter(String name) {
        for (char c : name.toCharArray()) {
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkNameWithBot(String name) {
        String lower = name.toLowerCase();
        if (lower.startsWith("bot")) {
            return name.length() < 6;
        }
        return false;
    }

    private void validateName(String name, Set<String> names) throws InvalidNameException {
        if (!isValidLength(name)) {
            throw new InvalidNameException("Name must be (3-10) characters long.");
        }

        if (!containLetter(name)) {
            throw new InvalidNameException("Name must contain at least one letter.");
        }

        if (checkNameWithBot(name)) {
            throw new InvalidNameException("Names starting with 'bot' must be at least 6 characters long.");
        }

        if (names.contains(name.toLowerCase())) {
            throw new InvalidNameException("Name already taken by another player. Please choose a different name.");
        }
    }
}
