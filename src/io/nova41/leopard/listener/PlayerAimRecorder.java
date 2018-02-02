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

import io.nova41.leopard.models.AimDataContainer;
import io.nova41.leopard.models.SequenceContainer;

public class PlayerAimRecorder implements Listener {
	
	/**
	 * The job of this class is to calculate angles on every hit
	 * and write them to the container.
	 */
	
	private Map<String, SequenceContainer> listenedPlayer;

	private JavaPlugin plugin;
	
	public PlayerAimRecorder(JavaPlugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.listenedPlayer = new HashMap<String, SequenceContainer>();
	}

	@EventHandler
	protected void onPlayerHit(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player))
			return;
		if (!listenedPlayer.containsKey(e.getDamager().getName()))
			return;
		Player player = (Player) e.getDamager();
		Entity entity = e.getEntity();
		Vector playerLookDir = player.getEyeLocation().getDirection();
		Vector playerEyeLoc = player.getEyeLocation().toVector();
		Vector entityLoc = entity.getLocation().toVector();
		Vector playerEntityVec = entityLoc.subtract(playerEyeLoc);
		float angle = playerLookDir.angle(playerEntityVec);
		System.out.println(angle);
	}

	public void setListen(String playername, SequenceContainer containerOfPlayer) {
		this.listenedPlayer.put(playername, containerOfPlayer);
	}
	
	public void stopListen(String playername) {
		this.listenedPlayer.remove(playername);
	}
	
	public SequenceContainer getData(String playername) {
		return this.listenedPlayer.get(playername);
	}
	
}
