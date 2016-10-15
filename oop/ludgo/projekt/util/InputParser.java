package oop.ludgo.projekt.util;

import javax.swing.JTextField;

/**
 * A helper class to handle input cases at {@link JTextField}
 */
public class InputParser {

	/**
	 * Get double typed in {@link JTextField}
	 * 
	 * @param field
	 *            A {@link JTextField}
	 * @return A positive double at input
	 * @throws InvalidInputException
	 *             In case of input which is not a positive double
	 */
	public double getInputDouble(JTextField field) throws InvalidInputException {
		try {
			double number = Double.parseDouble(field.getText());
			if (number == 0.0) {
				// If the typed value is zero, throw custom exception
				throw new InvalidInputException(InvalidInputException.MESSAGE_INPUT_ZERO);
			} else if (number < 0.0) {
				// If the typed value is negative, throw custom exception
				throw new InvalidInputException(InvalidInputException.MESSAGE_INPUT_NEGATIVE);
			}
			// If its format is acceptable, return the typed value
			return number;
		} catch (NullPointerException e) {
			// The field is null
			throw new InvalidInputException(e);
		} catch (NumberFormatException e) {
			// String in the field is not double type
			throw new InvalidInputException(e);
		}
	}

	/**
	 * Get integer typed in {@link JTextField}
	 * 
	 * @param field
	 *            A {@link JTextField}
	 * @return A positive integer at input
	 * @throws InvalidInputException
	 *             In case of input which is not a positive integer
	 */
	public int getInputInt(JTextField field) throws InvalidInputException {
		try {
			int number = Integer.parseInt(field.getText());
			if (number == 0) {
				// If the typed value is zero, throw custom exception
				throw new InvalidInputException(InvalidInputException.MESSAGE_INPUT_ZERO);
			} else if (number < 0) {
				// If the typed value is negative, throw custom exception
				throw new InvalidInputException(InvalidInputException.MESSAGE_INPUT_NEGATIVE);
			}
			// If its format is acceptable, return the typed value
			return number;
		} catch (NullPointerException e) {
			// The field is null
			throw new InvalidInputException(e);
		} catch (NumberFormatException e) {
			// String in the field is not integer type
			throw new InvalidInputException(e);
		}
	}
}
