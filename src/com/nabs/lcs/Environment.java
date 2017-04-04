package com.nabs.lcs;

import java.util.ArrayList;

import com.nabs.models.Notification;
import com.nabs.models.features.Feature;
import com.nabs.models.features.OrdinalFeature;
import com.nabs.models.features.RealValueFeature;

public class Environment {

	private ArrayList<Notification> notifications;
	private int currentInstance;
	
	/**
	 * Initializes the environment. 
	 */
	public Environment(){
		this.currentInstance = 0;
		createSimData();
	}
	
	/**
	 * Create some simulated notification data.
	 */
	private void createSimData(){
		Notification n = new Notification();
		n.setSender("family");
		n.setSubject("social");
		n.setApp("communication");
		
		Notification n1 = new Notification();
		n.setSender("colleague");
		n.setSubject("work");
		n.setApp("communication");
		
		notifications.add(n);
		notifications.add(n1);
	}
	
	/**
	 * Get the next data instance if it exists.
	 * @return
	 */
	public ArrayList<Feature> getNextInstance(){
		ArrayList<Feature> input = new ArrayList<Feature>();
		
		input.add(new OrdinalFeature(1));
		input.add(new OrdinalFeature(3));
		input.add(new OrdinalFeature(2));
		input.add(new RealValueFeature(23345345.03));
		
		return input;
	}
}
