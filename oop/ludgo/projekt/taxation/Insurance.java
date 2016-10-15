package oop.ludgo.projekt.taxation;

import java.util.Observer;

/**
 * A root class for all insurance types as the subtype of {@link Taxation}
 */
public abstract class Insurance extends Taxation {
	
	// Different mode for employer and employee,
	// since both cases use calculation based on different tariff coefficients
	private boolean mEmployer; 
	
	public Insurance(Observer o, boolean isEmployer) {
		super(o);
		mEmployer = isEmployer;
	}
	
	public boolean isEmployer() {
		return mEmployer;
	}
}
