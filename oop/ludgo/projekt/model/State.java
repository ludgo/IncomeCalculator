package oop.ludgo.projekt.model;

import java.util.Observer;

import oop.ludgo.projekt.model.Person;
import oop.ludgo.projekt.model.Tax;
import oop.ludgo.projekt.taxation.HealthInsurance;
import oop.ludgo.projekt.taxation.IncomeTaxation;
import oop.ludgo.projekt.taxation.SocialInsurance;
import oop.ludgo.projekt.util.Constants;

/**
 * A model representing a country to depict state-citizen relationship of this
 * with {@link Person}
 */
public class State {

	// Here the pension will be accumulated
	private Pension mPension = new Pension();
	private Observer mObserver;

	// Economic constant, bonus from state to parent per one child
	private static double CHILD_BONUS_UNIT = 21.41;

	public State(Observer o) {
		mObserver = o;
	}
	
	/**
	 * An employee can demand bonus on every own child
	 * 
	 * @param numChildren
	 *            A number of children of the demanding person
	 * @return A total bonus
	 */
	public static double demandChildrenBonus(int numChildren) {
		if (numChildren <= 0)
			return 0.0;
		return numChildren * CHILD_BONUS_UNIT;
	}

	/**
	 * An employer pays insurance for employee as one of their responsibilities
	 * to the state
	 * 
	 * @param grossWages
	 *            A array of the gross wages by months
	 * @return A array of the insurance by months
	 */
	public Tax payExpenses(Person person) {
		if (person == null)
			return null;

		double[] grossWages = person.getGrossWages();

		double[] employerInsurance = payInsuranceEmployer(grossWages);
		double[] employeeInsurance = payInsuranceEmployee(grossWages);
		double[] incomeTax = payIncomeTax(grossWages, employeeInsurance);

		return new Tax(employerInsurance, employeeInsurance, incomeTax);
	}

	/**
	 * Both employee and employer pay tax as one of their responsibilities to
	 * the state
	 * 
	 * @param grossWages
	 *            A array of the gross wages by months
	 * @return A array of the total taxes by months
	 */
	private double[] payInsuranceEmployer(double[] grossWages) {
		double[] employerInsurance = new double[Constants.NUM_MONTHS];

		// Create insurance instances in employer mode
		HealthInsurance employerHI = new HealthInsurance(mObserver, true);
		SocialInsurance employerSI = new SocialInsurance(mObserver, true);

		for (int i = 0; i < grossWages.length; i++) {
			employerInsurance[i] = employerHI.calcTotal(grossWages[i], i + 1)
					+ employerSI.calcTotal(grossWages[i], i + 1);
		}
		// The part of the social insurance goes to pension savings
		mPension = Pension.mergePensions(mPension, employerSI.getPension());
		return employerInsurance;
	}

	/**
	 * An employee pays insurance as one of their responsibilities to the state
	 * 
	 * @param grossWages
	 *            A array of the gross wages by months
	 * @return A array of the insurance by months
	 */
	private double[] payInsuranceEmployee(double[] grossWages) {
		double[] employeeInsurance = new double[Constants.NUM_MONTHS];

		// Create insurance instances in employee mode
		HealthInsurance employeeHI = new HealthInsurance(mObserver, false);
		SocialInsurance employeeSI = new SocialInsurance(mObserver, false);

		for (int i = 0; i < grossWages.length; i++) {
			employeeInsurance[i] = employeeHI.calcTotal(grossWages[i], i + 1)
					+ employeeSI.calcTotal(grossWages[i], i + 1);
		}
		// The part of the social insurance goes to pension savings
		mPension = Pension.mergePensions(mPension, employeeSI.getPension());
		return employeeInsurance;
	}

	/**
	 * An employee pays income tax as one of their responsibilities to the state
	 * 
	 * @param grossWages
	 *            A array of the gross wages by months
	 * @param employeeInsurance
	 *            A array of the employee insurance by months
	 * @return A array of the income taxes by months
	 */
	private double[] payIncomeTax(double[] grossWages, double[] employeeInsurance) {
		double[] incomeTax = new double[Constants.NUM_MONTHS];

		IncomeTaxation incomeTaxation = new IncomeTaxation(mObserver);

		for (int i = 0; i < grossWages.length; i++) {
			incomeTax[i] = incomeTaxation.calcTotal(grossWages[i] - employeeInsurance[i], i + 1);
		}
		return incomeTax;
	}

	/**
	 * Get accumulated pension
	 * 
	 * @return {@link Pension}
	 */
	public Pension getPension() {
		return mPension;
	}
}
