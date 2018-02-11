package io.nova41.leopard.interact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import io.nova41.leopard.Leopard;
import io.nova41.leopard.listener.PlayerAimRecorder;
import io.nova41.leopard.models.AimDataContainer;
import io.nova41.leopard.models.Dataset;
import io.nova41.leopard.models.LVQNeuronNetwork;
import io.nova41.leopard.models.SequenceContainer;

public class CombatManager {

	private Leopard plugin;
	private PlayerAimRecorder recorder;
	private LVQNeuronNetwork network;
	private Map<String, ArrayList<Dataset>> collectedData;
	private Map<String, BukkitTask> runningTasks;

	public CombatManager(Leopard leopard, PlayerAimRecorder aimRecorder, LVQNeuronNetwork network) {
		this.plugin = leopard;
		this.recorder = aimRecorder;
		this.network = network;
		this.collectedData = new HashMap<String, ArrayList<Dataset>>();
		this.runningTasks = new HashMap<String, BukkitTask>();
	}

	public void addNewCategory(String category, String playername, int samplenum, long samples_period_length, boolean verbose) {
		Player p = Bukkit.getPlayer(playername);
		long sample_interval = (long) (20 * samples_period_length);
		this.collectedData.put(playername, new ArrayList<Dataset>());
		BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			int i = 0;
			SequenceContainer container = new AimDataContainer();

			@Override
			public void run() {
				if (i > samplenum) {
					recorder.stopListen(playername);
					plugin.getLocales().sendMessage(p, "leopard.command.train.stop", i++, samplenum);
					runningTasks.get(playername).cancel();
					runningTasks.remove(playername);
					if (verbose)
						for (Dataset data : collectedData.get(playername)) {
							System.out.println(Arrays.asList(data.data));
						}
					return;
				}
				if (recorder.getData(playername) != null) {
					Dataset collected_sample = recorder.getData(playername).toVector(category);
					collectedData.get(playername).add(collected_sample);
				}
				container.clear();
				// new process starts here...
				recorder.setListen(playername, container);
				plugin.getLocales().sendMessage(p, "leopard.command.train.recording", i++, samplenum);
			}
		}, 0, sample_interval);
		this.runningTasks.put(playername, task);
	}

	public boolean isPlayerAvailable(String playername) {
		return !this.recorder.getListeningPlayers().contains(playername);
	}

}
