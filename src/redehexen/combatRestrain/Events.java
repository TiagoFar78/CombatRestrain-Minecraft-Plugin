package redehexen.combatRestrain;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import redehexen.combatRestrain.managers.WallsManager;

public class Events implements Listener {
	
	@EventHandler
	public void playerMove(PlayerMoveEvent e) {
		Location locFrom = e.getFrom();
		Location locTo = e.getTo();
		
		if (locFrom.getBlockX() == locTo.getBlockX() && locFrom.getBlockY() == locTo.getBlockY() &&
				locFrom.getBlockZ() == locTo.getBlockZ()) {
			return;
		}
		
		WallsManager.analyzeMovement(e.getPlayer(), locTo);
	}
	
	@EventHandler
	public void playerTeleport(PlayerTeleportEvent e) {
		WallsManager.analyzeMovement(e.getPlayer(), e.getTo());
	}
	
	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		WallsManager.playerLeftCombat(e.getPlayer());
	}

}
