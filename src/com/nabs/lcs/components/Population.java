package com.nabs.lcs.components;

import java.util.ArrayList;

import com.nabs.models.Classifier;
import com.nabs.models.LearningParams;

/**
 * Consists of all classifiers in the LCS. Max number of rules is a run parameter. 
 * Michigan style - all rules together make up the model and prediction.
 * Rules made up of condition - action -> if then.
 * Action gives predicted end point.
 * 
 * Rules have associated params:
 * 	Numerosity - number of classifier copies that exist in the population.
 * 	Match Count - number of times classifier has been in a match set.
 *  Correct Count - number of times it's been in a correct set.
 *  Accuracy - Correct count /  Match count (different to training accuracy of ML model)
 *  Fitness - function of accuracy to describe the utility of a classifier. Influences the survival, 
 *  			reproductive success and predictive influence of a classifier
 *  Birth iteration
 *  Average match set size
 * @author Kieran
 *
 */
public class Population {

	private long n;
	
	/**
	 * Classifier representation:
	 * Nominal/Ordinal features encoded {1,2,3,..,n,#}
	 * Binary Features encoded {1,0,#}
	 * Real numbers encoded in intervals [s, l]
	 * Payoff is the delivery class
	 * e.g. sender, subject, app, time = delivery class
	 * 			2, 3, 2, 2342343.341 = now
	 * 
	 * Required to parse string into condition -> result using split('=')
	 */
	private ArrayList<Classifier> classifiers;
	
	/**
	 * On initialization:
	 * The population [P] can either be left empty or can be filled with the maximal
	 * number of classifiers N, generating each classifier with a random condition and
	 * action and the initial parameters. The two methods differ only slightly in their
	 * effect on performance. Thus the simpler way of leaving the population empty in
	 * the beginning is commonly used.
	 * 
	 * Currently leaving empty.
	 */
	public Population(){
		n = LearningParams.getInstance().getMaxPopulation();
		classifiers = new ArrayList<Classifier>();
		
		
	}
}
