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
	private SequenceContainer dataContainer;
	
	public PlayerAimRecorder(JavaPlugin plugin, SequenceContainer container) {
		this.plugin = plugin;
		this.dataContainer = container;
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
				listenedPlayer.put(playername, new SequenceContainer());
		else
			listenedPlayer.remove(playername);
	}
	
	/**
	 * Get the recorded aim data 
	 * 
	 * @param playername
	 * @return
	 */

	public AimDataContainer getData(String playername) {
		return this.listenedPlayer.get(playername);
	}

}
