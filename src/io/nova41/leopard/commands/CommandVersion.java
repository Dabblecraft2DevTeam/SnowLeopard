package io.nova41.leopard.commands;

import org.bukkit.command.CommandSender;

public class CommandVersion extends LeopardCommand {
	
	public CommandVersion(boolean isPlayerOnly) {
		super(isPlayerOnly);
	}

	@Override
	public void perform(CommandSender sender, String[] args) {
		sender.sendMessage("1.0.0");
	}

}
