package com.nabs.models.features;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class definition for ordinal features.
 * Ordinal features appear in the condition of the classifiers and describe
 * attributes such as the sender, subject, app of a notification.
 * 
 * For example:
 * 
 * Rank -> Sender
 * 1 -> Family
 * 2 -> Friends
 * 3 -> Colleagues
 * 4 -> Acquaintances 
 * 5 -> Stranger 
 * 
 * @author kfraser
 *
 */
public class OrdinalFeature extends Feature {

	public static final int DEFAULT_VALUE = 0;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private int value;
	
	public OrdinalFeature(int value){
		this.setType(FeatureType.ORDINAL);
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public void printValue() {
		logger.log(Level.INFO, "Value: "+value);
	}

	@Override
	public String toString() {
		String val = "type: "+this.getType() + " value: "+value;
		return val;
	}
	
	
}
