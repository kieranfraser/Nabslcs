package com.nabs.models.actions;

/**
 * Action class definition. 
 * @author kfraser
 *
 */
public abstract class Action {

	private String name;
	private String type;
	
	public abstract void print();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		String value = "";
		
		value = "Name: "+this.name+"\n Type: "+this.type+"\n";
		
		return value;
	}
	
	
}
