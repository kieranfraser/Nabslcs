package com.nabs.lcs.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.nabs.models.Classifier;
import com.nabs.models.LearningParams;
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
	private ArrayList<Classifier> actionSet;
	
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
		this.predictionMap = new HashMap<Action, Double>();
		this.fitnessMap = new HashMap<Action, Double>();
			
		ArrayList<Action> allActions = Population.getInstance().getAllActions();
		for(int i=0; i < allActions.size(); i++){
			predictionMap.put(allActions.get(i), null);
			fitnessMap.put(allActions.get(i), 0.0);
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

		for(Action a : allActions){
			if(fitnessMap.get(a) != 0.0 && predictionMap.get(a)!=null){
				double calcValue = predictionMap.get(a) / fitnessMap.get(a);
				predictionMap.put(a, calcValue);
			}
		}
		return predictionMap;
	}
	
	/**
	 * Used in Action selection for choosing a non null action
	 * @return
	 */
	private static Action getRandomAction(Map<Action, Double> predMap){
		Random generator = new Random();
		ArrayList<Action> nonNullActions = new ArrayList<Action>();
		for(Map.Entry<Action, Double> entry : predMap.entrySet()) {
			if(entry.getKey() != null){
				nonNullActions.add(entry.getKey());
			}
	     }
		return nonNullActions.get(generator.nextInt(nonNullActions.size()));
	}
	
	/**
	 * Gets the action with the current best predicted outcome value.
	 * @return
	 */
	private static Action getBestAction(Map<Action, Double> predMap){
		Action currentBestAction = null;
		Double currentBestPA = 0.0;
		for(Map.Entry<Action, Double> entry : predMap.entrySet()) {
			if(predMap.get(entry.getKey()) >= currentBestPA){
				currentBestAction = entry.getKey();
			}
	     }
		return currentBestAction;
	}
	
	/**
	 * Used to select an action
	 */
	public static Action actionSelection(Map<Action, Double> predMap){
		Random generator = new Random();
		if(generator.nextFloat() < LearningParams.getInstance().getPexplr()){
			System.out.println("Random action");
			return getRandomAction(predMap);
		}
		else{
			System.out.println("Best action");
			return getBestAction(predMap);
		}
	}
	
	/**
	 * 
	 * @param matchedSet
	 * @param chosenAction
	 */
	public ArrayList<Classifier> generateActionSet(ArrayList<Classifier> matchedSet, Action chosenAction){
		actionSet = new ArrayList<Classifier>();
		for(Classifier c : matchedSet){
			if(c.getAction() == chosenAction){
				actionSet.add(c);
			}
		}
		return actionSet;
	}
}