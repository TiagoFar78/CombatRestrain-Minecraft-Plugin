package redehexen.combatRestrain.walls;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import redehexen.combatRestrain.managers.ConfigManager;


public class PlayerBarrier {
	
	private static final Material AIR = Material.AIR;
	
	private List<Location> _barrierBlocks = new ArrayList<Location>();
	
	@SuppressWarnings("deprecation")
	public void placeBarrier(Player player, Location centralBlock, boolean isXAxis) {
		ConfigManager configManager = ConfigManager.getInstance();
		
		Material wallMaterial = configManager.getWallMaterial();
		byte wallDataValue = configManager.getWallDataValue();
		
		for (Location barrierBlock : getBarrierBlocks(centralBlock, isXAxis)) {
			player.sendBlockChange(barrierBlock, wallMaterial, wallDataValue);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void removeBarrier(Player player) {
		for (Location barrierBlock : _barrierBlocks) {
			player.sendBlockChange(barrierBlock, AIR, (byte) 0);
		}
	}
	
	private List<Location> getBarrierBlocks(Location centralBlock, boolean isXAxis) {
		//TODO
		return null;
	}

}
