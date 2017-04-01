package com.nabs.models;

/**
 * Learning parameters of the XCS algorithm as found 
 * in Butz and Wilson - "An Algorithm Description of XCS"
 * @author kfraser
 *
 */
public class LearningParams {

	/**
	 * specifies the maximum size of the population (in micro-classifiers, i.e., N
	 * is the sum of the classifier numerosities).  should be large enough so that, 
	 * starting from an empty population, covering occurs only at the very beginning
	 * of a run.
	 */
	private long maxPopulation; 
	
	/**
	 * β is the learning rate for p, e, f, and as. could be in the range 0.1-0.2
	 */
	private double learningRate;
	
	/**
	 * used in calculating the fitness of a classifier.  normally 0.1
	 */
	private double alpha;
	
	/**
	 * used in calculating the fitness of a classifier. is the error 
	 * below which classifiers are considered to have equal accuracy;
	 * a typical value would be about one percent of the maximum value of 
	 * ρ, e.g., 10 if the maximum value is 1000
	 */
	private long epsilon;
	
	/**
	 * used in calculating the fitness of a classifier.  power parameter ν is typically
	 * 5
	 */
	private long v;
	
	/**
	 * gamma - discount factor used – in multi-step problems – in updating classifier
	 * predictions .has been 0.71 in many 
	 * problems in the literature, but larger or smaller values could certainly 
	 * work, depending on the environment
	 */
	private double discountFactor;
	
	/**
	 * thetaGA - The GA is applied in a set when the average time
	 * since the last GA in the set is greater than thetaGA. often in the range 25-50
	 */
	private long gaThreshold;
	
	/**
	 * X the probability of applying crossover in the GA. in the range 0.5-1.0
	 */
	private double crossoverProbability;
	
	/**
	 * mu - specifies the probability of mutating an allele in the offspring.
	 * in the range 0.01-0.05 are often used
	 */
	private double mutationProbability;
	
	/**
	 * thetaDel - is the deletion threshold. If the experience of a classifier is greater than
	 * thetaDel, its fitness may be considered in its probability of deletion.
	 * could be about 20
	 */
	private long deletionThreshold;
	
	/**
	 *  delta - specifies the fraction of the mean fitness in [P] below which 
	 *  the fitness of a classifier may be considered in its probability of deletion.
	 *  often taken to be 0.1.
	 */
	private double fitnessFractionProbDeletion;
	
	/**
	 * thetaSubsumption - the subsumption threshold. The experience of a
	 *  classifier must be greater than θsub in order to be able to subsume 
	 *  another classifier. could be about 20, though larger values (more 
	 *  experience) are important in some problems
	 */
	private long subsumptionThreshold;
	
	/**
	 * P hash = the probability of using a wildcard in one attribute in C when covering.
	 * could be around 0.33. Larger values reduce the need for covering, but may make
	 *  it harder to evolve accurate classifiers
	 */
	private double wildcardCoveringProbability;
	
	/**
	 * p i - used as initial values in classifiers. should be taken very 
	 * small – essentially zero
	 */
	private double pI;
	
	/**
	 * e i - used as initial values in classifiers. should be taken very 
	 * small – essentially zero
	 */
	private double eI;
	
	/**
	 * f i - used as initial values in classifiers. should be taken very 
	 * small – essentially zero
	 */
	private double fI;
	
	/**
	 * specifies the probability during action selection of choosing the action
	 * uniform randomly. exploration probability pexplr could be 0.5.
	 * but this depends on the type of experiment contemplated
	 */
	private double pexplr;
	
	/**
	 * theta mna - specifies the minimal number of actions that must be present in a
	 * match set [M], or else covering will occur.
	 * To cause covering to provide classifiers for every action, choose theta mna
	 * equal to the number of available actions
	 */
	private long minActions;
	
	/**
	 * Boolean parameter that specifies if offspring are to
	 * be tested for possible logical subsumption by parents
	 * depends on the problem. In general, subsumption is used to
	 * eliminate classifiers that clearly add nothing to the system’s decision
	 * capability. In the case of GA subsumption, these are offspring classifiers
	 * whose conditions are logically subsumed by a parent’s condition, 
	 * given that the parent is both accurate and sufficiently experienced. 
	 */
	private boolean doGASubsumption;
	
	/**
	 * Boolean parameter that specifies if action sets
	 * are to be tested for subsuming classifiers.
	 * In the case of action set subsumption, a general, accurate, and
	 * experienced classifier in an action set eliminates classifiers in 
	 * the set that its condition logically subsumes. action set subsumption 
	 * appears to be ‘stronger’: it causes greater ‘condensation’ of the population
	 */
	private boolean doActionSetSubsumption;
	
	private static LearningParams learningParams;
	
	/**
	 * Get the current learningParams instance if exists, otherwise create
	 * a default instance.
	 * @return
	 */
	public static synchronized LearningParams getInstance(){
		if(learningParams==null){
			learningParams = new LearningParams();
		}
		return learningParams;
	}
	
	/**
	 * Initialize the learning parameters to default values.
	 */
	private LearningParams(){
		this.maxPopulation = 100;
		this.learningRate = 0.1;
		this.alpha = 0.1;
		this.epsilon = 10;
		this.v = 5;
		this.discountFactor = 0.71;
		this.gaThreshold = 25;
		this.crossoverProbability = 0.5;
		this.mutationProbability = 0.01;
		this.deletionThreshold = 20;
		this.fitnessFractionProbDeletion = 0.1;
		this.subsumptionThreshold = 20;
		this.wildcardCoveringProbability = 0.33;
		this.pI = 0.001;
		this.eI = 0.001;
		this.fI = 0.001;
		this.pexplr = 0.5;
		this.minActions = 5;
		this.doGASubsumption = false;
		this.doActionSetSubsumption = false;
	}
		
	
	/**
	 * Getters and Setters
	 */

	public long getMaxPopulation() {
		return maxPopulation;
	}

	public void setMaxPopulation(long maxPopulation) {
		this.maxPopulation = maxPopulation;
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public long getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(long epsilon) {
		this.epsilon = epsilon;
	}

	public long getV() {
		return v;
	}

	public void setV(long v) {
		this.v = v;
	}

	public double getDiscountFactor() {
		return discountFactor;
	}

	public void setDiscountFactor(double discountFactor) {
		this.discountFactor = discountFactor;
	}

	public long getGaThreshold() {
		return gaThreshold;
	}

	public void setGaThreshold(long gaThreshold) {
		this.gaThreshold = gaThreshold;
	}

	public double getCrossoverProbability() {
		return crossoverProbability;
	}

	public void setCrossoverProbability(double crossoverProbability) {
		this.crossoverProbability = crossoverProbability;
	}

	public double getMutationProbability() {
		return mutationProbability;
	}

	public void setMutationProbability(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	public long getDeletionThreshold() {
		return deletionThreshold;
	}

	public void setDeletionThreshold(long deletionThreshold) {
		this.deletionThreshold = deletionThreshold;
	}

	public double getFitnessFractionProbDeletion() {
		return fitnessFractionProbDeletion;
	}

	public void setFitnessFractionProbDeletion(double fitnessFractionProbDeletion) {
		this.fitnessFractionProbDeletion = fitnessFractionProbDeletion;
	}

	public long getSubsumptionThreshold() {
		return subsumptionThreshold;
	}

	public void setSubsumptionThreshold(long subsumptionThreshold) {
		this.subsumptionThreshold = subsumptionThreshold;
	}

	public double getWildcardCoveringProbability() {
		return wildcardCoveringProbability;
	}

	public void setWildcardCoveringProbability(double wildcardCoveringProbability) {
		this.wildcardCoveringProbability = wildcardCoveringProbability;
	}

	public double getpI() {
		return pI;
	}

	public void setpI(double pI) {
		this.pI = pI;
	}

	public double geteI() {
		return eI;
	}

	public void seteI(double eI) {
		this.eI = eI;
	}

	public double getfI() {
		return fI;
	}

	public void setfI(double fI) {
		this.fI = fI;
	}

	public double getPexplr() {
		return pexplr;
	}

	public void setPexplr(double pexplr) {
		this.pexplr = pexplr;
	}

	public long getMinActions() {
		return minActions;
	}

	public void setMinActions(long minActions) {
		this.minActions = minActions;
	}

	public boolean isDoGASubsumption() {
		return doGASubsumption;
	}

	public void setDoGASubsumption(boolean doGASubsumption) {
		this.doGASubsumption = doGASubsumption;
	}

	public boolean isDoActionSetSubsumption() {
		return doActionSetSubsumption;
	}

	public void setDoActionSetSubsumption(boolean doActionSetSubsumption) {
		this.doActionSetSubsumption = doActionSetSubsumption;
	}
	
}
