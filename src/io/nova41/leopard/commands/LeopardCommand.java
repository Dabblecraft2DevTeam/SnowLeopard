package io.nova41.leopard.commands;

import org.bukkit.command.CommandSender;

public interface LeopardCommand {
	public boolean isPlayerOnly();

	public void perform(CommandSender sender, String[] args);
}
