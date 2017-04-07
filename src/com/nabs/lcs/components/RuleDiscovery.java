package com.nabs.lcs.components;

import java.util.ArrayList;
import java.util.Random;

import com.nabs.lcs.Environment;
import com.nabs.models.Classifier;
import com.nabs.models.LearningParams;
import com.nabs.models.features.Feature;
import com.nabs.models.features.OrdinalFeature;
import com.nabs.models.features.Feature.FeatureType;

/**
 * Genetic algorithm for exploring the search space. Highly elitest. Only 2 added each learning cycle. Includes
 * selection, crossover and mutation. Tournament selection is preferred and implemented.
 * @author kfraser
 *
 */
public class RuleDiscovery {
	
	private static Random generator;

	/**
	 * Genetic algorithm:
	 * action set first checked to see if the GA should run
	 * Avg time period since the last GA application in the set
	 * must be greater than the threshold.
	 * Two classifiers selected using roulette wheel based on 
	 * fitness and the offspring created out of them
	 * Offspring are crossed and mutated
	 * If the offspring are corssed their prediction 
	 * error and fitness values are set to the average of the
	 * parent values.
	 * Offspring are inserted in the population and
	 * necessary deletions made
	 * Subsumption run to check if any rule in the population
	 * covers the new offspring rules. If so, deleted and parent
	 * numerosity is increased.
	 */
	public static void runGeneticAlgorithm(ArrayList<Classifier> actionSet, ArrayList<Feature> situation, Population population){
		double actualTime = 0;
		generator = new Random();
		double[] sums = sumClassifierActionSet(actionSet);
		if((actualTime - (sums[0] / sums[1])) > LearningParams.getInstance().getGaThreshold()){
			for(Classifier c : actionSet){
				c.setTimeStamp(actualTime);
			}
			Classifier parent1 = selectOffspring(actionSet);
			Classifier parent2 = selectOffspring(actionSet);
			Classifier child1 = parent1;
			Classifier child2 = parent2;
			child1.setNumerosity(1);
			child2.setNumerosity(1);
			child1.setExperience(0);
			child2.setExperience(0);
			if(generator.nextFloat() < LearningParams.getInstance().getCrossoverProbability()){
				applyCrossover(child1, child2);
				child1 .setPrediction( (parent1.getPrediction() + parent2.getPrediction())/2);
				child1.setPredictionError( (parent1.getPredictionError() + parent2.getPredictionError())/2);
				child1.setFitness((parent1.getFitness() + parent2.getFitness())/2);
				child2.setPrediction(child1.getPrediction());
				child2.setPredictionError(child1.getPredictionError());
				child2.setFitness(child2.getFitness());
			}
			child1.setFitness(child1.getFitness() * 0.1);
			child2.setFitness(child2.getFitness() * 0.1);
			Classifier[] children = {child1, child2};
			for(Classifier c : children){
				applyMutation(c, situation, population);
				if(LearningParams.getInstance().isDoGASubsumption()){
					if(doesSubsume(parent1, c)){
						parent1.setNumerosity(parent1.getNumerosity()+1);
					}
					else if(doesSubsume(parent2, c)){
						parent2.setNumerosity(parent2.getNumerosity() + 1);
					}
					else{
						population.insertChild(c);
					}
				}
				else{
					population.insertChild(c);
				}
				population.deleteFromPopulation();
			}
		}
	}

	private static double[] sumClassifierActionSet(ArrayList<Classifier> actionSet){
		double[] sumValues = null;
		double firstSum = 0.0;
		double secondSum = 0.0;
		for(Classifier c : actionSet){
			firstSum += c.getTimeStamp() * c.getNumerosity();
			secondSum += c.getNumerosity();
		}
		return sumValues;
	}
	
	private static Classifier selectOffspring(ArrayList<Classifier> actionSet) {
		double fitnessSum = 0;
		for(Classifier c : actionSet){
			fitnessSum = fitnessSum + c.getFitness();
		}
		double choicePoint = generator.nextFloat() * fitnessSum;
		fitnessSum = 0;
		for(Classifier c : actionSet){
			fitnessSum = fitnessSum + c.getFitness();
			if(fitnessSum > choicePoint){
				return c;
			}
		}
		return null;
	}
	
	private static boolean doesSubsume(Classifier parent1, Classifier c) {
		
		return false;
	}

	/**
	 * 
	 * @param c
	 */
	private static void applyMutation(Classifier c, ArrayList<Feature> currentSituation, Population population) {
		int i = 0;
		do {
			if(generator.nextFloat() < LearningParams.getInstance().getMutationProbability()){
				if(c.getCondition().get(i).getType() == FeatureType.WILDCARD){
					ArrayList<Feature> condition = c.getCondition();
					condition.set(i, currentSituation.get(i));
					c.setCondition(condition);
				}
				else{
					ArrayList<Feature> condition = c.getCondition();
					Feature feature = new OrdinalFeature(OrdinalFeature.DEFAULT_VALUE);
					feature.setType(FeatureType.WILDCARD);
					condition.set(i, feature);
					c.setCondition(condition);
				}
			}
			i++;
		} while(i < c.getCondition().size());
		if(generator.nextFloat() < LearningParams.getInstance().getMutationProbability()){
			//randomly chosen other possible action?
			c.setAction(population.getDifferingRandomAction(c.getAction()));
		}
	}

	/**
	 * Applies crossover to child1 and child2 - x and y is the applicable range
	 * - i is the int found to be within the range.
	 * @param child1
	 * @param child2
	 */
	private static void applyCrossover(Classifier child1, Classifier child2) {
		double x = generator.nextFloat() * (child1.getCondition().size() + 1);
		double y = generator.nextFloat() * (child2.getCondition().size() + 1);
		if(x > y){
			switchXAndY(x, y);
		}
		int i = 0;
		do{
			if(x <= i && i < y){
				switchChildFeatures(child1, child2, i);
			}
			i++;
		} while(i < y);
	}
	
	/**
	 * Switches the feature in conditions of child1 and child2 at point i.
	 * @param child1
	 * @param child2
	 * @param i
	 */
	private static void switchChildFeatures(Classifier child1, Classifier child2, int i){
		ArrayList<Feature> condition1 = child1.getCondition();
		ArrayList<Feature> condition2 = child2.getCondition();
		
		Feature child1Feature = condition1.get(i);
		Feature child2Feature = condition2.get(i);
		
		condition1.set(i, child2Feature);
		condition2.set(i, child1Feature);
		
		child1.setCondition(condition1);
		child2.setCondition(condition2);
	}
	
	/**
	 * Switches values 
	 * @param x
	 * @param y
	 */
	private static void switchXAndY(Double x, Double y){
		Double temp = x;
		x = y;
		y = temp;
	}
}
