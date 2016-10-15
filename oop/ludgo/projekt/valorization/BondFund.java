package oop.ludgo.projekt.valorization;

import java.util.Calendar;
import java.util.Random;

/**
 * A fund created by bonds (generated mainly by country bonds)
 */
public class BondFund extends Fund {

	private int mSeed = 100;
	private static double mSundayDisadvantage = 0.9975;

	/**
	 * Find out a coefficient of the change of the market
	 */
	public double generateMarketDrift() {
		Random random = new Random(mSeed);
		mSeed += 2;
		// From 0% to 0.5%
		return 1.0 + random.nextDouble() * 0.005;
	}

	/**
	 * Market drift considering both itself and the day in week
	 * 
	 * @return A market drift coefficient
	 */
	public double marketDriftConsideringDay() {
		return (isSunday()) ? mSundayDisadvantage * generateMarketDrift() : generateMarketDrift();
	}

	/**
	 * Determine if the current day is Sunday
	 * 
	 * @return true if it is Sunday
	 */
	private boolean isSunday() {
		Calendar startDate = Calendar.getInstance();
		startDate.set(2016, Calendar.MAY, 01);
		return startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

}
