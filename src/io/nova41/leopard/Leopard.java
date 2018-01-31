package io.nova41.leopard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import io.nova41.leopard.models.LVQNeuronNetwork;

public class Leopard extends JavaPlugin{
	
	// For testing purposes.
	public static void main(String[] args) {
		LVQNeuronNetwork lvq = new LVQNeuronNetwork(4, 0.5, 0.95);

		Double[][] train_killaura = readDataset("E:/Killaura");
		for (Double[] line : train_killaura) {
			lvq.input("killaura", line);
		}
		Double[][] train_vanilla = readDataset("E:/Vanilla");
		for (Double[] line : train_vanilla) {
			lvq.input("vanilla", line);
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
				new Double[] { 0.14148080214650174, 0.11398027697664276, 0.23061594367027283, 0.11088762416139893 }).getBestMatched());
		System.out.println("  Vanilla categorized:\t" + lvq.predict(
				new Double[] { 0.20942507828179005, 0.13824867503629082, 0.37103211879730225, 0.1600528048017086 }).getBestMatched());
		
		System.out.println("... end ... Snowleopard powered LVQ neuron network ...");
	}
	
	// For testing purposes.
	public static Double[][] readDataset(String path) {
		try {
			int features = 0;
			String file = FileUtils.readFileToString(new File(path));
			List<Double[]> datasets = new ArrayList<Double[]>();

			for (String line : file.split("\n")) {
				//System.out.print("x");
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
