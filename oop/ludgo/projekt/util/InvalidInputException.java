package oop.ludgo.projekt.util;

/**
 * A custom exception to handle non-acceptable input cases
 */
public class InvalidInputException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public static final String MESSAGE_INPUT_NEGATIVE = "Negative number at input.";
	public static final String MESSAGE_INPUT_ZERO = "Zero at input.";

	public InvalidInputException() {
		super();
	}

	public InvalidInputException(String message) {
		super(message);
	}

	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidInputException(Throwable cause) {
		super(cause);
	}

}
