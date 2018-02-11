package io.nova41.leopard.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class SequenceContainer {

	protected final Double[] emptyVector = new Double[] {};
	protected List<Float> angleSequence;

	public SequenceContainer() {
		angleSequence = new ArrayList<Float>();
	}

	public abstract Dataset toVector(String category);

	public abstract void input(Player player, Entity enemy, boolean verboseOutput);

	protected double mean(List<Float> list) {
		double sum = 0;
		Iterator<Float> iter = list.iterator();
		while (iter.hasNext())
			sum += iter.next();
		return sum / list.size();
	}

	protected double dev(List<Float> list) {
		double devsum = 0;
		double mean = mean(list);
		Iterator<Float> iter = list.iterator();
		while (iter.hasNext())
			devsum += Math.pow(iter.next() - mean, 2);
		return devsum / list.size();
	}
}
