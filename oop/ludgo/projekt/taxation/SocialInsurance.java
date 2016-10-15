package oop.ludgo.projekt.taxation;

import java.util.Observer;

import oop.ludgo.projekt.model.Pension;
import oop.ludgo.projekt.util.Constants;
import oop.ludgo.projekt.util.Utilities;

/**
 * A single taxation class as the subtype of {@link Insurance}
 */
public class SocialInsurance extends Insurance {

	// Here the pension will be accumulated
	private Pension mPension = new Pension();

	// The name of the institution to which this is paid
	private static String NAME_SOCIAL_INSTITUTION = "Social insurance office";

	// Insurance for illness
	// SK: Nemocenske poistenie
	private static String NAME_INSURANCE_ILLNESS = "Illness insurance";
	private static double TARIFF_EMPLOYER_ILLNESS = 0.014;
	private static double TARIFF_EMPLOYEE_ILLNESS = 0.014;
	// Insurance for pension
	// SK: Starobne poistenie
	private static String NAME_INSURANCE_PENSION = "Pension insurance";
	private static double TARIFF_EMPLOYER_PENSION = 0.14;
	private static double TARIFF_EMPLOYEE_PENSION = 0.04;
	// Insurance for invalidity
	// SK: Invalidne poistenie
	private static String NAME_INSURANCE_INVALIDITY = "Invalidity insurance";
	private static double TARIFF_EMPLOYER_INVALIDITY = 0.03;
	private static double TARIFF_EMPLOYEE_INVALIDITY = 0.03;
	// Insurance for unemployment
	// SK: Poistenie v nezamestnanosti
	private static String NAME_INSURANCE_UNEMPLOYMENT = "Unemployment insurance";
	private static double TARIFF_EMPLOYER_UNEMPLOYMENT = 0.01;
	private static double TARIFF_EMPLOYEE_UNEMPLOYMENT = 0.01;
	// Insurance for guarantee
	// SK: Garance poistenie
	private static String NAME_INSURANCE_GUARANTEE = "Guarantee insurance";
	private static double TARIFF_EMPLOYER_GUARANTEE = 0.0025;
	private static double TARIFF_EMPLOYEE_GUARANTEE = 0.0;
	// Insurance for injury
	// SK: Urazove poistenie
	private static String NAME_INSURANCE_INJURY = "Injury insurance";
	private static double TARIFF_EMPLOYER_INJURY = 0.0475;
	private static double TARIFF_EMPLOYEE_INJURY = 0.0;
	// Insurance for reserve
	// SK: Rezervny fond solidarity
	private static String NAME_INSURANCE_RESERVE = "Reserve insurance";
	private static double TARIFF_EMPLOYER_RESERVE = 0.008;
	private static double TARIFF_EMPLOYEE_RESERVE = 0.0;

	// Assessed base
	// SK: Vymeriavaci zaklad
	private static double MIN_ASSESSED_BASE = 75.0;
	private static double MAX_ASSESSED_BASE = 4290.0;

	public SocialInsurance(Observer o, boolean isEmployer) {
		super(o, isEmployer);
	}

	/**
	 * Calculate assessed base
	 * 
	 * @param grossWage
	 *            A prerequisite gross wage
	 */
	private double calcAssessedBase(double grossWage) {
		if (grossWage < MIN_ASSESSED_BASE) {
			return MIN_ASSESSED_BASE;
		} else if (grossWage > MAX_ASSESSED_BASE) {
			return MAX_ASSESSED_BASE;
		}
		return grossWage;
	}

	/**
	 * Calculate insurance for illness
	 * 
	 * @param grossWage
	 *            A prerequisite assessed base
	 */
	private double calcIllnessInsurance(double assessedBase) {
		if (isEmployer()) {
			return assessedBase * TARIFF_EMPLOYER_ILLNESS;
		} else {
			return assessedBase * TARIFF_EMPLOYEE_ILLNESS;
		}
	}

	/**
	 * Calculate insurance for pension
	 * 
	 * @param assessedBase
	 *            A prerequisite assessed base
	 * @param month
	 *            A number from 1 to 12 corresponding to the month from January
	 *            to December
	 */
	private double calcPensionInsurance(double assessedBase, int month) {
		double pensionInsurance;
		if (isEmployer()) {
			pensionInsurance = assessedBase * TARIFF_EMPLOYER_PENSION;
			mPension.addMoney(pensionInsurance, month);
			return pensionInsurance;
		} else {
			pensionInsurance = assessedBase * TARIFF_EMPLOYEE_PENSION;
			mPension.addMoney(pensionInsurance, month);
			return pensionInsurance;
		}
	}

	/**
	 * Calculate insurance for invalidity
	 * 
	 * @param grossWage
	 *            A prerequisite assessed base
	 */
	private double calcInvalidityInsurance(double assessedBase) {
		if (isEmployer()) {
			return assessedBase * TARIFF_EMPLOYER_INVALIDITY;
		} else {
			return assessedBase * TARIFF_EMPLOYEE_INVALIDITY;
		}
	}

	/**
	 * Calculate insurance for unemployment
	 * 
	 * @param grossWage
	 *            A prerequisite assessed base
	 */
	private double calcUnemploymentInsurance(double assessedBase) {
		if (isEmployer()) {
			return assessedBase * TARIFF_EMPLOYER_UNEMPLOYMENT;
		} else {
			return assessedBase * TARIFF_EMPLOYEE_UNEMPLOYMENT;
		}
	}

	/**
	 * Calculate insurance for guarantee
	 * 
	 * @param grossWage
	 *            A prerequisite assessed base
	 */
	private double calcGuaranteeInsurance(double assessedBase) {
		if (isEmployer()) {
			return assessedBase * TARIFF_EMPLOYER_GUARANTEE;
		} else {
			return assessedBase * TARIFF_EMPLOYEE_GUARANTEE;
		}
	}

	/**
	 * Calculate insurance for injury
	 * 
	 * @param grossWage
	 *            A prerequisite assessed base
	 */
	private double calcInjuryInsurance(double assessedBase) {
		if (isEmployer()) {
			return assessedBase * TARIFF_EMPLOYER_INJURY;
		} else {
			return assessedBase * TARIFF_EMPLOYEE_INJURY;
		}
	}

	/**
	 * Calculate insurance for reserve
	 * 
	 * @param grossWage
	 *            A prerequisite assessed base
	 */
	private double calcReserveInsurance(double assessedBase) {
		if (isEmployer()) {
			return assessedBase * TARIFF_EMPLOYER_RESERVE;
		} else {
			return assessedBase * TARIFF_EMPLOYEE_RESERVE;
		}
	}

	/**
	 * Calculate total this taxation
	 * 
	 * @param grossWage
	 *            A prerequisite gross wage
	 * @param A
	 *            number from 1 to 12 corresponding to the month from January to
	 *            December
	 */
	@Override
	public double calcTotal(double grossWage, int month) {
		double assessedBase = calcAssessedBase(grossWage);

		double illnessInsurance = calcIllnessInsurance(assessedBase);
		double pensionInsurance = calcPensionInsurance(assessedBase, month);
		double invalidityInsurance = calcInvalidityInsurance(assessedBase);
		double unemploymentInsurance = calcUnemploymentInsurance(assessedBase);
		double guaranteeInsurance = calcGuaranteeInsurance(assessedBase);
		double injuryInsurance = calcInjuryInsurance(assessedBase);
		double reserveInsurance = calcReserveInsurance(assessedBase);

		// START output
		String fluxLine = Utilities.buildFlux((isEmployer()) ? Constants.NAME_EMPLOYER : Constants.NAME_EMPLOYEE,
				NAME_SOCIAL_INSTITUTION);
		String illnessLine = Utilities.buildCost(NAME_INSURANCE_ILLNESS, illnessInsurance);
		String pensionLine = Utilities.buildCost(NAME_INSURANCE_PENSION, pensionInsurance);
		String invalidityLine = Utilities.buildCost(NAME_INSURANCE_INVALIDITY, invalidityInsurance);
		String unemploymentLine = Utilities.buildCost(NAME_INSURANCE_UNEMPLOYMENT, unemploymentInsurance);
		String guaranteeLine = Utilities.buildCost(NAME_INSURANCE_GUARANTEE, guaranteeInsurance);
		String injuryLine = Utilities.buildCost(NAME_INSURANCE_INJURY, injuryInsurance);
		String reserveLine = Utilities.buildCost(NAME_INSURANCE_RESERVE, reserveInsurance);
		informUser(month, fluxLine, illnessLine, pensionLine, invalidityLine, unemploymentLine, guaranteeLine,
				injuryLine, reserveLine);
		// END output

		double total = 0.0;
		total += illnessInsurance;
		total += pensionInsurance;
		total += invalidityInsurance;
		total += unemploymentInsurance;
		total += guaranteeInsurance;
		total += injuryInsurance;
		total += reserveInsurance;
		return total;
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
