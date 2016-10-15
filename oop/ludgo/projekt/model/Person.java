package oop.ludgo.projekt.model;

import oop.ludgo.projekt.util.Constants;
import oop.ludgo.projekt.util.Utilities;

/**
 * A model of person (employee)
 */
public class Person {
	
	private double[] mGrossWages;
	private int mNumChildren;
	private double[] mNetWages;
	
	public Person(double[] grossWages, int numChildren) {
		
		mGrossWages = new double[Constants.NUM_MONTHS];
		for (int i=0; i < grossWages.length; i++) {
			if (grossWages[i] > 0.0) mGrossWages[i] = grossWages[i];
		}
		mNumChildren = (numChildren > 0) ? numChildren : 0;
	}
	
	public double[] getGrossWages(){
		return mGrossWages;
	}

	/**
	 * Knowing gross wages, tax and children bonus, net wages can be calculates
	 * @param tax A tax which has been paid
	 */
	public void calcNetWages(Tax tax) {
		mNetWages = new double[Constants.NUM_MONTHS];
		
		if (tax != null) {
			double childrenBonus = State.demandChildrenBonus(mNumChildren);
			
			for (int i=0; i < mGrossWages.length; i++) {
				// Prerequisites are gross wage, tax, children bonus
				mNetWages[i] = mGrossWages[i] - tax.lostFromGrossWage(i) + childrenBonus;
			}
		}
	}
	
	/**
	 * Knowing gross wages and tax and children bonus, work cost can be estimated
	 * @param tax A tax which has been paid
	 * @return A double array of work costs by months
	 */
	private double[] calcWorkCosts(Tax tax){
		double[] costOfWork = new double[Constants.NUM_MONTHS];

		if (tax != null) {
			for (int i=0; i < mGrossWages.length; i++) {
				// Prerequisites are gross wage, tax
				costOfWork[i] = mGrossWages[i] + tax.besidesGrossWage(i);
			}
		}
		return costOfWork;
	}
	
	public double getWorkCostTotal(Tax tax) {
		return Utilities.sumDoubleArray(calcWorkCosts(tax));
	}
	
	public double getNetWagesTotal() {
		return Utilities.sumDoubleArray(mNetWages);
	}
	
	
}
