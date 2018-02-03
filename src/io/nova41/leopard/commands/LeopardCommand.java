package io.nova41.leopard.commands;

import org.bukkit.command.CommandSender;

public abstract class LeopardCommand {
	
	protected boolean playerOnly;
	
	public LeopardCommand(boolean isPlayerOnly) {
		this.playerOnly = isPlayerOnly;
	}
	
	public boolean isPlayerOnly() {
		return this.playerOnly;
	}

	public abstract void perform(CommandSender sender, String[] args);
}
