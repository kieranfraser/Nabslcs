package com.nabs.lcs;

import java.util.ArrayList;
import java.util.Scanner;

import com.nabs.models.Notification;
import com.nabs.models.actions.Action;
import com.nabs.models.features.Feature;
import com.nabs.models.features.OrdinalFeature;
import com.nabs.models.features.RealValueFeature;

public class Environment {

	public static Environment environment;
	
	private ArrayList<Notification> notifications;
	private ArrayList<Feature> currentSituation;
	private int currentInstance;
	
	private ArrayList<Action> executedActions;
	
	private ArrayList<ArrayList<Feature>> situations;
	
	public static synchronized Environment getInstance(){
		if(environment == null){
			environment = new Environment();
		}
		return environment;
	}
	/**
	 * Initializes the environment. 
	 */
	private Environment(){
		this.currentInstance = 0;
		executedActions = new ArrayList<>();
		situations = new ArrayList<>();
		createSimData();
	}
	
	/**
	 * Create some simulated notification data.
	 */
	private void createSimData(){
		ArrayList<Feature> first = new ArrayList<>();
		first.add(new OrdinalFeature(1));
		first.add(new OrdinalFeature(3));
		first.add(new OrdinalFeature(2));
		first.add(new RealValueFeature(23345345.03));
		
		ArrayList<Feature> second = new ArrayList<>();
		second.add(new OrdinalFeature(3));
		second.add(new OrdinalFeature(3));
		second.add(new OrdinalFeature(3));
		second.add(new RealValueFeature(23347777.03));
		
		ArrayList<Feature> third = new ArrayList<>();
		third.add(new OrdinalFeature(1));
		third.add(new OrdinalFeature(3));
		third.add(new OrdinalFeature(3));
		third.add(new RealValueFeature(23348888.03));
		
		ArrayList<Feature> fourth = new ArrayList<>();
		fourth.add(new OrdinalFeature(1));
		fourth.add(new OrdinalFeature(3));
		fourth.add(new OrdinalFeature(1));
		fourth.add(new RealValueFeature(23349998.03));
		
		ArrayList<Feature> fifth = new ArrayList<>();
		fifth.add(new OrdinalFeature(2));
		fifth.add(new OrdinalFeature(3));
		fifth.add(new OrdinalFeature(1));
		fifth.add(new RealValueFeature(23349999.03));
		
		situations.add(first);
		situations.add(second);
		situations.add(third);
		situations.add(fourth);
		situations.add(fifth);
		
	}
	
	/**
	 * Get the next data instance if it exists.
	 * @return
	 */
	public ArrayList<Feature> getNextInstance(){
		this.currentSituation = situations.get(currentInstance);
		currentInstance++;
		return this.currentSituation;
	}
	
	public ArrayList<Feature> getCurrentSituation() {
		return currentSituation;
	}

	public void executeAction(Action a){
		executedActions.add(a);
	}
	
	public Double getReward(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter a reward value: ");
		return Double.valueOf(scanner.next());
	}
	
}
