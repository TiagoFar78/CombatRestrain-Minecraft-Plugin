package redehexen.combatRestrain.walls;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import redehexen.combatRestrain.managers.ConfigManager;


public class PlayerBarrier {
	
	private static final Material AIR = Material.AIR;
	
	private List<Location> _barrierBlocks = new ArrayList<Location>();
	
	@SuppressWarnings("deprecation")
	public void placeBarrier(Player player, Location centralBlock, boolean isXAxis, int fixedCord) {
		ConfigManager configManager = ConfigManager.getInstance();
		
		Material wallMaterial = configManager.getWallMaterial();
		byte wallDataValue = configManager.getWallDataValue();

		System.out.println("Central block: " + centralBlock.getBlockX() + " " + centralBlock.getBlockY() + 
				" " + centralBlock.getBlockZ());
		System.out.println("blocks list:");
		System.out.println(getBarrierBlocks(centralBlock, isXAxis, fixedCord));
		
		World world = centralBlock.getWorld();
		for (Location barrierBlock : getBarrierBlocks(centralBlock, isXAxis, fixedCord)) {
			if (world.getBlockAt(barrierBlock).getType() != Material.AIR) {
				player.sendBlockChange(barrierBlock, wallMaterial, wallDataValue);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void removeBarrier(Player player) {
		for (Location barrierBlock : _barrierBlocks) {
			player.sendBlockChange(barrierBlock, AIR, (byte) 0);
		}
	}
	
	private List<Location> getBarrierBlocks(Location centralBlock, boolean isXAxis, int fixedCord) {
		int[][] limits = getBarrierLimits(centralBlock, isXAxis);

		int[] widthLimits = limits[0];
		int[] heigthLimits = limits[1];
		
		List<Location> blocksLocations = new ArrayList<Location>();
		
		for (int i = widthLimits[0]; i <= widthLimits[1]; i++) {
			for (int j = heigthLimits[0]; j <= heigthLimits[1]; j++) {
				blocksLocations.add(createBarrierLocation(centralBlock, isXAxis, fixedCord, i, j));
			}
		}
		
		return blocksLocations;
	}
	
	private Location createBarrierLocation(Location centralBlock, boolean isXAxis, int fixedCord, int width, int heigth) {
		if (isXAxis) {
			return new Location(centralBlock.getWorld(), fixedCord, heigth, width);
		}
		
		return new Location(centralBlock.getWorld(), width, heigth, fixedCord);
	}
	
	private int[][] getBarrierLimits(Location centralBlock, boolean isXAxis) {
		ConfigManager configManager = ConfigManager.getInstance();
		
		int wallHeight = configManager.getWallHeight();
		int wallWidth = configManager.getWallWidth();
		
		int blockCord = isXAxis ? centralBlock.getBlockX() : centralBlock.getBlockZ();
		int barrierWidthRadius = (wallWidth - 1) / 2;
		int barrierHeightRadius = (wallHeight - 1) / 2;
		
		int lowerHeigth = centralBlock.getBlockY() - barrierHeightRadius;
		int higherHeigth = centralBlock.getBlockY() + barrierHeightRadius;
		
		int lowerWidth = blockCord - barrierWidthRadius;
		int higherWidth = blockCord + barrierWidthRadius;
		
		int[] heigthLimits = { lowerHeigth , higherHeigth };
		int[] widthLimits = { lowerWidth , higherWidth };
		int[][] limits = { widthLimits , heigthLimits };
		
		return limits;
	}

}
