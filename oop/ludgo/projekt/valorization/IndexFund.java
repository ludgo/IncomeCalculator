package oop.ludgo.projekt.valorization;

import java.util.Random;

/**
 * A fund created by the world economy index
 */
public class IndexFund extends Fund {

	private int mSeed = 300;
	private final static double mBreakAmount = 1000000.0;
	private final static double mBreakAmountBonus = 1.1;

	/**
	 * Find out a coefficient of the change of the market
	 */
	public double generateMarketDrift() {
		Random random = new Random(mSeed);
		mSeed += 2;
		// From -3.5% to 7%
		return 0.965 + random.nextDouble() * 0.105;
	}

	/**
	 * Market drift considering both itself and the special managers
	 * intervention
	 * 
	 * @return A market drift coefficient
	 */
	public double marketDriftSpecial(double amount) {
		if (hireSpecialManagers(amount))
			return mBreakAmount * generateMarketDrift();
		return generateMarketDrift();
	}

	/**
	 * Special managers improve market drift in a positive way
	 * 
	 * @param amount
	 *            An amount above which special managers are hired
	 * @return true above some amount
	 */
	private boolean hireSpecialManagers(double amount) {
		return amount >= mBreakAmount;
	}

}
