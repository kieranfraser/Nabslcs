package com.nabs.lcs.components;

import java.util.ArrayList;
import java.util.Random;

import com.nabs.models.Classifier;
import com.nabs.models.LearningParams;
import com.nabs.models.actions.Action;
import com.nabs.models.actions.OrdinalAction;

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

	private static Population instance;
	private long n;
	private ArrayList<Action> allActions;
	
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
	
	public static synchronized Population getInstance(){
		if(instance == null){
			instance = new Population();
		}
		return instance;
	}
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
	private Population(){
		n = LearningParams.getInstance().getMaxPopulation();
		classifiers = new ArrayList<Classifier>();
	}

	public ArrayList<Classifier> getPopulation() {
		return classifiers;
	}	
	
	/**
	 * Checks to see if the classifier to be inserted is 
	 * identical in condition and action with a classifier already
	 * in the population. If so the latter's numerosity is incremented;
	 * if not the new classifier is added to the population.
	 * @param child
	 */
	public void insertChild(Classifier child){
		for(Classifier c : classifiers){
			if(c.getCondition() == child.getCondition() && c.getAction() == child.getAction()){
				c.setNumerosity(c.getNumerosity()+1);
				return;
			}
		}
		classifiers.add(child);
	}
	
	public void deleteFromPopulation(){
		Random generator = new Random();
		if(sumOfNumerosities() <= LearningParams.getInstance().getMaxPopulation()){
			return;
		}
		double avFitnessInPopulation = sumOfFitness() / sumOfNumerosities();
		double voteSum = 0;
		for(Classifier c : classifiers){
			voteSum = voteSum + deletionVote(c, avFitnessInPopulation);
		}
		double choicePoint = generator.nextFloat() * voteSum;
		voteSum = 0;
		for(Classifier c : classifiers){
			voteSum = voteSum + deletionVote(c, avFitnessInPopulation);
			if(voteSum > choicePoint){
				if(c.getNumerosity() > 1){
					c.setNumerosity(c.getNumerosity() - 1);
				}
				else{
					classifiers.remove(c);
				}
				return;
			}
		}
	}
	
	private double deletionVote(Classifier c, double avFitnessInPopulation){
		double vote = c.getActionSetSize() * c.getNumerosity();
		if(c.getExperience() > LearningParams.getInstance().getDeletionThreshold() && 
				c.getFitness() / c.getNumerosity() < LearningParams.getInstance().getFitnessFractionProbDeletion() * avFitnessInPopulation){
			vote = vote * avFitnessInPopulation / (c.getFitness() / c.getNumerosity()); 
		}
		return vote;			
	}
	
	private double sumOfFitness(){
		double sum = 0.0;
		for(Classifier c : classifiers){
			sum += c.getFitness();
		}
		return sum;
	}
	
	private double sumOfNumerosities(){
		double sum = 0.0;
		for(Classifier c : classifiers){
			sum += c.getNumerosity();
		}
		return sum;
	}
	
	/**
	 * Returns a random action that isn't already the
	 * chosen action.
	 * @param action
	 */
	public Action getDifferingRandomAction(Action currentAction){
		Random generator = new Random();
		Action a = allActions.get(generator.nextInt(allActions.size()));
		if(a != currentAction){
			return a;
		}
		else{
			return getDifferingRandomAction(currentAction);
		}
	}
	
	public static ArrayList<Action> getAllActions(){
		ArrayList<Action> actions = new ArrayList<Action>();
		Action a1 = new OrdinalAction(1000);
		a1.setName("Now");
		actions.add(a1);
		
		Action a2 = new OrdinalAction(1000);
		a2.setName("Next Break");
		actions.add(a2);
		
		Action a3 = new OrdinalAction(1000);
		a3.setName("Next Free Period");
		actions.add(a3);
		
		Action a4 = new OrdinalAction(1000);
		a4.setName("Later");
		actions.add(a4);
		
		return actions;
	}
}
