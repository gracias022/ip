package miffy.exception;

/**
 * Represents an exception specific to the Miffy application.
 * <p>
 * This exception is thrown when user input is invalid or when
 * an error occurs during task-related command execution or storage operations.
 */
public class MiffyException extends Exception {

    /**
     * Constructs a {@code MiffyException} with a specified error message.
     *
     * @param message Error message describing the cause of the exception.
     */
    public MiffyException(String message) {
        super(message);
    }
}
