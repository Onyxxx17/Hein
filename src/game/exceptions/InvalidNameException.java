package game.exceptions;

/**
 * Exception thrown when a name is invalid.
 * This can occur if the name is empty or contains invalid characters.
 */
public class InvalidNameException extends Exception {
    public InvalidNameException(String message) {
        super(message);
    }
}
