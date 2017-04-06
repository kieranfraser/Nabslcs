package com.nabs.lcs.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.nabs.models.Classifier;
import com.nabs.models.LearningParams;
import com.nabs.models.actions.Action;

/**
 * Update the classifier params e.g. fitness
 * Update match and correct count
 * Update match and incorrect count
 * 
 * 
 * @author Kieran
 *
 */
public class ParameterUpdate {

	public void updateSet(ArrayList<Classifier> actionSet, Double p, Map<Action, Double> predictionArray){
		for(Classifier c : actionSet){
			c.setExperience(c.getExperience()+1);
			if(c.getExperience() < 1/LearningParams.getInstance().getLearningRate()){
				double calcValue = c.getPrediction() + (p - c.getPrediction()) / c.getExperience();
				c.setPrediction(calcValue);
			}
			else{
				double calcValue = c.getPrediction() + LearningParams.getInstance().getLearningRate() * (p - c.getPrediction());
				c.setPrediction(calcValue);
			}
			if(c.getExperience() < 1/LearningParams.getInstance().getLearningRate()){
				double calcValue2 = c.getPredictionError() + (Math.abs(p - c.getPrediction()) / c.getExperience());
				c.setPredictionError(calcValue2);
			}
			else{
				double calcValue2 = c.getPredictionError() + LearningParams.getInstance().getLearningRate() * (Math.abs(p - c.getPrediction()) - c.getExperience());
				c.setPredictionError(calcValue2);
			}
			if(c.getExperience() < 1/LearningParams.getInstance().getLearningRate()){
				double calcValue3 = c.getActionSetSize() + sumActionSetSize(actionSet) / c.getExperience();
				c.setPredictionError(calcValue3);
			}
			else{
				double calcValue3 = c.getActionSetSize() * LearningParams.getInstance().getLearningRate() * sumActionSetSize(actionSet);
				c.setPredictionError(calcValue3);
			}
			updateFitness(actionSet);
			if(LearningParams.getInstance().isDoActionSetSubsumption());
				// ToDo: do action set subsumption in actionSet updating predictionArray
		}
	}
	
	/**
	 * Gets the overall sum of differences between the numerosity and action set size
	 * for each rule in the action set. - ToDo: need to check the c.n value.. typo? cl.n?
	 * @param actionSet
	 * @return
	 */
	private double sumActionSetSize(ArrayList<Classifier> actionSet){
		double sumValue = 0.0;
		for(Classifier c : actionSet){
			sumValue += (c.getNumerosity() - c.getActionSetSize());
		}
		return sumValue;
	}
	
	private void updateFitness(ArrayList<Classifier> actionSet){
		double accuracySum = 0.0;
		Map<Classifier, Double> accuracyVector= new HashMap<Classifier, Double>();
		for(Classifier c : actionSet){
			if(c.getPredictionError() < LearningParams.getInstance().getEpsilon()){
				accuracyVector.put(c, 1.0);
			}
			else{
				double calcValue = LearningParams.getInstance().getAlpha() * Math.pow((c.getPredictionError() / LearningParams.getInstance().getEpsilon()), LearningParams.getInstance().getV());
				accuracyVector.put(c, calcValue);
			}
			accuracySum = accuracySum + accuracyVector.get(c) * c.getNumerosity();
		}
		for(Classifier c : actionSet){
			double calcValue2 = c.getFitness() + LearningParams.getInstance().getLearningRate() * (accuracyVector.get(c) * c.getNumerosity() / accuracySum - c.getFitness());
			c.setFitness(calcValue2);
		}
	}
}
