package io.nova41.leopard.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import io.nova41.leopard.models.PlayerAimData;

public class PlayerAimRecorder implements Listener {
	private Map<String, PlayerAimData> listenedPlayer;

	public PlayerAimRecorder(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.listenedPlayer = new HashMap<String, PlayerAimData>();
	}
	
	
	@EventHandler
	protected void onPlayerHit(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		if (!listenedPlayer.containsKey(e.getEntity().getName()))
			return;
		Player player = (Player) e.getEntity();
		Entity entity = e.getDamager();
		Vector playerLookDir = player.getEyeLocation().getDirection();
		Vector playerEyeLoc = player.getEyeLocation().toVector();
		Vector entityLoc = entity.getLocation().toVector();
		Vector playerEntityVec = entityLoc.subtract(playerEyeLoc);
		float angle = playerLookDir.angle(playerEntityVec);
		System.out.println(angle);
	}
	
	/**
	 * Tell the listener whether the player's aim data should be collected.
	 * 
	 * @param playername
	 * @param listen
	 */

	public void setListen(String playername, boolean listen) {
		if (listen)
			if (listenedPlayer.containsKey(playername))
				return;
			else
				listenedPlayer.put(playername, new PlayerAimData());
		else
			listenedPlayer.remove(playername);
	}
	
	
}
