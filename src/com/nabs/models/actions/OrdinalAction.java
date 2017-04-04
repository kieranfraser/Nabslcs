package com.nabs.models.actions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OrdinalAction extends Action{

	public final static int DEFAULT_ACTION = 0;
	public final static String DEFAULT_NAME= "default_name";
	public final static String TYPE = "ordinal";
	
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private int value;
		
	public OrdinalAction(int value){
		this.value = value;
		this.setType(TYPE);
		this.setName(DEFAULT_NAME);
	}

	@Override
	public void print() {
		logger.log(Level.INFO, "Value: "+value);
	}
}
