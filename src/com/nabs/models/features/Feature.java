package com.nabs.models.features;

/**
 * Parent Class, Feature 
 * @author kfraser
 *
 */
public abstract class Feature {
	
	private String name;
	private String type;
	
	public abstract Object getValue();
	public abstract void printValue();

}
