package com.nabs.models;

import java.util.ArrayList;

import com.nabs.models.actions.Action;
import com.nabs.models.actions.OrdinalAction;
import com.nabs.models.features.Feature;

public class Classifier {

	private ArrayList<Feature> condition;
	private Action action;
	
	/**
	 * p - estimates (average of) the payoff expected if the classifier
	 * matches and its action is taken by the system.
	 * Payoff = combination of p (the expected reward) and the payoff prediction
	 * of the best possible action in the next state.
	 * In single step problems, payoff reduces to the reward produced by the proposed
	 * action.
	 */
	private double prediction;
	
	/**
	 * Epsilon - the prediction error - estimates the errors made in the predictions.
	 * Measures the error of the predictions in units of payoff.
	 */
	private double predictionError;
	
	/**
	 * F - the classifiers fitness.
	 */
	private double fitness;
	
	/**
	 * exp - counts the number of times since its creation that the classifier 
	 * has belonged to an action set.
	 */
	private double experience;
	
	/**
	 * ts - denotes the time-step of the last occurence of a GA in an action
	 * set to which this classifier belonged.
	 */
	private double timeStamp;
	
	/**
	 * as - estimates the aveage size of the action sets this classifier has 
	 * belonged to.
	 */
	private double actionSetSize;
	
	/**
	 * n - reflects the number of micro-classifiers (ordinary classifiers) this
	 * classifier - which is technically called a macroclassifier - represents.
	 */
	private double numerosity;
	
	public Classifier(){
		this.condition = new ArrayList<Feature>();
		action = new OrdinalAction(OrdinalAction.DEFAULT_ACTION);
		prediction = LearningParams.getInstance().getpI();
		fitness = LearningParams.getInstance().getfI();
	}

	public ArrayList<Feature> getCondition() {
		return condition;
	}

	public void setCondition(ArrayList<Feature> condition) {
		this.condition = condition;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public double getPrediction() {
		return prediction;
	}

	public double getFitness() {
		return fitness;
	}
	
	
}
