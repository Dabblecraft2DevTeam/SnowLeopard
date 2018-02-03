package io.nova41.leopard.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class LocaleManager {

	private JavaPlugin plugin;
	private Map<String, ConfigFile> messageFiles;

	public LocaleManager(JavaPlugin plugin, String message_folder_name)
			throws FileNotFoundException, IOException, InvalidConfigurationException {
		this.plugin = plugin;
		this.messageFiles = new HashMap<String, ConfigFile>();

		// Read all configs from the message folder
		File messageFolder = new File(plugin.getDataFolder(), message_folder_name);
		for (File messageFile : messageFolder.listFiles()) {
			ConfigFile messageConfig = new ConfigFile(plugin,
					message_folder_name + File.separator + messageFile.getName());
			messageFiles.put(messageConfig.getConfig().getString("locale.name"), messageConfig);
		}
	}

	public int getLoadedLocales() {
		return this.messageFiles.size();
	}

	public void sendMessage(CommandSender sender, String path, Object... arg) {
		String message = MessageFormat.format(messageFiles.get("en-US").getConfig().getString(path), arg);
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public JavaPlugin getPlugin() {
		return this.plugin;
	}

}
