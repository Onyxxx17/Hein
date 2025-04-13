package game.exceptions;

/**
 * Exception thrown when an invalid input is provided by the user.
 * This can occur if the input is not in the expected format or range.
 */
public class InvalidInputException extends Exception {

    // Default constructor
    public InvalidInputException() {
    }

    // Constructor with a message parameter
    public InvalidInputException(String message) {
        super(message);
    }
}
