package com.nabs.lcs.components;

import java.util.ArrayList;

import com.nabs.models.Classifier;

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
		Classifier cl = new Classifier();
		
		for(Classifier c : actionSet){
			if(couldSubsume(c)){
				
			}
		}
		if(cl != null){
			for(Classifier c :  actionSet){
				if(moreGeneral(cl, c)){
					cl.setNumerosity(cl.getNumerosity() + c.getNumerosity());
				}
			}
		}
	}
}
