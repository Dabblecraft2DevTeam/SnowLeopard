package io.nova41.leopard.models;

public class LVQPredictResult {
	/**
	 * A class storing the detailed result of prediction generated
	 * by the LVQ neuron network.
	 * @author Nova41
	 * 
	 */
	
	private String bestMatched;
	private double distance;

	public LVQPredictResult(String bestMatched, double distance) {
		this.bestMatched = bestMatched;
		this.distance = distance;
	}
	
	public String getBestMatched() {
		return this.bestMatched;
	}
	
	public double getDistance() {
		return this.distance;
	}
}
