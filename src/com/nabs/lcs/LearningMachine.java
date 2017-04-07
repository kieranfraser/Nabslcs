package com.nabs.lcs;

import java.util.ArrayList;
import java.util.Map;

import com.nabs.lcs.components.Covering;
import com.nabs.lcs.components.Deletion;
import com.nabs.lcs.components.MatchedSets;
import com.nabs.lcs.components.Matching;
import com.nabs.lcs.components.ParameterUpdate;
import com.nabs.lcs.components.Population;
import com.nabs.lcs.components.Prediction;
import com.nabs.lcs.components.RuleDiscovery;
import com.nabs.lcs.components.Subsumption;
import com.nabs.models.Classifier;
import com.nabs.models.LearningParams;
import com.nabs.models.actions.Action;
import com.nabs.models.features.Feature;

/**
 * Main XCS algorithm 
 * @author kfraser
 *
 */
public class LearningMachine {
	
	private LearningParams learningParams;
	
	/**
	 * Actual time.
	 */
	private long actualTime;
	
	private Environment environment;
	private Population pComponent;
	private Matching mComponent;
	private MatchedSets icComponent;
	private Covering cComponent;
	private ParameterUpdate updateComponent;
	private Subsumption subComponent;
	private RuleDiscovery rdComponent;
	private Deletion delCompent;
	private Prediction predictionComponent;
	

	ArrayList<ArrayList<Classifier>> actionSetLog;
	ArrayList<Double> rewardLog;
	ArrayList<ArrayList<Feature>> situationLog;
	
	private ArrayList<Feature> currentSituation;
	
	/**
	 * Function initializes the LM by initializing 
	 * each component.
	 * 
	 * When XCS is started, the modules must first of all be initialized. The parameters
	 * in the environment must be set and, e.g., a maze must be read in. Also,
	 * the reinforcement program rp must be initialized. Finally, XCS itself must be
	 * initialized. Apart from the parameter settings and the start of the time-step
	 * counter referred to as actual time t, the population [P] needs to be initialized.
	 */
	public LearningMachine(){
		this.actualTime = 0;
		learningParams = LearningParams.getInstance();
		// Reinforcement Program rp must be initialized
		this.actionSetLog = new ArrayList<ArrayList<Classifier>>();
		this.rewardLog = new ArrayList<Double>();
		this.situationLog = new ArrayList<ArrayList<Feature>>();
		
		this.environment = Environment.getInstance();
		this.pComponent = Population.getInstance();
		this.mComponent = new Matching();
		this.icComponent = new MatchedSets();
		this.cComponent = new Covering();
		this.updateComponent = new ParameterUpdate();
		this.subComponent = new Subsumption();
		this.rdComponent = new RuleDiscovery();
		this.delCompent = new Deletion();
		
		mainLoop();
	}

	/**
	 * Main Loop of XCS
	 */
	private void mainLoop(){
		do{
			System.out.println("Getting situation");
			currentSituation = environment.getNextInstance();
			System.out.println("Getting matchedSet");
			ArrayList<Classifier> matchedSet = mComponent.generateMatchSet(pComponent.getInstance().getPopulation(), currentSituation);
			System.out.println("Getting prediction array");
			Map<Action, Double> predictionMap = predictionComponent.getInstance().generatePredictionArray(matchedSet);
			System.out.println("Getting chosen action");
			Action chosenAction = predictionComponent.getInstance().actionSelection();
			System.out.println("Getting action");
			ArrayList<Classifier> actionSet = predictionComponent.getInstance().generateActionSet(matchedSet, chosenAction);
			environment.executeAction(chosenAction);
			Double p = environment.getReward();
			ArrayList<Classifier> prevActionSet = actionSetLog.get(actionSetLog.size()-1);
			ArrayList<Feature> prevSituation = situationLog.get(situationLog.size()-1);
			if(!prevActionSet.isEmpty()){
				Double predictedReward = rewardLog.get(rewardLog.size() - 1) + LearningParams.getInstance().getDiscountFactor() * maxValueInPA(predictionMap);
				ParameterUpdate.updateSet(prevActionSet, predictedReward);
				RuleDiscovery.runGeneticAlgorithm(prevActionSet, prevSituation, pComponent);
			}
			/*if(rp : eop){
				ParameterUpdate.updateSet(actionSet	, p);
				RuleDiscovery.runGeneticAlgorithm(actionSet, currentSituation, pComponent);
				actionSetLog = new ArrayList<ArrayList<Classifier>>();
			}
			else{*/
			
			actionSetLog.add(actionSet);
			rewardLog.add(p);
			situationLog.add(currentSituation);
			
			
			/*}*/
			
		} while(terminate());
		
	}

	private boolean terminate() {
		return false;
	}

	public long getActualTime() {
		return actualTime;
	}
	
	private Double maxValueInPA(Map<Action, Double> predictionMap){
		Double maxValue = 0.0;
		for(Map.Entry<Action, Double> entry : predictionMap.entrySet()) {
			Double curValue = predictionMap.get(entry);
			if(curValue > maxValue){
				maxValue = curValue;
			}
	     }
		return maxValue;
	}
	
}
