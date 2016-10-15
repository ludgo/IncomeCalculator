package oop.ludgo.projekt.taxation;

import java.util.Observer;

import oop.ludgo.projekt.util.Constants;
import oop.ludgo.projekt.util.Utilities;

/**
 * A single taxation class as the subtype of {@link Insurance}
 */
public class HealthInsurance extends Insurance {
	
	// The name of the institution to which this is paid
	private static String NAME_HEALTH_INSTITUTION = "Health insurance office";

	// Insurance for health
	// SK: Zdravotne poistenie
	private static String NAME_INSURANCE_HEALTH = "Health insurance";
	private static double TARIFF_EMPLOYER_HEALTH = 0.1;
	private static double TARIFF_EMPLOYEE_HEALTH = 0.04;
	
	// Deductable item
	// SK: Odpocitatelna polozka
	private static double MIN_DEDUCTABLE_ITEM = 0.0;
	private static double MAX_DEDUCTABLE_ITEM = 380.0;
	// Assessed base
	// SK: Vymeriavaci zaklad
	private static double MIN_ASSESSED_BASE = Constants.MINIMAL_WAGE;
	private static double MAX_ASSESSED_BASE = 4290.0;
	
	public HealthInsurance(Observer o, boolean isEmployer){
		super(o, isEmployer);
	}
	
	/**
	 * Calculate deductable item
	 * @param grossWage A prerequisite gross wage
	 * @param daysInMonth A prerequisite number of days in considered month
	 */
	private double calcDeductableItem(double grossWage, int daysInMonth){
		double deductableItem = (MAX_DEDUCTABLE_ITEM - ((grossWage - MAX_DEDUCTABLE_ITEM) * 2)) / daysInMonth;
		if (deductableItem < MIN_DEDUCTABLE_ITEM) {
			return MIN_DEDUCTABLE_ITEM;
		} else if (deductableItem > MAX_DEDUCTABLE_ITEM) {
			return MAX_DEDUCTABLE_ITEM;
		}
		return deductableItem;
	}
	
	/**
	 * Calculate assessed base
	 * @param grossWage A prerequisite gross wage
	 * @param deductableItem A prerequisite deductable item
	 */
	private double calcAssessedBase(double grossWage, double deductableItem){
		double assessedBase = grossWage - deductableItem;
		if (assessedBase < MIN_ASSESSED_BASE) {
			return MIN_ASSESSED_BASE;
		} else if (assessedBase > MAX_ASSESSED_BASE) {
			return MAX_ASSESSED_BASE;
		}
		return assessedBase;
	}
	
	/**
	 * Calculate insurance for health
	 * @param grossWage A prerequisite assessed base
	 */
	private double calcHealthInsurance(double assessedBase){
		if (isEmployer()){
			return assessedBase * TARIFF_EMPLOYER_HEALTH;
		} else {
			return assessedBase * TARIFF_EMPLOYEE_HEALTH;
		}
	}
	
	/**
	 * Calculate total this taxation
	 * @param grossWage A prerequisite gross wage
	 * @param A number from 1 to 12 corresponding to the month from January to December
	 */
	@Override
	public double calcTotal(double grossWage, int month){
		double deductableItem = calcDeductableItem(
				grossWage, Utilities.daysInMonth(month));
		double assessedBase = calcAssessedBase(
				grossWage, deductableItem);
		
		double healthInsurance = calcHealthInsurance(assessedBase);

		// START output
		String fluxLine = Utilities.buildFlux(
				(isEmployer()) ? Constants.NAME_EMPLOYER : Constants.NAME_EMPLOYEE,
				NAME_HEALTH_INSTITUTION
				);
		String healthLine = Utilities.buildCost(
				NAME_INSURANCE_HEALTH,
				healthInsurance
				);
		informUser(month,
				fluxLine,
				healthLine
				);
		// END output
		
		return healthInsurance;
	}
}
