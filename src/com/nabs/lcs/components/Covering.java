package com.nabs.lcs.components;

import java.util.ArrayList;
import java.util.Random;

import com.nabs.models.Classifier;
import com.nabs.models.LearningParams;
import com.nabs.models.actions.Action;
import com.nabs.models.features.Feature;
import com.nabs.models.features.OrdinalFeature;

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
		int i = 0;
		for(Feature x : condition){
			if(generator.nextFloat() < LearningParams.getInstance().getCrossoverProbability()){
				x = new OrdinalFeature(OrdinalFeature.DEFAULT_VALUE);
			}
			else{
				x = currentSituation.get(i);
			}
			i++;
		}
		return c;
	}
	
	private Action actionNotPresentInM(ArrayList<Classifier> matchedSet){
		/**
		 * Needs to be changed to get the complete list of 
		 * possible actions.
		 */
		ArrayList<Action> allActions = new ArrayList<Action>(); 
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
