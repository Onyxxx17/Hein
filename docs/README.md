CS102 Programming Fundamentals II: Parade Game (G3T2)
 
 ----------------------------------------------
 |                  Overview                   |
 ----------------------------------------------
 
 Parade game is a strategic game that allows 2-6 players taking turns to play their cards into the parade while minimizing their score.
 Cards are added to and removed from the parade based on specific rules (refer to simplified rules below), and game ends when certain conditions are met.
 Player with the lowest score wins.
 
 
 ----------------------------------------------
 |            Installation & Setup             |
 ----------------------------------------------
 
 JDK 15 or higher, and able to read simple English to follow the instructions given
 
 Compilation: javac -d tempclasses -cp src src/app/Main.java
 Running:     java -cp tempclasses app.Main
 
 
 ----------------------------------------------
 |                 Game Feature                |
 ----------------------------------------------
 
 [] Players each roll a dice to determine the play sequence, highest value goes first
 [] Players each roll a dice to determine the play sequence, highest value goes first, followed by next player in line (not player who rolled the second highest!)
 [] Automatic deck shuffling and dealing
 [] Players take turns selecting and playing cards
 [] Parade mechanics for card removal based on fixed rules
 [] Final round when end game condition is trigerred
 [] Scoring system determining winner with the lowest score
 
 
 ----------------------------------------------
 |                 How To Play                 |
 ----------------------------------------------
 
 [1] All players start with 5 cards from the deck
 [2] 6 faced-up cards form the Parade
 [3] On a turn, player chooses one card from hand and adds it to the end of the Parade
 [4] Players draw new cards (except in the final round)
 [5] The game ends when no more cards can be drawn or when a player collects all 6 color
 [6] The player with the lowest score wins!
 
 
 ----------------------------------------------
 |            Game Rules (Simplified)          |
 ----------------------------------------------
 
 Playing a Card
 [] When a face value of 0 is played, all cards enter removal mode (take all same color cards and face value of 0 card)
 [] If the played card has a value v, then the first v cards in the Parade (from the right) are safe and cannot be removed
    --> The removal check starts from the (v+1)th card onward
 [] Then, remove cards if the colour matches and value comparison
    --> Has the same colour as the played card
    --> Has a value equal to or lower than the played card
 
 End Game Condition
 [] Game proceed to final round when one of the following happens:
     --> No more cards can be drawn from the deck
     --> A player collects all 6 colours
 [] Player discard 2 cards from their hand and add to their collection
 [] Player with the lowest score wins
 
 
 ----------------------------------------------
 |              Program Structure              |
 ----------------------------------------------
 
 The game is organized and structured into different classes, each handling a specific aspect of the game logic.
 
 [src/app]
 1. Main
 Main entry point for the game
 
 
 [src/game/core]
 1. Card
 Represents a single playing card, stores the colour and the value of the card
 
 2. Deck
 Manages the shuffling and distribution of the cards, provides the drawing pile for players 
 
 3. Parade
 Represents the cards on the table, handles card placement and removal 
 
 4. Player
 Manages players' hand and collections of cards, score and calculate player's total score based on the open cards
 
 5. Computer
 Simulates an AI player that acts as a human player and handles the computer's player card selection during gameplay 
 
 6. Human
 Representing human player, prompting users for input, allowing them to interact along the game by selecting cards to play
 
 
 [src/game/exceptions]
 1. InvalidPlayerCountException
 Customized exception to handle invalid player count input, ensuring user input is between 2 to 6
 
 2. InvalidTypeException
 Customized exception to handle invalid player type input, when a player is neither a human or computer
 
 
 [src/game/gameplay]
 1. GameManager
 Handles game logic, flip cards based on majority and determine winners

 2. GameControl
 Manages the overall game flow, including initialization and turns, conclude the game by coordinating player actions, deck management and parade updates

 3. PlayerComparator
 Compares players based on score, and if they have the same score, total cards and number of colors collected

 4. Podium
 Display final standings of players with rankings and score at the end of the game

 5. RollDice
 Prompt players to roll dice to decide who goes first

 
 [src/game/utils]
 1. GameUtils
 Includes functions to display welcome message, validate user input, and provides utility for game initialization
 
 2. Helper
 Provides utility methods for game, for instance, clearing the console, displaying a loading spinner, introducing delays between players' turns, etc.

 3. AsciiArt
 Displays welcome and banner ASCII art for the game, adding visual appeal during startup
 
 
 ----------------------------------------------
 |                Contributors                 |
 ----------------------------------------------
 
 👤 Aung Ye Thant Hein
 👤 Choon Zhen Yang
 👤 Kiara Kuldeep Desai
 👤 Loh Kai Xing
 👤 Trisha Pratik Chaudhry
