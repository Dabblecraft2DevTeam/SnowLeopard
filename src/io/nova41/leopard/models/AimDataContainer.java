package io.nova41.leopard.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AimDataContainer extends SequenceContainer {

	private List<Float> angleSequence;
	private List<Float> deltaSequence;

	public AimDataContainer() {
		this.angleSequence = new ArrayList<Float>();
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
		if (verboseOutput) {
			Bukkit.getLogger()
					.info("[SnowLeopard Clickdetector] DetectedClick{damager=" + player.getName() + ",entity=" + enemy.getName() + ",entityID="
							+ enemy.getEntityId() + ",damager_loc=" + player.getLocation() + ",entity_loc="
							+ enemy.getLocation() + ",ANGLE_abs=" + angle + "}");
		}
	}

	@Override
	public void clear() {
		this.angleSequence.clear();
		this.deltaSequence.clear();
	}

}
