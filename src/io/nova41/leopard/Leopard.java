package io.nova41.leopard;

import java.io.IOException;
import java.util.Arrays;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;

import io.nova41.leopard.commands.CommandError;
import io.nova41.leopard.commands.CommandHelp;
import io.nova41.leopard.commands.CommandManager;
import io.nova41.leopard.commands.CommandReload;
import io.nova41.leopard.commands.CommandServerInfo;
import io.nova41.leopard.commands.CommandTest;
import io.nova41.leopard.commands.CommandTrain;
import io.nova41.leopard.commands.CommandUsage;
import io.nova41.leopard.commands.CommandVersion;
import io.nova41.leopard.file.DataFolderIniter;
import io.nova41.leopard.file.LocaleManager;
import io.nova41.leopard.interact.CombatManager;
import io.nova41.leopard.listener.PlayerAimRecorder;
import io.nova41.leopard.models.Dataset;
import io.nova41.leopard.models.LVQNeuronNetwork;

public class Leopard extends JavaPlugin {

	static {
		ConfigurationSerialization.registerClass(Dataset.class, "Dataset");
	}

	private static final String VERSION = "1.0.0-DEV";

	private PlayerAimRecorder	aimRecorder;

	private CombatManager		combatManager;
	private CommandManager		commandManager;
	private LocaleManager		localeManager;

	private LVQNeuronNetwork	network;

	public int					samples_number_of_samples;
	public long 				samples_interval;
	public Double				check_min_check_time;
	public Double				banwave_maximum_selection;
	public Double				nn_max_epoch_delta;
	public Double				nn_init_step;
	public Double				nn_step_del_rate;
	public Boolean				nn_verbose;

	public void onEnable() {
		if (this.getConfig() == null)
			this.saveDefaultConfig();
		this.reloadConfig();
		this.samples_number_of_samples =	this.getConfig().getInt("sample.number-of-samples");
		this.samples_interval =				this.getConfig().getLong("sample.interval");
		this.check_min_check_time =			this.getConfig().getDouble("check.min-check-time");
		this.banwave_maximum_selection =	this.getConfig().getDouble("banwave.maximum-random-analyzes");
		this.nn_max_epoch_delta =			this.getConfig().getDouble("network.max-epoch-delta");
		this.nn_init_step =					this.getConfig().getDouble("network.init-step");
		this.nn_step_del_rate =				this.getConfig().getDouble("network.step-del-rate");
		this.nn_verbose =					this.getConfig().getBoolean("network.verbose-output");
		try {
			DataFolderIniter folderIniter = new DataFolderIniter(this);
			folderIniter.makeFolder(Arrays.asList("category", "locales", "database"));
			folderIniter.releaseFile("en-US.yml", "locales\\en-US.yml", false);
			this.localeManager = new LocaleManager(this, "locales");
		} catch (NullPointerException | IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			this.getLogger().severe("Snowleopard could not load resource files!");
			this.getLogger().severe("Try to remove folders completely in order to solve the problem.");
			this.getLogger().severe(
					"If the error still occur please report stacktraces here: github.com/Nova41/SnowLeopard/issues");
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		this.getLogger().info("Loaded " + localeManager.getLoadedLocales() + " locale(s)");
		this.commandManager = new CommandManager(this, "sl");
		this.commandManager.setLocale(localeManager);
		this.commandManager.setErrorCommand(new CommandError(false));
		this.commandManager.setUsageCommand(new CommandUsage(false));
		// this.commandManager.registerCommand("analysis", new
		// CommandAnalysis(true));
		// this.commandManager.registerCommand("banwave", new
		// CommandBanwave(true));
		this.commandManager.registerCommand("help", new CommandHelp(false));
		// this.commandManager.registerCommand("info", new CommandInfo(false));
		// this.commandManager.registerCommand("rebuild", new
		// CommandRebuild(true));
		this.commandManager.registerCommand("reload", new CommandReload(false));
		this.commandManager.registerCommand("test", new CommandTest(true));
		this.commandManager.registerCommand("serverinfo", new CommandServerInfo(true));
		this.commandManager.registerCommand("train", new CommandTrain(true));
		this.commandManager.registerCommand("version", new CommandVersion(false));
		this.getLogger().info("Constructing network...");
		this.aimRecorder =		new PlayerAimRecorder(this, nn_verbose);
		this.network =			new LVQNeuronNetwork(4, this.nn_init_step, this.nn_step_del_rate);
		this.combatManager =	new CombatManager(this, aimRecorder, network);
		this.getLogger().info("Snowleopard is now enabled.");
		this.getLogger().info("Check the sources & discussions here: github.com/Nova41/SnowLeopard");
	}

	public void onDisable() {
		ProtocolLibrary.getProtocolManager().removePacketListeners(this);
	}
	
	public LocaleManager getLocales() {
		return this.localeManager;
	}

	public CombatManager getCombatManager() {
		return this.combatManager;
	}
	
	public static String getVersion() {
		return VERSION;
	}

}
