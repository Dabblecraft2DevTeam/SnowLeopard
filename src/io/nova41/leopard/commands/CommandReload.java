package io.nova41.leopard.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import io.nova41.leopard.file.LocaleManager;

public class CommandReload extends LeopardCommand {

	public CommandReload(boolean isPlayerOnly) {
		super(isPlayerOnly);
	}

	@Override
	public void perform(Object fromPlugin, LocaleManager locale, CommandSender sender, String[] args) {
		locale.sendMessage(sender, "leopard.command.reload.before");
		JavaPlugin leopard = locale.getPlugin();
		leopard.getServer().getPluginManager().disablePlugin(leopard);
		leopard.getServer().getPluginManager().enablePlugin(leopard);
		locale.sendMessage(sender, "leopard.command.reload.after");
	}

}
