package oop.ludgo.projekt.valorization;

import java.util.Random;

/**
 * A base class for the pension companies
 */
public abstract class PensionCompany {

	private static int mSeed = 1000;

	// Coefficients by which money are to be multiplied with aim to valorize
	// them
	private double mFactorExperience;
	private double mFactorManagement;
	private double mFactorEnvironment;
	private double mFactorLuck;

	public PensionCompany(double factorExperience) {
		Random random = new Random(mSeed);
		mSeed += 5;
		// Random: budget can be changed from -0.5% to +0.5% for a single unit
		// of valorization
		mFactorManagement = 0.995 + random.nextDouble() * 0.005;
		mFactorEnvironment = 0.995 + random.nextDouble() * 0.005;
		mFactorLuck = 0.995 + random.nextDouble() * 0.005;
		// Non random: how long is the company on the market
		mFactorExperience = factorExperience;
	}

	/**
	 * Calculate the overall success coefficient
	 */
	protected double getTotalFactor(double factorFund) {
		return (mFactorExperience + mFactorManagement + mFactorEnvironment + mFactorLuck + factorFund) / 5;
	}

}
