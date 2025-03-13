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

JDK 8(?) or higher, and able to read simple English to follow the instructions given

Compilation: javac -d tempclasses -cp src src/coreclasses/Main.java
Running:     java -cp tempclasses coreclasses.Main


----------------------------------------------
|                 Game Feature                |
----------------------------------------------

[] Randomized starting player // wanna ask player to roll dice and see who goes first to make it more interesting?
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
[] When a face value of 0 is played, remove all cards from the Parade (except the 0 card itself) and add to player's collection
[] Otherwise, remove cards if the colour matches and value comparison
   --> Has the same colour as the played card
   --> Has a value equal to or lower than the played card

End Game Condition
[] Game proceed to final round when one of the following happens:
    --> No more cards can be drawn from the deck
    --> A player collects all 6 colours
[] Player discard 2 cards from their hand and add to their collection
[] Player with the lowest score wins


----------------------------------------------
|                Contributors                 |
----------------------------------------------

👤 Aung Ye Thant Hein
👤 Choon Zhen Yang
👤 Kiara Kuldeep Desai
👤 Loh Kai Xing
👤 Trisha Pratik Chaudhry









