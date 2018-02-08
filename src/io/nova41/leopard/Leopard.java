package io.nova41.leopard;

import java.io.IOException;
import java.util.Arrays;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import io.nova41.leopard.commands.CommandError;
import io.nova41.leopard.commands.CommandHelp;
import io.nova41.leopard.commands.CommandManager;
import io.nova41.leopard.commands.CommandReload;
import io.nova41.leopard.commands.CommandServerInfo;
import io.nova41.leopard.commands.CommandTrain;
import io.nova41.leopard.commands.CommandUsage;
import io.nova41.leopard.commands.CommandVersion;
import io.nova41.leopard.file.DataFolderIniter;
import io.nova41.leopard.file.LocaleManager;
import io.nova41.leopard.listener.PlayerAimRecorder;
import io.nova41.leopard.models.AimDataContainer;
import io.nova41.leopard.models.Dataset;

public class Leopard extends JavaPlugin {

	static {
		ConfigurationSerialization.registerClass(Dataset.class, "Dataset");
	}

	private static final String VERSION = "1.0.0-DEV";

	private PlayerAimRecorder aimRecorder;
	private CommandManager commandManager;
	private LocaleManager localeManager;
	private AimDataContainer dataContainer;
	
	public void onEnable() {
		try {
			DataFolderIniter folderIniter = new DataFolderIniter(this);
			folderIniter.makeFolder(Arrays.asList("category", "locales", "database"));
			folderIniter.releaseFile("en-US.yml", "locales\\en-US.yml", false);
			this.localeManager = new LocaleManager(this, "locales");
		} catch (NullPointerException | IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			this.getLogger().severe("Snowleopard could not load resource files!");
			this.getLogger().severe("Try to remove folders completely in order to solve the problem.");
			this.getLogger().severe("If the error still occur please report stacktraces here: github.com/Nova41/SnowLeopard/issues");
			this.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		this.getLogger().info("Loaded " + localeManager.getLoadedLocales() + " locale(s)");
		this.commandManager = new CommandManager(this, "sl");
		this.commandManager.setLocale(localeManager);
		this.commandManager.setErrorCommand(new CommandError(false));
		this.commandManager.setUsageCommand(new CommandUsage(false));
		//this.commandManager.registerCommand("analysis", new CommandAnalysis(true));
		//this.commandManager.registerCommand("banwave", new CommandBanwave(true));
		this.commandManager.registerCommand("help", new CommandHelp(false));
		//this.commandManager.registerCommand("info", new CommandInfo(false));
		//this.commandManager.registerCommand("rebuild", new CommandRebuild(true));
		this.commandManager.registerCommand("reload", new CommandReload(false));
		this.commandManager.registerCommand("test", new CommandTest(true));
		this.commandManager.registerCommand("serverinfo", new CommandServerInfo(true));
		this.commandManager.registerCommand("train", new CommandTrain(true));
		this.commandManager.registerCommand("version", new CommandVersion(false));
		this.getLogger().info("Snowleopard is now enabled.");
		this.getLogger().info("Check the sources & discussions here: github.com/Nova41/SnowLeopard");
		this.aimRecorder = new PlayerAimRecorder(this);
		this.dataContainer = new AimDataContainer();
		this.aimRecorder.setListen("Unity41", dataContainer);
	}
	
	public LocaleManager getLocales() {
		return this.localeManager;
	}

	public static String getVersion() {
		return VERSION;
	}

}
