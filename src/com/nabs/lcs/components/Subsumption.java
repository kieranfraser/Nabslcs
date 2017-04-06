package com.nabs.lcs.components;

import java.util.ArrayList;
import java.util.Random;

import com.nabs.models.Classifier;
import com.nabs.models.LearningParams;
import com.nabs.models.features.Feature;
import com.nabs.models.features.Feature.FeatureType;

/**
 * Generalisation mechanism. 
 * Examines pairs of rules - looks for one rule a subsumer - must cover the problem space, and more general
 * and just as accurate.
 * Subsumer is copied by incrementing the numerosity.
 * @author Kieran
 *
 */
public class Subsumption {

	public void doActionSetSubsumption(ArrayList<Classifier> actionSet, ArrayList<Classifier> population){
		Classifier cl = null;
		Random generator = new Random();
		for(Classifier c : actionSet){
			if(couldSubsume(c)){
				if(cl == null || numWildcards(c) > numWildcards(cl) || (
						numWildcards(c) == numWildcards(cl) && generator.nextFloat() < 0.5)){
					cl = c;
				}
			}
		}
		if(cl != null){
			for(Classifier c :  actionSet){
				if(isMoreGeneral(cl, c)){
					cl.setNumerosity(cl.getNumerosity() + c.getNumerosity());
					actionSet.remove(c);
					population.remove(c);
				}
			}
		}
	}
	
	private int numWildcards(Classifier c){
		int count = 0;
		for(Feature f : c.getCondition()){
			if(f.getType() == FeatureType.WILDCARD){
				count++;
			}
		}
		return count;
	}
	
	private boolean couldSubsume(Classifier c){
		if(c.getExperience() > LearningParams.getInstance().getSubsumptionThreshold()){
			if(c.getPredictionError() < LearningParams.getInstance().getEpsilon()){
				return true;
			}
		}
		return false;
	}
	
	private boolean isMoreGeneral(Classifier cl, Classifier c){
		if(numWildcards(cl) <= numWildcards(c)){
			return false;
		}
		int i = 0;
		ArrayList<Feature> conditionGen = cl.getCondition();
		ArrayList<Feature> conditionSpec = c.getCondition();
		do{
			if(conditionGen.get(i).getType() != FeatureType.WILDCARD && 
					conditionGen.get(i) != conditionSpec.get(i)){
				return false;
			}
			i++;
		}while( i < cl.getCondition().size());
		return true;
	}
	
	private boolean doesSubsume(Classifier cl, Classifier c){
		if(cl.getAction() == c.getAction()){
			if(couldSubsume(cl)){
				if(isMoreGeneral(cl, c)){
					return true;
				}
			}
		}
		return false;
	}
}
