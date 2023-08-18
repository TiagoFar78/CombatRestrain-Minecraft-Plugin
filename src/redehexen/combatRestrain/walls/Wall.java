package redehexen.combatRestrain.walls;

import java.util.Hashtable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import redehexen.combatRestrain.managers.ConfigManager;

public class Wall {
	
	private Location _loc1;
	private Location _loc2;
	private Hashtable<Player, PlayerBarrier> playersBarriers = new Hashtable<Player, PlayerBarrier>();
	
	public Wall(Location loc1, Location loc2) {		
		World world = Bukkit.getWorld(loc1.getWorld().getName());
		
		int x1 = loc1.getBlockX();
		int y1 = loc1.getBlockY();
		int z1 = loc1.getBlockZ();
		
		int x2 = loc2.getBlockX();
		int y2 = loc2.getBlockY();
		int z2 = loc2.getBlockZ();
		
		if (x1 > x2) {
			int temp = x1;
			x1 = x2;
			x2 = temp;
		}
		
		if (y1 > y2) {
			int temp = y1;
			y1 = y2;
			y2 = temp;
		}
		
		if (z1 > z2) {
			int temp = z1;
			z1 = z2;
			z2 = temp;
		}
		
		_loc1 = new Location(world, x1, y1, z1);
		_loc2 = new Location(world, x2, y2, z2);
	}
	
//	>--------------------------------------{ Wall }--------------------------------------<
	
	public boolean isCloseToWall(Location loc) {
		int closeDistance = ConfigManager.getInstance().getWallDistanceToShowBlocks();
		
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		return isCloseToBorder(x, _loc1.getBlockX(), false, closeDistance, y) || 
				isCloseToBorder(x, _loc2.getBlockX(), true, closeDistance, y) ||
				isCloseToBorder(z, _loc1.getBlockZ(), false, closeDistance, y) ||
				isCloseToBorder(x, _loc2.getBlockZ(), true, closeDistance, y);
	}
	
	private boolean isCloseToBorder(int cord, int borderCord, boolean isFurtherBorder, int closeDistance, int yCord) {
		if (yCord < _loc1.getBlockY() - closeDistance || yCord > _loc2.getBlockY() + closeDistance) {
			return false;
		}
		
		if (isFurtherBorder) {
			return borderCord - closeDistance <= cord && cord <= borderCord;
		}
		
		return borderCord <= cord && cord <= borderCord + closeDistance;
	}
	
//	>------------------------------------{ Barrier }------------------------------------<
	
	public void addBarrier(Player player) {
		
	}
	
	public void removePlayer(Player player) {
		playersBarriers.remove(player);
	}
	
}
