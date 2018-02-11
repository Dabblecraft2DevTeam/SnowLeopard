package io.nova41.leopard.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import io.nova41.leopard.Leopard;
import io.nova41.leopard.file.LocaleManager;
import io.nova41.leopard.interact.CombatManager;

public class CommandTrain extends LeopardCommand {

	public CommandTrain(boolean isPlayerOnly) {
		super(isPlayerOnly);
	}

	@Override
	public void perform(Object fromPlugin, LocaleManager locale, CommandSender sender, String[] args) {
		if (args.length != 3) {
			locale.sendMessage(sender, "leopard.command.usage");
			return;
		}
		String category = args[1];
		String playername = args[2];
		if (Bukkit.getPlayer(playername) == null) {
			locale.sendMessage(sender, "leopard.command.train.player-offline");
			return;
		}
		Leopard plugin = (Leopard) fromPlugin;
		CombatManager combatManager = plugin.getCombatManager();
		if (!combatManager.isPlayerAvailable(playername)) {
			locale.sendMessage(sender, "leopard.command.train.player-unavailable");
			return;
		}
		combatManager.addNewCategory(category, playername, sender, plugin.samples_number_of_samples, plugin.samples_interval, plugin.nn_verbose);
	}

}
