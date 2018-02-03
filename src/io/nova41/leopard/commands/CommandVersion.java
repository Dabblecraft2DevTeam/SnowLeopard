package io.nova41.leopard.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import io.nova41.leopard.Leopard;

public class CommandVersion extends LeopardCommand {

	public CommandVersion(boolean isPlayerOnly) {
		super(isPlayerOnly);
	}

	@Override
	public void perform(JavaPlugin plugin, CommandSender sender, String[] args) {
		sender.sendMessage(Leopard.getVersion());
	}

}
