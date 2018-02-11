package io.nova41.leopard.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
		this.listenedPlayer.get(e.getDamager().getName()).input(player, entity);
	}

	public void setListen(String playername, SequenceContainer containerOfPlayer) {
		this.listenedPlayer.put(playername, containerOfPlayer);
	}
	
	public void stopListen(String playername) {
		this.listenedPlayer.remove(playername);
	}
	
	public Set<String> getListeningPlayers() {
		return this.listenedPlayer.keySet();
	}
	
	public SequenceContainer getData(String playername) {
		return this.listenedPlayer.get(playername);
	}
	
}
