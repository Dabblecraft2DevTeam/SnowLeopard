package io.nova41.leopard.models;

import java.util.Iterator;
import java.util.List;

public abstract class AimDataContainer {

	private List<Float> angleSequence;

	public abstract Dataset toVector();

	public void add(Float angle) {
		this.angleSequence.add(angle);
	}

	protected double mean(List<Float> list) {
		double sum = 0;
		Iterator<Float> iter = list.iterator();
		while (iter.hasNext())
			sum += iter.next();
		return sum / list.size();
	}
	
	protected double dev(List<Float> list) {
		double dev = 0;
		double mean = mean(list);
		Iterator<Float> iter = list.iterator();
		while (iter.hasNext())
			dev += Math.pow(iter.next() - mean, 2);
		return dev;
	}
}
