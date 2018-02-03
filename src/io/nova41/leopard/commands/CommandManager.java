package io.nova41.leopard.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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
		if (args.length == 0 && usageCommand != null)
			this.usageCommand.perform(sender, args);
		else if (commands.containsKey(cmd.getName().toLowerCase())) {
			LeopardCommand executor = commands.get(cmd.getName().toLowerCase());
			if (sender instanceof Player)
				executor.perform(sender, args);
			else if (executor.isPlayerOnly() == false)
				if (errorCommand != null)
					errorCommand.perform(sender, args);
				else
					return false;
			return true;
		}
		if (usageCommand != null) {
			usageCommand.perform(sender, args);
			return true;
		}
		return false;
	}

}
