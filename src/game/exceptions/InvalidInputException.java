package game.exceptions;

public class InvalidInputException extends Exception {

    // Default constructor
    // This constructor is used when no message is provided
    public InvalidInputException() {
    }

    // Constructor with a message parameter
    // This constructor is used when a specific message is provided
    public InvalidInputException(String message) {
        super(message);
    }
}
