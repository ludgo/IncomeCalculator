package oop.ludgo.projekt.valorization;

/**
 * A company with the acronym name AAA as a particular {@link PensionCompany}
 */
public class CompanyAAA extends PensionCompany implements SecondPillar {

	private final static double FACTOR_EXPERIENCE = 1.014;

	public final static String AAA_BOND_FUND = "aaa_bond_fund"; // default
	public final static String AAA_SHARE_FUND = "aaa_share_fund";
	public final static String AAA_INDEX_FUND = "aaa_index_fund";

	private Fund mFund;

	/**
	 * @param fundType
	 *            A type of the chosen fund to be used within this company
	 */
	public CompanyAAA(String fundType) {
		super(FACTOR_EXPERIENCE);
		if (fundType.equals(AAA_SHARE_FUND)) {
			mFund = new ShareFund();
		} else if (fundType.equals(AAA_INDEX_FUND)) {
			mFund = new IndexFund();
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
		} else if (mFund instanceof IndexFund) {
			marketDrift = ((IndexFund) mFund).marketDriftSpecial(amount);
		} else if (mFund instanceof BondFund) {
			marketDrift = ((BondFund) mFund).marketDriftConsideringDay();
		}
		double factor = getTotalFactor(marketDrift);
		return amount * factor;
	}

}
