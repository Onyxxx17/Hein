package game.utils;

/**
 * AsciiArt class provides static methods to display ASCII art for the game.
 * It includes a welcome message and representations of dice faces.
 */
public class AsciiArt {

    // Welcome art for the game
    public static void welcomeArt() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
        }
        System.out.println("""
██████╗  █████╗ ██████╗  █████╗ ██████╗ ███████╗
██╔══██╗██╔══██╗██╔══██╗██╔══██╗██╔══██╗██╔════╝
██████╔╝███████║██████╔╝███████║██║  ██║█████╗   
██╔═══╝ ██╔══██║██╔══██╗██╔══██║██║  ██║██╔══╝   
██║     ██║  ██║██║  ██║██║  ██║██████╔╝███████╗
╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚══════╝
                """);
    }

    // Dice faces
    public static final String[] DICE_FACES = {
        """
        +-------+
        |       |
        |   ●   |
        |       |
        +-------+
        """,
        """
        +-------+
        | ●     |
        |       |
        |     ● |
        +-------+
        """,
        """
        +-------+
        | ●     |
        |   ●   |
        |     ● |
        +-------+
        """, """
        +-------+
        | ●   ● |
        |       |
        | ●   ● |
        +-------+
        """,
        """
        +-------+
        | ●   ● |
        |   ●   |
        | ●   ● |
        +-------+
        """,
        """
        +-------+
        | ●   ● |
        | ●   ● |
        | ●   ● |
        +-------+
        """
    };
}
