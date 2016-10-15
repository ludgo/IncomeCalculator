package oop.ludgo.projekt.valorization;

import java.util.Random;

/**
 * A fund created by shares (generated mainly by shareholders)
 */
public class ShareFund extends Fund {

	private int mSeed = 200;

	/**
	 * Find out a coefficient of the change of the market
	 */
	public double generateMarketDrift() {
		Random random = new Random(mSeed);
		mSeed += 2;
		// From -2% to 4%
		return 0.98 + random.nextDouble() * 0.06;
	}

}
