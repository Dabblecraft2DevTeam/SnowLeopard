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

import io.nova41.leopard.models.AimData;
import io.nova41.leopard.models.AimDataContainer;

public class PlayerAimRecorder implements Listener {
	private Map<String, AimData> listenedPlayer;

	private JavaPlugin plugin;
	private AimDataContainer dataContainer;
	
	public PlayerAimRecorder(JavaPlugin plugin, AimDataContainer container) {
		this.plugin = plugin;
		this.dataContainer = container;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.listenedPlayer = new HashMap<String, AimData>();
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
				listenedPlayer.put(playername, new AimData());
		else
			listenedPlayer.remove(playername);
	}

	public AimData getData(String playername) {
		return this.listenedPlayer.get(playername);
	}

}
