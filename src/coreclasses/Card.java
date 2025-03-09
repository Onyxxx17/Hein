package coreclasses;

public class Card {
    public char color;
    public int value;

    // constructor
    public Card(char c, int v) {
        this.color = c;
        this.value = v;
    }

    public int getValue() {
        return value;
    }

    public char getColor() {
        return color;
    }

    public String toString() {
        return "{" + color + ":" + value + "}";
    }

    // tester for card class (from gpt)
    // public static void main(String[] args) {
    //     // Create a new card
    //     Card card = new Card('R', 5);

    //     // Test getters
    //     System.out.println("Card Color: " + card.getColor());  // Expected: R
    //     System.out.println("Card Value: " + card.getValue());   // Expected: 5

    //     // Test toString()
    //     System.out.println("Card Representation: " + card);    // Expected: { R:5 }
    // }

        
}