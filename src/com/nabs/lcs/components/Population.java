package com.nabs.lcs.components;

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

}
