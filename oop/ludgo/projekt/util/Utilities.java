package oop.ludgo.projekt.util;

/**
 * A helper class contained with helper methods
 */
public class Utilities {

	/**
	 * A chooser to determine how many days in particular month
	 * @param month A number from 1 to 12 corresponding to the month from January to December
	 * @return A number of days in month, default -1
	 */
	public static int daysInMonth(int month){
		switch(month){
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			case 2:
				return 28;
			default:
				return -1;
		}
	}
	
	/**
	 * A chooser to determine the name of the particular month
	 * @param month A number from 1 to 12 corresponding to the month from January to December
	 * @return A name of the month, default null
	 */
	public static String getMonthName(int month) {
		if (month >= 1 && month <= Constants.NUM_MONTHS) {
			return Constants.MONTH_NAMES[month - 1];
		}
		return null;
	}
	
	/**
	 * A chooser to determine the shortcut of the name of the particular month
	 * @param month A number from 1 to 12 corresponding to the month from January to December
	 * @return A shortcut of the name of the month, default null
	 */
	public static String getMonthShortcut(int month) {
		if (month >= 1 && month <= Constants.NUM_MONTHS) {
			return Constants.MONTH_SHORTCUTS[month - 1];
		}
		return null;
	}
	
	/**
	 * Sum the array
	 * @return A double sum of the array elements
	 */
	public static double sumDoubleArray(double[] array) {
		double sum = 0.0;
		if (array != null) {
			for (int i=0; i < array.length; i++) {
				sum += array[i];
			}
		}
		return sum;
	}
	
	/**
	 * Put a " -> " String between all adjacent pairs
	 * @return A string describing a transaction flux as to be displayed
	 */
	public static String buildFlux(String... members) {
		String flux = "";
		if (members != null) {
			for (int i=0; i < members.length; i++) {
				if (i != 0) {
					flux += " -> "; 
				}
				flux += members[i];
			}
		}
		return flux;
	}
	
	/**
	 * Create a specified format, for example "Fruits $ 15.99"
	 * @return A string describing a transaction cost as to be displayed
	 */
	public static String buildCost(String name, double cost) {
		return String.format("%s$ %.2f",
				(name == null || name.equals("")) ? "" : (name + " "),
				(cost > 0.0) ? cost : 0.0);
	}
}
