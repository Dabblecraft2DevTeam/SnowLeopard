package io.nova41.leopard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import io.nova41.leopard.commands.CommandManager;
import io.nova41.leopard.commands.CommandVersion;
import io.nova41.leopard.file.DataFolderIniter;
import io.nova41.leopard.file.LocaleManager;
import io.nova41.leopard.listener.PlayerAimRecorder;
import io.nova41.leopard.models.Dataset;
import io.nova41.leopard.models.LVQNeuronNetwork;

public class Leopard extends JavaPlugin {

	static {
		ConfigurationSerialization.registerClass(Dataset.class, "Dataset");
	}

	private static final String VERSION = "1.0.0-DEV";

	private PlayerAimRecorder aimRecorder;
	private CommandManager commandManager;
	private LocaleManager localeManager;

	public void onEnable() {
		try {
			DataFolderIniter folderIniter = new DataFolderIniter(this);
			folderIniter.makeFolder(Arrays.asList("category", "locales", "database"));
			this.commandManager = new CommandManager(this, "sl");
			this.commandManager.registerCommand("version", new CommandVersion(false));
			this.localeManager = new LocaleManager(this, "locales");
		} catch (NullPointerException | IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			this.getLogger().severe("Snowleopard could not load resource files!");
			this.getLogger().severe("Try to remove folders completely in order to solve the problem.");
			this.getLogger().severe("If the error still occur please report stacktraces here: github.com/Nova41/SnowLeopard/issues");
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		this.getLogger().info("Snowleopard is now enabled.");
		this.getLogger().info("Check the sources & discussions here: github.com/Nova41/SnowLeopard");
		this.aimRecorder = new PlayerAimRecorder(this);
		this.aimRecorder.stopListen("Unity41");
	}
	
	public LocaleManager getLocales() {
		return this.localeManager;
	}

	public static String getVersion() {
		return VERSION;
	}
	
	// For testing purposes.
	public static void main(String[] args) {
		LVQNeuronNetwork lvq = new LVQNeuronNetwork(4, 0.5, 0.95);

		Double[][] train_killaura = readDataset("E:/Killaura");
		for (Double[] line : train_killaura) {
			lvq.input(new Dataset("killaura", line));
		}
		Double[][] train_vanilla = readDataset("E:/Vanilla");
		for (Double[] line : train_vanilla) {
			lvq.input(new Dataset("vanilla", line));
		}
		lvq.print_inputlayers();

		System.out.println(">> Normalizing input layers");
		lvq.normalize();
		lvq.print_inputlayers();

		lvq.initialize();
		lvq.print_outputlayers();

		System.out.println(">> Training Neuron Network... Trained " + lvq.trainUntil(1E-10) + " times");
		lvq.print_outputlayers();

		System.out.println("Predicting the category of a new dataset according to the trained neuron network");
		System.out.println("  Killaura categorized:\t" + lvq.predict(
				new Double[] { 0.14148080214650174, 0.11398027697664276, 0.23061594367027283, 0.11088762416139893 })
				.getBestMatched());
		System.out.println("  Vanilla categorized:\t" + lvq.predict(
				new Double[] { 0.20942507828179005, 0.13824867503629082, 0.37103211879730225, 0.1600528048017086 })
				.getBestMatched());

		System.out.println("... end ... Snowleopard powered LVQ neuron network ...");
	}

	// For testing purposes.
	public static Double[][] readDataset(String path) {
		try {
			int features = 0;
			String file = FileUtils.readFileToString(new File(path));
			List<Double[]> datasets = new ArrayList<Double[]>();

			for (String line : file.split("\n")) {
				List<Double> oneline = new ArrayList<Double>();
				for (String feature : line.split(" ")) {
					features = line.split(" ").length;
					oneline.add(Double.valueOf(feature));
				}
				Double[] buf = new Double[oneline.size()];
				datasets.add(oneline.toArray(buf));
				oneline.clear();
			}

			Double[][] buf = new Double[datasets.size()][features];
			datasets.toArray(buf);
			return buf;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
