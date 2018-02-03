package io.nova41.leopard.commands;

import org.bukkit.command.CommandSender;

import io.nova41.leopard.Leopard;
import io.nova41.leopard.file.LocaleManager;

public class CommandVersion extends LeopardCommand {

	public CommandVersion(boolean isPlayerOnly) {
		super(isPlayerOnly);
	}

	@Override
	public void perform(LocaleManager locale, CommandSender sender, String[] args) {
		locale.sendMessage(sender, "leopard.command.version", Leopard.getVersion());
	}

}
