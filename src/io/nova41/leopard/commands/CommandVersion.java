package io.nova41.leopard.commands;

import org.bukkit.command.CommandSender;

public class CommandVersion extends LeopardCommand {

	public CommandVersion(boolean isPlayerOnly) {
		super(isPlayerOnly);
	}

	@Override
	public boolean isPlayerOnly() {
		return true;
	};

	@Override
	public void perform(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub

	}

}
