package io.nova41.leopard.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandMap implements CommandExecutor {
	
	private LeopardCommand defaultCommand;
	private LeopardCommand errorCommand;
	private Map<String, LeopardCommand> commands;
	
	/**
	 * Specifiy the base command of the commandmap.
	 * e.g. The base command of '/game stop' is 'game'
	 * 
	 * @param baseCommand
	 */
	public CommandMap(JavaPlugin plugin, String baseCommand) {
		plugin.getCommand(baseCommand).setExecutor(this);
		this.commands = new HashMap<String, LeopardCommand>();
	}
	
	/**
	 * The command will be executed when there are no arguments
	 * @param executor
	 */
	
	public void setDefaultCommand(LeopardCommand executor) {
		defaultCommand = executor;
	}
	
	public void setErrorCommand(LeopardCommand executor) {
		errorCommand = executor;
	}
	
	public void registerCommand(String name, LeopardCommand executor) {
		this.commands.put(name.toLowerCase(), executor);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
		if (args.length == 0 && this.defaultCommand != null)
			this.defaultCommand.perform(sender, args);
		else if (this.commands.containsKey(cmd.getName().toLowerCase())) {
			this.commands.get(cmd.getName().toLowerCase()).perform(sender, args);
			return true;
		}
		if (this.errorCommand != null) {
			this.errorCommand.perform(sender, args);
			return true;
		}
		return false;
	}

}
