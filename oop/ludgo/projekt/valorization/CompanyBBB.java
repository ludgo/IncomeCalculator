package oop.ludgo.projekt.valorization;

/**
 * A company with the acronym name BBB as a particular {@link PensionCompany}
 */
public class CompanyBBB extends PensionCompany implements SecondPillar {

	private final static double FACTOR_EXPERIENCE = 1.008;

	public final static String BBB_BOND_FUND = "bbb_bond_fund"; // default
	public final static String BBB_SHARE_FUND = "bbb_share_fund";

	private Fund mFund;

	/**
	 * @param fundType
	 *            A type of the chosen fund to be used within this company
	 */
	public CompanyBBB(String fundType) {
		super(FACTOR_EXPERIENCE);
		if (fundType.equals(BBB_SHARE_FUND)) {
			mFund = new ShareFund();
		} else {
			mFund = new BondFund();
		}
	}

	/**
	 * Change the money value based on economic and other aspects
	 * 
	 * @param amount
	 *            The money before valorization
	 * @return The money after valorization
	 */
	public double valorizeMoney(double amount) {
		double marketDrift = 1.0;
		// A success of the valorization depends on the type of fund
		if (mFund instanceof ShareFund) {
			marketDrift = ((ShareFund) mFund).generateMarketDrift();
		} else if (mFund instanceof BondFund) {
			marketDrift = ((BondFund) mFund).marketDriftConsideringDay();
		}
		double factor = getTotalFactor(marketDrift);
		return amount * factor;
	}
}
