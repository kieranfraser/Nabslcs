package com.nabs.lcs.components;

import java.util.ArrayList;

import com.nabs.models.Classifier;
import com.nabs.models.LearningParams;
import com.nabs.models.features.Feature;
import com.nabs.models.features.Feature.FeatureType;

/**
 * Search for rules that have conditions matching the attribute values
 * of a given instance (compares to all rules in the population).
 * Wildcards match by default.
 * Endpoint is ignored.
 * 
 * @author Kieran
 *
 */
public class Matching {
	
	public static ArrayList<Classifier> generateMatchSet(ArrayList<Classifier> population, ArrayList<Feature> currentSituation){
		ArrayList<Classifier> matchedSet = new ArrayList<Classifier>();
		while(matchedSet.isEmpty()){
			for(Classifier c : population){
				if(doesMatch(c, currentSituation))
					matchedSet.add(c);
			}
			//if(countActions(matchedSet) < LearningParams.getInstance().getMinActions()){
			if(countActions(matchedSet) < 4){
				//System.out.println(countActions(matchedSet));
				//System.out.println("Starting Covering");
				
				Classifier coverClassifier = Covering.generateCoveringClassifier(matchedSet, currentSituation);
				population.add(coverClassifier);
				// delete from population - keeps the number of classifiers under param
				matchedSet = new ArrayList<Classifier>();
			}
			//System.out.println(matchedSet.size());
		}
		//System.out.println(matchedSet.size());
		return matchedSet;
	}
	
	private static boolean doesMatch(Classifier c, ArrayList<Feature> s){
		//System.out.println(c.toString());
		//System.out.println(s.toString());
		for(int i=0; i< c.getCondition().size(); i++){
			Feature x = c.getCondition().get(i);
			Feature y = s.get(i);
			if(x.getType() != FeatureType.WILDCARD && x.getValue() != y.getValue()){
				return false;
			}
		}
		return true;
	}
	
	private static int countActions(ArrayList<Classifier> matchedSet){
		int uniqueActions = 0;
		ArrayList<String> actionStrings = new ArrayList<String>();
		for(Classifier c : matchedSet){
			if(!actionStrings.contains(c.getAction().getName())){
				actionStrings.add(c.getAction().getName());
				uniqueActions++;
			}
		}
		return uniqueActions;
	}
	
}
