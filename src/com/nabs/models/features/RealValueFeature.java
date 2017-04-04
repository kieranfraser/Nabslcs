package com.nabs.models.features;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class definition for real value features.
 * Real value features appear in the condition of the classifiers and describe
 * attributes such as the notification time.
 * 
 * @author kfraser
 *
 */
public class RealValueFeature extends Feature{

	private Logger logger = Logger.getLogger(this.getClass().getName());
	private double value;
	
	public RealValueFeature(double value){
		this.value = value;
	}

	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public void printValue() {
		logger.log(Level.INFO, "Value: "+value);
	}
}
