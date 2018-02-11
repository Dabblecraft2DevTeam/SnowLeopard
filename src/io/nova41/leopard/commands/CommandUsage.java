package io.nova41.leopard.commands;

import org.bukkit.command.CommandSender;

import io.nova41.leopard.file.LocaleManager;

public class CommandUsage extends LeopardCommand {

	public CommandUsage(boolean isPlayerOnly) {
		super(isPlayerOnly);
	}

	@Override
	public void perform(Object fromPlugin, LocaleManager locale, CommandSender sender, String[] args) {
		locale.sendMessage(sender, "leopard.command.usage");
	}

}
