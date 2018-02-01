package io.nova41.leopard.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import io.nova41.leopard.listener.PlayerAimListener;
import io.nova41.leopard.models.PlayerAimData;

public class AimRecorder {
	private PlayerAimListener aimListener;
	private Map<String, PlayerAimData> playerdataMap;

	public AimRecorder(PlayerAimListener aimListener) {
		this.playerdataMap = new HashMap<String, PlayerAimData>();
	}

	public PlayerAimData getData(String playername) {
		return this.playerdataMap.get(playername);
	}

	/**
	 * If listen = true, new aim data will be constantly written to
	 * corresponding PlayerAimData.
	 * 
	 * @param playername
	 * @param listen
	 */
	public void setListen(String playername, boolean listen) {
		if (Bukkit.getPlayer(playername) == null)
			return;
		aimListener.setListen(playername, listen);
	}
}
