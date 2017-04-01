package com.nabs.lcs;

import java.util.ArrayList;

import com.nabs.models.Notification;

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
	public String getNextInstance(){
		String input = null;
		
		input = "2,3,2,423423.234=verysoon";
		
		return input;
	}
}
