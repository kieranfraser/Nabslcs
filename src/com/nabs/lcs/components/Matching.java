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

	private ArrayList<Classifier> matchedSet;

	public Matching(){
		this.matchedSet = new ArrayList<Classifier>();
	}
	
	public void generateMatchSet(ArrayList<Classifier> population, ArrayList<Feature> currentSituation){
		this.matchedSet = new ArrayList<Classifier>();
		while(matchedSet.isEmpty()){
			for(Classifier c : population){
				if(doesMatch(c, currentSituation))
					this.matchedSet.add(c);
			}
			if(countActions() < LearningParams.getInstance().getMinActions()){
				Classifier coverClassifier = Covering.generateCoveringClassifier(this.matchedSet, currentSituation);
				population.add(coverClassifier);
				// delete from population - keeps the number of classifiers under param
				this.matchedSet = new ArrayList<Classifier>();
			}
		}
	}
	
	private boolean doesMatch(Classifier c, ArrayList<Feature> s){
		for(int i=0; i<c.getCondition().size(); i++){
			Feature x = c.getCondition().get(i);
			Feature y = s.get(i);
			if(x.getType() != FeatureType.WILDCARD && x.getValue() != y.getValue()){
				return false;
			}
		}
		return true;
	}
	
	private int countActions(){
		int uniqueActions = 0;
		ArrayList<String> actionStrings = new ArrayList<String>();
		for(Classifier c : this.matchedSet){
			if(!actionStrings.contains(c.getAction().getName())){
				actionStrings.add(c.getAction().getName());
				uniqueActions++;
			}
		}
		return uniqueActions;
	}

	public ArrayList<Classifier> getMatchedSet() {
		return matchedSet;
	}	
}
