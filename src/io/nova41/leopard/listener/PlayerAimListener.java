package io.nova41.leopard.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerAimListener implements Listener {
	private List<String> listenedPlayer;

	public PlayerAimListener() {
		this.listenedPlayer = new ArrayList<String>();
	}

	@EventHandler
	public void onPlayerHit() {
		// TODO
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
