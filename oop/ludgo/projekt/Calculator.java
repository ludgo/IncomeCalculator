package oop.ludgo.projekt;

import java.util.Observable;
import java.util.Observer;

import oop.ludgo.projekt.CalculatorWindow;
import oop.ludgo.projekt.model.State;
import oop.ludgo.projekt.model.Pension;
import oop.ludgo.projekt.model.Person;
import oop.ludgo.projekt.model.Tax;
import oop.ludgo.projekt.util.Constants;
import oop.ludgo.projekt.util.Utilities;
import oop.ludgo.projekt.valorization.CompanyAAA;
import oop.ludgo.projekt.valorization.CompanyBBB;
import oop.ludgo.projekt.valorization.CompanyCCC;
import oop.ludgo.projekt.valorization.SecondPillar;

/**
 * A class to use other classes to fulfil the total functionality of the app.
 * {@link Observable} by {@link CalculatorWindow.MyCustomizedPanel}
 */
public class Calculator extends Observable {
	
	private Observer mObserver;
	
	// Each mode represents an unique combination: a single pension company plus its fund
	public enum CalcMode {
		First,
	    SecondAAAbond, SecondAAAshare, SecondAAAindex,
	    SecondBBBbond, SecondBBBshare,
	    SecondCCCbond, SecondCCCindex
	}
	
	public Calculator(CalculatorWindow window) {
		mObserver = window.getPanel();
		this.addObserver(mObserver);
	}
	
	/**
	 * A central method to initiate app's calculations
	 * @param grossWages Input gross wages by months
	 * @param balance Input balance as the beginning of the year
	 * @param numChildren Input number of children
	 * @param mode A type of saving money
	 */
	public void beginCalculation(double[] grossWages, int numChildren,
			CalcMode mode, double balance){
		// Output beginning
		changeAndNotifyObserver("--- Financial flows in the year: ---\n\n");
		
		// At the beginning, there is an employee person and the country when they live
		State state = new State(mObserver);
		Person person = new Person(grossWages, numChildren);
		// As the person earns money, they must pay tax
		Tax tax = state.payExpenses(person);
		// After the all tax has been paid, person's net wage can be determined
		person.calcNetWages(tax);
		
		// It creates a space to calculate how much the work done actually cost
		double costOfWork = person.getWorkCostTotal(tax);
		// and how much of it the person actually got
		double earning = person.getNetWagesTotal();
		// and how much of it went to person's pension
		Pension pension = state.getPension();
		
		// Summary at the end
		changeAndNotifyObserver("\n--- Summary: ---\n");
		changeAndNotifyObserver("\nThe work the person has done cost the employer and the state: ");
		changeAndNotifyObserver(Utilities.buildCost(null, costOfWork));
		changeAndNotifyObserver("\nThe person got: ");
		changeAndNotifyObserver(Utilities.buildCost(null, earning));
		
		if (mode == CalcMode.First) {
			continueWithFirst(pension);
		} else {
			continueWithSecond(pension, mode, balance);
		}
	}
	
	/**
	 * The calculation which follows if the person is in the first pillar
	 */
	private void continueWithFirst(Pension pension) {
		// How much went to the state
		changeAndNotifyObserver("\nThe part of the pension savings to the state: ");
		changeAndNotifyObserver(Utilities.buildCost(null, pension.getTotal()));
		changeAndNotifyObserver("\nThe person has to rely on the state 100% to get any pension.");
	}
	
	/**
	 * The calculation which follows if the person is in the second pillar
	 */
	private void continueWithSecond(Pension pension, CalcMode mode, double balance) {
		// How much went to the state
		double statePart = Constants.PERCENTAGE_PILLAR_FIRST / (Constants.PERCENTAGE_PILLAR_FIRST + Constants.PERCENTAGE_PILLAR_SECOND);
		Pension statePension = Pension.getPensionFraction(pension, statePart);
		double stateTotal = statePension.getTotal();
		changeAndNotifyObserver("\nThe part of the pension savings to the state: ");
		changeAndNotifyObserver(Utilities.buildCost(null, stateTotal));
		changeAndNotifyObserver(String.format(
				"\nThe person has to rely on the state %.2f%% to get any pension.",
				statePart * 100
		// How much went to the pension company
		));
		double companyPart = Constants.PERCENTAGE_PILLAR_SECOND / (Constants.PERCENTAGE_PILLAR_FIRST + Constants.PERCENTAGE_PILLAR_SECOND);
		Pension companyPension = Pension.getPensionFraction(pension, companyPart);
		double companyTotal = companyPension.getTotal();
		changeAndNotifyObserver("\nThe part of the pension savings to the company: ");
		changeAndNotifyObserver(Utilities.buildCost(null, companyTotal));
		changeAndNotifyObserver(String.format(
				"\nThe person has to rely on the company %.2f%% to get any pension.",
				companyPart * 100
		));
		
		// Create pension company instance
		SecondPillar secondPillar;
		switch (mode) {
			case SecondAAAbond:
				secondPillar = new CompanyAAA(CompanyAAA.AAA_BOND_FUND);
				break;
			case SecondAAAshare:
				secondPillar = new CompanyAAA(CompanyAAA.AAA_SHARE_FUND);
				break;
			case SecondAAAindex:
				secondPillar = new CompanyAAA(CompanyAAA.AAA_INDEX_FUND);
				break;
			case SecondBBBbond:
				secondPillar = new CompanyBBB(CompanyBBB.BBB_BOND_FUND);
				break;
			case SecondBBBshare:
				secondPillar = new CompanyBBB(CompanyBBB.BBB_SHARE_FUND);
				break;
			case SecondCCCbond:
				secondPillar = new CompanyCCC(CompanyCCC.CCC_BOND_FUND);
				break;
			case SecondCCCindex:
				secondPillar = new CompanyCCC(CompanyCCC.CCC_INDEX_FUND);
				break;
			default:
				secondPillar = new CompanyAAA(CompanyAAA.AAA_BOND_FUND);
				break;
		}
		// Valorize money
		double newBalance = balance;
		double[] money = companyPension.getMoney();
		for (int i=0; i<money.length; i++) {
			newBalance = secondPillar.valorizeMoney(newBalance);
			newBalance += money[i];
		}
		double increase = 100 * (newBalance - balance - companyTotal) / (balance + companyTotal);
		// Summarize the valorization and its success
		changeAndNotifyObserver(String.format(
				"\nThe company changed the savings by %.2f%%",
				increase
		));
		changeAndNotifyObserver("\nAt the end of the year, the persons's new balance is: ");
		changeAndNotifyObserver(Utilities.buildCost(null, newBalance));
	}
	
	// Run program
	public static void main(String[] args) {
		CalculatorWindow window = new CalculatorWindow();
		Calculator calculator = new Calculator(window);
		window.setCalculator(calculator);
	}
	
	private void changeAndNotifyObserver(String text) {
		setChanged();
		notifyObservers(text);
	}
}
