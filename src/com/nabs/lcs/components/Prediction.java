package com.nabs.lcs.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.nabs.models.Classifier;
import com.nabs.models.actions.Action;

/**
 * Class to calculate and hold the prediction array. 
 * Given an input the XCS maes a best guess prediciton of th epayoff to be expected
 * for each possible action. These systems predictions are stored in an array
 * called the Prediction Array. For an action the P is a fitness
 * weighted average of the predictions of all classifiers in M
 * that advocate that action. If no classifiers in M advocate an 
 * action then it's p value is null.
 * @author Kieran
 *
 */
public class Prediction {

	
	private static Prediction instance;
	
	private Map<Action, Double> predictionMap;
	private Map<Action, Double> fitnessMap;
	
	public static synchronized Prediction getInstance(){
		if(instance==null){
			instance = new Prediction();
		}
		// prediction map insta with all actions
		return instance;
	}
	
	private Prediction(){
		predictionMap = new HashMap<Action, Double>();// is a set of doubles corresponding to the action set.
		fitnessMap = new HashMap<Action, Double>();
	}
	
	public Map<Action, Double> generatePredictionArray(ArrayList<Classifier> matchedSet){
		predictionMap = new HashMap<Action, Double>();
		fitnessMap = new HashMap<Action, Double>();
				
		for(Map.Entry<Action, Double> entry : predictionMap.entrySet()) {
			predictionMap.put(entry.getKey(), null);
	     }
		for(Map.Entry<Action, Double> entry : predictionMap.entrySet()) {
			fitnessMap.put(entry.getKey(), 0.0);
	     }
		
		for(Classifier c : matchedSet){
			double p = c.getPrediction();
			double f = c.getFitness();
			Double paValue = predictionMap.get(c.getAction());
			if(paValue == null){
				double calculatedValue = p * f;
				predictionMap.put(c.getAction(), calculatedValue);
			}
			else{
				double calculatedValue = (paValue+p)
						* f;
				predictionMap.put(c.getAction(), calculatedValue);
			}
			Double fitnessValue = fitnessMap.get(c.getAction()) + c.getFitness();
			fitnessMap.put(c.getAction(), fitnessValue);
		}
		ArrayList<Action> actionSet = new ArrayList<>(); // change to get actionset
		for(Action a : actionSet){
			if(fitnessMap.get(a) != 0.0 && predictionMap.get(a)!=null){
				double calcValue = predictionMap.get(a) / fitnessMap.get(a);
				predictionMap.put(a, calcValue);
			}
		}
		return predictionMap;
	}
}