package oop.ludgo.projekt.model;

import oop.ludgo.projekt.util.Constants;
import oop.ludgo.projekt.util.Utilities;

/**
 * A model as a part of taxation to be saved for person's future pension income
 */
public class Pension {

	double[] mPension;

	public Pension() {
		mPension = new double[Constants.NUM_MONTHS];
	}

	public double getTotal() {
		return Utilities.sumDoubleArray(mPension);
	}

	/**
	 * Add to the specific month
	 * 
	 * @param amount
	 *            How much to add
	 * @param month
	 *            A number from 1 to 12 corresponding to the month from January
	 *            to December
	 */
	public void addMoney(double amount, int month) {
		if (amount >= 1 && amount <= Constants.NUM_MONTHS) {
			mPension[month - 1] += amount;
		}
	}

	/**
	 * Add money to all months
	 * 
	 * @param amount
	 *            How much to add
	 */
	public void addMoney(double[] amount) {
		if (amount != null && amount.length == Constants.NUM_MONTHS) {
			for (int i = 0; i < amount.length; i++) {
				addMoney(amount[i], i + 1);
			}
		}
	}

	public double[] getMoney() {
		return mPension;
	}

	/**
	 * Create pension as a sum of more ones
	 * 
	 * @param pensions
	 *            Pensions to be merges
	 * @return The final pension
	 */
	public static Pension mergePensions(Pension... pensions) {
		Pension pension = new Pension();
		for (int i = 0; i < pensions.length; i++) {
			pension.addMoney(pensions[i].getMoney());
		}
		return pension;
	}

	/**
	 * Create a part of the pension
	 * 
	 * @param pension
	 *            The full pension
	 * @param fraction
	 *            The fraction of the pension expressed by 0.0 - 1.0 coefficient
	 * @return
	 */
	public static Pension getPensionFraction(Pension pension, double fraction) {
		if (fraction < 0.0 || fraction > 1.0)
			return null;

		Pension newPension = new Pension();
		double[] money = pension.getMoney();
		for (int i = 0; i < money.length; i++) {
			newPension.addMoney(fraction * money[i], i + 1);
		}
		return newPension;
	}

}
