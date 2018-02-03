package io.nova41.leopard.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigFile {

	private YamlConfiguration yamlConfig;

	public ConfigFile(JavaPlugin plugin, String filename)
			throws FileNotFoundException, IOException, InvalidConfigurationException {
		File config = new File(plugin.getDataFolder(), filename);
		yamlConfig = new YamlConfiguration();
		yamlConfig.load(config);
	}

	public FileConfiguration getConfig() {
		return this.yamlConfig;
	}

}
