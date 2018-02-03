package io.nova41.leopard.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import io.nova41.leopard.file.LocaleManager;

public abstract class LeopardCommand {
	
	protected boolean playerOnly;
	
	public LeopardCommand(boolean isPlayerOnly) {
		this.playerOnly = isPlayerOnly;
	}
	
	public boolean isPlayerOnly() {
		return this.playerOnly;
	}

	public abstract void perform(LocaleManager locale, CommandSender sender, String[] args);
}
