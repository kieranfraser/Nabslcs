package com.nabs.lcs.components;

import java.util.ArrayList;
import java.util.Random;

import com.nabs.lcs.LearningMachine;
import com.nabs.models.Classifier;
import com.nabs.models.LearningParams;
import com.nabs.models.actions.Action;
import com.nabs.models.features.Feature;
import com.nabs.models.features.OrdinalFeature;
import com.nabs.models.features.Feature.FeatureType;

/**
 * If no classifiers are found in the correct set, introduces new rules.
 * Ensures explores rules - but not waste time exploring rules that won't work.
 * Smart initialization.
 * Generate new rule, randomly specifies a random subset of current instance,
 * assigns wildcards for remaining. Uses endpoint of the instance.
 * 
 * 
 * @author Kieran
 *
 */
public class Covering {

	private static Random generator;
	
	public static Classifier generateCoveringClassifier(ArrayList<Classifier> matchedSet, ArrayList<Feature> currentSituation){
		generator = new Random();
		Classifier c = new Classifier();
		ArrayList<Feature> condition = new ArrayList<Feature>(currentSituation.size());
		System.out.println(condition.size());
		System.out.println(currentSituation.size());
		for(int i=0; i<currentSituation.size(); i++){
			if(generator.nextFloat() < LearningParams.getInstance().getCrossoverProbability()){
				Feature x = new OrdinalFeature(OrdinalFeature.DEFAULT_VALUE);
				x.setType(FeatureType.WILDCARD);
				condition.add(i, x);
			}
			else{
				Feature x = currentSituation.get(i);
				condition.add(i, x);
			}
		}
		c.setCondition(condition);
		c.setAction(actionNotPresentInM(matchedSet));
		c.setPrediction(LearningParams.getInstance().getpI());
		c.setPredictionError(LearningParams.getInstance().geteI());
		c.setFitness(LearningParams.getInstance().getfI());
		c.setExperience(0.0);
		c.setTimeStamp(System.currentTimeMillis()); //ToDo: create global time
		c.setActionSetSize(1.0);
		c.setNumerosity(1.0);
		return c;
	}
	
	private static Action actionNotPresentInM(ArrayList<Classifier> matchedSet){
		/**
		 * Needs to be changed to get the complete list of 
		 * possible actions.
		 */
		ArrayList<Action> allActions = Population.getAllActions();
		ArrayList<Action> matchedSetActions = new ArrayList<Action>();
		
		ArrayList<Action> actionsNotPresentInM = new ArrayList<Action>();
		for( Action a : allActions){
			if( !matchedSetActions.contains(a)){
				actionsNotPresentInM.add(a);
			}
		}
		
		generator = new Random();
		Action randomAction = actionsNotPresentInM.get(generator.nextInt(actionsNotPresentInM.size()));
		
		return randomAction;
	}
}
