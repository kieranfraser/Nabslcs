package com.nabs.models.features;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class definition for boolean features.
 * Boolean features appear in the condition of the classifiers and describe
 * attributes such as the notification seen, phone idle etc.
 * 
 * @author kfraser
 *
 */
public class BooleanFeature extends Feature{

	private Logger logger = Logger.getLogger(this.getClass().getName());
	private boolean value;
	
	public BooleanFeature(boolean value){
		logger.log(Level.INFO, "Created ordinal feature.");
		this.value = value;
	}
	
	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public void printValue() {
		logger.log(Level.INFO, "Value: "+value);
	}
}
