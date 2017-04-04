package com.nabs.models.features;

/**
 * Parent Class, Feature 
 * @author kfraser
 *
 */
public abstract class Feature {
	
	public enum FeatureType {
	    WILDCARD, ORDINAY
	}
	
	private String name;
	private FeatureType type;
	
	public abstract Object getValue();
	public abstract void printValue();
	
	public String getName() {
		return name;
	}
	public FeatureType getType() {
		return type;
	}
}
