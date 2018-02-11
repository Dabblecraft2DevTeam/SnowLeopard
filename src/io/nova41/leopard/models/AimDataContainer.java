package io.nova41.leopard.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AimDataContainer extends SequenceContainer {

	protected List<Float> deltaSequence;

	public AimDataContainer() {
		super();
		this.deltaSequence = new ArrayList<Float>();
	}

	@Override
	public Dataset toVector(String category) {
		if (this.angleSequence.size() == 1)
			return new Dataset(category, this.emptyVector);

		// the vector to be returned
		Double[] vector = new Double[4];

		// construct the vector double array
		vector[0] = dev(angleSequence);
		vector[1] = dev(deltaSequence);
		vector[2] = mean(angleSequence);
		vector[3] = mean(deltaSequence);
		return new Dataset(category, vector);
	}

	@Override
	public void input(Player player, Entity enemy, boolean verboseOutput) {
		Vector playerLookDir = player.getEyeLocation().getDirection();
		Vector playerEyeLoc = player.getEyeLocation().toVector();
		Vector entityLoc = enemy.getLocation().toVector();
		Vector playerEntityVec = entityLoc.subtract(playerEyeLoc);
		float angle = playerLookDir.angle(playerEntityVec);
		if (this.angleSequence.size() > 1) {
			this.deltaSequence.add(Math.abs(angle - angleSequence.get(angleSequence.size() - 1)));
		}
		this.angleSequence.add(angle);
		System.out.println("Current hit angle: " + angle);
	}

}
