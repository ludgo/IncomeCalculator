package oop.ludgo.projekt.valorization;

/**
 * A company with the acronym name CCC as a particular {@link PensionCompany}
 */
public class CompanyCCC extends PensionCompany implements SecondPillar {

	private final static double FACTOR_EXPERIENCE = 1.007;
	
	public final static String CCC_BOND_FUND = "ccc_bond_fund"; // default
	public final static String CCC_INDEX_FUND = "ccc_index_fund";

	private Fund mFund;	

	/**
	 * @param fundType
	 *            A type of the chosen fund to be used within this company
	 */
	public CompanyCCC(String fundType){
		super(FACTOR_EXPERIENCE);
		if (fundType.equals(CCC_INDEX_FUND)) {
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
		if (mFund instanceof IndexFund) {
			marketDrift = ((IndexFund) mFund).generateMarketDrift();
		} else if (mFund instanceof BondFund) {
			marketDrift = ((BondFund) mFund).generateMarketDrift();
		}
		double factor = getTotalFactor(marketDrift);
		return amount * factor;
	}
	
}
