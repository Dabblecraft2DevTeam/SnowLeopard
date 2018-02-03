package io.nova41.leopard.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.fusesource.jansi.Ansi;

public class CommandManager implements CommandExecutor {

	private LeopardCommand errorCommand;
	private LeopardCommand usageCommand;
	private Map<String, LeopardCommand> commands;

	/**
	 * Specifiy the base command of the commandmap. e.g. The base command of
	 * '/game stop' is 'game'
	 * 
	 * @param baseCommand
	 */
	public CommandManager(JavaPlugin plugin, String baseCommand) {
		plugin.getCommand(baseCommand).setExecutor(this);
		this.commands = new HashMap<String, LeopardCommand>();
	}

	/**
	 * The command will be executed when there are no arguments
	 * 
	 * @param executor
	 */

	public void setDefaultCommand(LeopardCommand executor) {
		usageCommand = executor;
	}

	public void setErrorCommand(LeopardCommand executor) {
		errorCommand = executor;
	}

	public void registerCommand(String name, LeopardCommand executor) {
		this.commands.put(name.toLowerCase(), executor);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if (args.length == 0) {
			if (usageCommand != null)
				this.usageCommand.perform(sender, args);
		} else {
			String subcommand = args[0];
			if (commands.containsKey(subcommand.toLowerCase())) {
				LeopardCommand executor = commands.get(subcommand.toLowerCase());
				if (sender instanceof Player)
					executor.perform(sender, args);
				else if (executor.isPlayerOnly() == false)
					executor.perform(sender, args);
				else if (errorCommand != null)
					errorCommand.perform(sender, args);
				else
					return false;
			}
			return true;
		}
		if (usageCommand != null) {
			usageCommand.perform(sender, args);
			return true;
		}
		return false;
	}

}
