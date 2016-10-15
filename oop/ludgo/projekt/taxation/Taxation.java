package oop.ludgo.projekt.taxation;

import java.util.Observable;
import java.util.Observer;

import oop.ludgo.projekt.util.Utilities;

/**
 * A root class for all taxation types
 * {@link Observable} by {@link CalculatorWindow.MyCustomizedPanel}
 */
public abstract class Taxation extends Observable {
	
	public Taxation(Observer o) {
		this.addObserver(o);
	}
	
	/**
	 * Calculate the taxation value
	 * @param financialBase A base to calculate taxation from
	 * @param month A number from 1 to 12 corresponding to the month from January to December
	 */
	public double calcTotal(double financialBase, int month) {
		// Natural is not to pay tax
		return 0.0;
	}
	
	/**
	 * Use observer to inform user about conducted transaction
	 */
	public void informUser(int month, String... lines) {
		if (lines != null) {
			
			String monthShortcut = Utilities.getMonthShortcut(month);
			if (monthShortcut != null) {
				setChanged();
				notifyObservers(monthShortcut + "\n");
			}
			
			for (int i=0; i < lines.length; i++) {
				if (lines[i] != null
						&& !lines.equals(""))
					setChanged();
					notifyObservers(lines[i] + "\n");
			}
		}
	}
}
