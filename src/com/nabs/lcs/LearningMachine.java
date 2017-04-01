package com.nabs.lcs;

import com.nabs.lcs.components.Covering;
import com.nabs.lcs.components.Deletion;
import com.nabs.lcs.components.MatchedSets;
import com.nabs.lcs.components.Matching;
import com.nabs.lcs.components.ParameterUpdate;
import com.nabs.lcs.components.Population;
import com.nabs.lcs.components.RuleDiscovery;
import com.nabs.lcs.components.Subsumption;
import com.nabs.models.LearningParams;

/**
 * Main XCS algorithm 
 * @author kfraser
 *
 */
public class LearningMachine {

	private static LearningMachine instance;
	
	private LearningParams learningParams;
	
	/**
	 * Actual time.
	 */
	private long t;
	
	private Population pComponent;
	private Matching mComponent;
	private MatchedSets icComponent;
	private Covering cComponent;
	private ParameterUpdate updateComponent;
	private Subsumption subComponent;
	private RuleDiscovery rdComponent;
	private Deletion delCompent;
	
	public static synchronized LearningMachine getInstance(){
		if(instance==null){
			instance = new LearningMachine();
		}
		return instance;
	}
	
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
	private LearningMachine(){
		learningParams = LearningParams.getInstance();
		
	}
	
	/**
	 * Start the algorithm with the current params.
	 */
	public void startMachine(){
		
	}
}
