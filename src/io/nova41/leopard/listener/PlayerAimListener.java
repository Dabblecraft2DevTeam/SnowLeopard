package io.nova41.leopard.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;
import org.encanta.mc.ac.AimDataManager;
import org.encanta.mc.ac.AimDataSeries;

public class PlayerAimListener implements Listener {
	private List<String> listenedPlayer;

	public PlayerAimListener() {
		this.listenedPlayer = new ArrayList<String>();
	}

	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		if (!listenedPlayer.contains(e.getEntity().getName()))
			return;
		Player player = (Player) e.getEntity();
		Entity entity = e.getDamager();
		Vector playerLookDir = player.getEyeLocation().getDirection();
		Vector playerEyeLoc = player.getEyeLocation().toVector();
		Vector entityLoc = entity.getLocation().toVector();
		Vector playerEntityVec = entityLoc.subtract(playerEyeLoc);
		float angle = playerLookDir.angle(playerEntityVec);

	}
	
	/**
	 * Tell the listener whether the player's aim data should be collected.
	 * 
	 * @param playername
	 * @param listen
	 */

	public void setListen(String playername, boolean listen) {
		if (listen)
			if (listenedPlayer.contains(playername))
				return;
			else
				listenedPlayer.add(playername);
		else
			listenedPlayer.remove(playername);
	}
}
