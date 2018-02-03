package io.nova41.leopard.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class DataFolderIniter {
	
	/**
	 * DataFolderIniter
	 * 
	 * @author Nova41
	 */
	
	private JavaPlugin plugin;

	public DataFolderIniter(JavaPlugin plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Create the dictionary by the given name.
	 * Useful when the plugin is called for the first time.
	 * @param name
	 */
	public void makeFolder(String name) {
		File folderToMake = new File(plugin.getDataFolder(), name);
		folderToMake.mkdirs();
	}
	
	/**
	 * Create the dictionary by the given name.
	 * Useful when the plugin is called for the first time.
	 * @param names
	 */
	
	public void makeFolder(List<String> names) {
		for (String folder : names)
			makeFolder(folder);
	}
	
	/**
	 * Release specific resource file to the target folder
	 * 
	 * @param resource
	 * @param path
	 * @throws IOException 
	 */
	public void releaseFile(String resource, String path) throws IOException {
		InputStream resourceStream = plugin.getResource(resource);
		File resourceFile = new File(plugin.getDataFolder(), path);
		FileUtils.copyInputStreamToFile(resourceStream, resourceFile);
	}
}
