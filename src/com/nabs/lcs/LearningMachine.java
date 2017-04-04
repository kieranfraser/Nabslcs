package com.nabs.lcs;

import java.util.ArrayList;

import com.nabs.lcs.components.Covering;
import com.nabs.lcs.components.Deletion;
import com.nabs.lcs.components.MatchedSets;
import com.nabs.lcs.components.Matching;
import com.nabs.lcs.components.ParameterUpdate;
import com.nabs.lcs.components.Population;
import com.nabs.lcs.components.Prediction;
import com.nabs.lcs.components.RuleDiscovery;
import com.nabs.lcs.components.Subsumption;
import com.nabs.models.LearningParams;
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
	private long t;
	
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
		this.t = 0;
		learningParams = LearningParams.getInstance();
		// Reinforcement Program rp must be initialized
		
		this.environment = new Environment();
		this.pComponent = new Population();
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
		currentSituation = environment.getNextInstance();
		mComponent.generateMatchSet(pComponent.getPopulation(), currentSituation);
		predictionComponent.generatePredictionArray(mComponent.getMatchedSet());
		
	}
}
