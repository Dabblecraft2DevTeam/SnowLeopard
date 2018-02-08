package io.nova41.leopard.commands;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.nova41.leopard.file.LocaleManager;

public class CommandServerInfo extends LeopardCommand {

	public CommandServerInfo(boolean isPlayerOnly) {
		super(isPlayerOnly);
	}

	@Override
	public void perform(Object fromPlugin, LocaleManager locale, CommandSender sender, String[] args) {
		try {
			String server_version = locale.getPlugin().getServer().getVersion();
			String averageTps = averageTps();
			String playerPing = String.valueOf(playerPing((Player) sender));
			locale.sendMessage(sender, "leopard.command.serverinfo", server_version, averageTps, playerPing);
		} catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
			locale.sendMessage(sender, "leopard.command.serverinfo-fail");
		}
	}

	String averageTps() throws NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		String nmsversion = Bukkit.getServer().getClass().getPackage().getName().substring(23);
		Class<?> craftServer;
		craftServer = Class.forName("org.bukkit.craftbukkit." + nmsversion + ".CraftServer");
		Method getServer = craftServer.getMethod("getServer");
		Object nmsServer = getServer.invoke(Bukkit.getServer());
		Field tpsField = nmsServer.getClass().getField("recentTps");
		double[] recentTps = (double[]) tpsField.get(nmsServer);
		double averageTps = (recentTps[0] + recentTps[1] + recentTps[2]) / 3;
		return String.valueOf(averageTps).substring(0, 8);
	}
	
	/**
	 * In fact it returns p.getHandle().playerConnection.player.ping;
	 * 
	 * @param player
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	
	int playerPing(Player player) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException {
	    Method getHandle = player.getClass().getMethod("getHandle");
	    Object nmsPlayer = getHandle.invoke(player);
	    Field conField = nmsPlayer.getClass().getField("playerConnection");
	    Object con = conField.get(nmsPlayer);
	    Field ePlayerField = con.getClass().getField("player");
	    Object ePlayer = ePlayerField.get(con);
	    Field pingField = ePlayer.getClass().getField("ping");
	    int ping = (int) pingField.get(ePlayer);
	    return ping;
	}

}
