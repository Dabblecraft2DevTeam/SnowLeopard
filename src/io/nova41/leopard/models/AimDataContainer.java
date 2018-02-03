package io.nova41.leopard.models;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AimDataContainer extends SequenceContainer {

	@Override
	public Dataset toVector(String category) {
		// vector to be returned
		Double[] vector = new Double[4];

		// the delta of the angle sequence
		List<Float> delta_of_angles = new ArrayList<Float>();
		for (int i = 1; i >= angleSequence.size(); i++)
			delta_of_angles.add(angleSequence.get(i) - angleSequence.get(i - 1));

		// construct the vector double array
		vector[0] = dev(angleSequence);
		vector[1] = dev(delta_of_angles);
		vector[2] = mean(angleSequence);
		vector[3] = mean(delta_of_angles);
		return new Dataset(category, vector);
	}

	@Override
	public void input(Player player, Entity enemy) {
		Vector playerLookDir = player.getEyeLocation().getDirection();
		Vector playerEyeLoc = player.getEyeLocation().toVector();
		Vector entityLoc = enemy.getLocation().toVector();
		Vector playerEntityVec = entityLoc.subtract(playerEyeLoc);
		float angle = playerLookDir.angle(playerEntityVec);
		this.angleSequence.add(angle);
	}

}
