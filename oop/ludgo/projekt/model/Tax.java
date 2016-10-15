package oop.ludgo.projekt.model;

/**
 * A model of tax paid by {@link Person}
 */
public class Tax {

	private double[] mEmployerInsurance;
	private double[] mEmployeeInsurance;
	private double[] mIncomeTax;

	public Tax(double[] employerInsurance, double[] employeeInsurance, double[] incomeTax) {

		mEmployerInsurance = employerInsurance;
		mEmployeeInsurance = employeeInsurance;
		mIncomeTax = incomeTax;
	}

	/**
	 * Calculate how much money the work cost in a single month
	 * 
	 * @param month A considered month
	 * @return A cost of work besides gross wage
	 */
	public double besidesGrossWage(int month) {
		if (month >= 1 && month <= mEmployerInsurance.length) {
			return mEmployerInsurance[month];
		}
		return 0.0;
	}

	/**
	 * Calculate the rest after applying all tax on the gross wage
	 * 
	 * @param month A considered month
	 * @return A gross wage minus tax
	 */
	public double lostFromGrossWage(int month) {
		if (month >= 1 && month <= mEmployeeInsurance.length && month <= mIncomeTax.length) {
			return mEmployeeInsurance[month] + mIncomeTax[month];
		}
		return 0.0;
	}
}
