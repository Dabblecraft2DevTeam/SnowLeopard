package io.nova41.leopard.interact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
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

	public void addNewCategory(String category, String fromPlayer, CommandSender callback, int samplenum,
			long samples_period_length, boolean verboseOutput) {
		long sample_interval = (long) (20 * samples_period_length);
		this.collectedData.put(fromPlayer, new ArrayList<Dataset>());
		BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
			int i = 0;
			SequenceContainer container = new AimDataContainer();

			@Override
			public void run() {
				if (i > samplenum) {
					recorder.stopListen(fromPlayer);
					plugin.getLocales().sendMessage(callback, "leopard.command.train.stop", i++, samplenum);
					runningTasks.get(fromPlayer).cancel();
					runningTasks.remove(fromPlayer);
					if (verboseOutput)
						for (Dataset data : collectedData.get(fromPlayer)) {
							System.out.println("Final vector: " + Arrays.asList(data.data));
						}
					saveSample(category, fromPlayer, callback);
					return;
				}
				if (recorder.getData(fromPlayer) != null) {
					Dataset collected_sample = recorder.getData(fromPlayer).toVector(category);
					collectedData.get(fromPlayer).add(collected_sample);
				}
				container.clear();
				// new process starts here...
				recorder.setListen(fromPlayer, container);
				plugin.getLocales().sendMessage(callback, "leopard.command.train.recording", i++, samplenum);
			}
		}, 0, sample_interval);
		this.runningTasks.put(fromPlayer, task);
	}

	public boolean isPlayerAvailable(String playername) {
		return !this.recorder.getListeningPlayers().contains(playername);
	}

	private void saveSample(String category, String playername, CommandSender callback) {
		plugin.getLocales().sendMessage(callback, "leopard.command.train.saving", category);
		
		plugin.getLocales().sendMessage(callback, "leopard.command.train.saved", category);
	}
}
