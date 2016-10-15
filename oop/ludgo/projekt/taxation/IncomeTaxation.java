package oop.ludgo.projekt.taxation;

import java.util.Observer;

import oop.ludgo.projekt.util.Constants;
import oop.ludgo.projekt.util.Utilities;

/**
 * A single taxation class as the subtype of {@link Taxation}
 */
public class IncomeTaxation extends Taxation {

	// The name of the institution to which this is paid
	private static String NAME_TAX_INSTITUTION = "Tax bureau";

	// Income tax
	// SK: Dan z prijmu
	private static String NAME_TAX_INCOME = "Income tax";
	private static double TARIFF_INCOME_LOWER = 0.19;
	private static double TARIFF_INCOME_UPPER = 0.25;
	private static double BREAK_INCOME_WAGE = 2918.53;
	
	// Minimal nontaxable part
	// SK: Nezdanitelna cast zakladu dane
	private static double MIN_NONTAXABLE_PART = 0.0;
	private static double MAX_NONTAXABLE_PART = 316.94;
	private static double BREAK_NONTAXABLE_PART_WAGE = 1650.75;
	private static double CONSTANT_NONTAXABLE_PART = 8755.578;

	public IncomeTaxation(Observer o) {
		super(o);
	}
	
	/**
	 * Calculate minimal nontaxable part
	 * @param incomeBase A prerequisite income base
	 */
	private double calcNontaxablePart(double incomeBase){
		double nontaxablePart;
		if (incomeBase <= BREAK_NONTAXABLE_PART_WAGE) {
			nontaxablePart = MAX_NONTAXABLE_PART;
		} else {
			nontaxablePart = (CONSTANT_NONTAXABLE_PART - (incomeBase / 4.0)) / Constants.NUM_MONTHS;
		}
		if (nontaxablePart < MIN_NONTAXABLE_PART) {
			nontaxablePart = MIN_NONTAXABLE_PART;
		}
		return nontaxablePart;
	}
	
	/**
	 * Calculate taxable income base
	 * @param incomeBase A prerequisite income base
	 * @param nontaxablePart A prerequisite minimal nontaxable part
	 */
	private double calcTaxableIncomeBase(double incomeBase, double nontaxablePart) {
		double taxableIncomeBase = incomeBase - nontaxablePart;
		// Can't be negative (in this system)
		return (taxableIncomeBase > 0.0) ? taxableIncomeBase : 0.0;
	}
	
	/**
	 * Calculate income tax
	 * @param taxableIncomeBase A prerequisite taxable income base
	 */
	private double calcIncomeTax(double taxableIncomeBase){
		double incomeTax = 0.0;
		if (taxableIncomeBase > BREAK_INCOME_WAGE) {
			incomeTax += BREAK_INCOME_WAGE * TARIFF_INCOME_LOWER;
			incomeTax += (taxableIncomeBase - BREAK_INCOME_WAGE) * TARIFF_INCOME_UPPER;
		} else {
			incomeTax += taxableIncomeBase * TARIFF_INCOME_LOWER;
		}
		return incomeTax;
	}
	
	/**
	 * Calculate total this taxation
	 * @param incomeBase A prerequisite income base
	 * @param A number from 1 to 12 corresponding to the month from January to December
	 */
	@Override
	public double calcTotal(double incomeBase, int month){
		double nontaxablePart = calcNontaxablePart(incomeBase);
		double taxableIncomeBase = calcTaxableIncomeBase(incomeBase, nontaxablePart);
		
		double incomeTax = calcIncomeTax(taxableIncomeBase);
		
		// START output
		String fluxLine = Utilities.buildFlux(
				Constants.NAME_EMPLOYEE,
				NAME_TAX_INSTITUTION
				);
		String incomeLine = Utilities.buildCost(
				NAME_TAX_INCOME,
				incomeTax
				);
		informUser(month,
				fluxLine,
				incomeLine
				);
		// END output

		return incomeTax;
	}
}
