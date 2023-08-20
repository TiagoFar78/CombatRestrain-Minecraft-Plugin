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
	private Hashtable<Player, PlayerBarrier> _playersBarriers = new Hashtable<Player, PlayerBarrier>();
	
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
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		if (!isInsideWalls(x, z)) {
			return false;
		}
		
		int closeDistance = ConfigManager.getInstance().getWallDistanceToShowBlocks();
		
		return isCloseToBorder(x, _loc1.getBlockX(), false, closeDistance, y) || 
				isCloseToBorder(x, _loc2.getBlockX(), true, closeDistance, y) ||
				isCloseToBorder(z, _loc1.getBlockZ(), false, closeDistance, y) ||
				isCloseToBorder(z, _loc2.getBlockZ(), true, closeDistance, y);
	}
	
	private boolean isInsideWalls(int x, int z) {
		return _loc1.getBlockX() <= x && x <= _loc2.getBlockX() && 
				_loc1.getBlockZ() <= z && z <= _loc2.getBlockZ();
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
	
	private Location getCentralBlock(Location loc) {
		int closeDistance = ConfigManager.getInstance().getWallDistanceToShowBlocks();
		
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		System.out.println("Vai tentar fazer o central block");
		System.out.println("Loc1: " + _loc1.getBlockX() + " " + _loc1.getBlockY() + " " + _loc1.getBlockY());
		System.out.println("Loc2: " + _loc2.getBlockX() + " " + _loc2.getBlockY() + " " + _loc2.getBlockY());
		System.out.println("Player: " + x + " " + y + " " + z);
		
		if (isCloseToBorder(x, _loc1.getBlockX(), false, closeDistance, y)) {
			return new Location(loc.getWorld(), x, y, _loc1.getBlockZ());
		}
		
		if (isCloseToBorder(x, _loc2.getBlockX(), true, closeDistance, y)) {
			return new Location(loc.getWorld(), x, y, _loc2.getBlockZ());
		}
		
		if (isCloseToBorder(z, _loc1.getBlockZ(), false, closeDistance, y)) {
			return new Location(loc.getWorld(), _loc1.getBlockX(), y, z);
		}
		
		if (isCloseToBorder(z, _loc2.getBlockZ(), true, closeDistance, y)) {
			return new Location(loc.getWorld(), _loc2.getBlockX(), y, z);
		}
		
		//TODO remove this after tests
		try {
			System.out.println("Devia dar erro lol");
			throw new InstantiationException();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean isXAxis(Location centralBlock) {
		int closeDistance = ConfigManager.getInstance().getWallDistanceToShowBlocks();

		int y = centralBlock.getBlockY();
		int z = centralBlock.getBlockZ();
		
		return isCloseToBorder(z, _loc1.getBlockZ(), false, closeDistance, y) ||
				isCloseToBorder(z, _loc2.getBlockZ(), true, closeDistance, y);
	}
	
	private int getFixedCord(Location centralBlock) {
		int x = centralBlock.getBlockX();
		int z = centralBlock.getBlockZ();
		
		if (x == _loc1.getBlockX()) {
			return _loc1.getBlockZ();
		}
		
		if (x == _loc2.getBlockX()) {
			return _loc2.getBlockZ();
		}
		
		if (z == _loc1.getBlockZ()) {
			return _loc1.getBlockX();
		}
		
		if (z == _loc2.getBlockZ()) {
			return _loc2.getBlockX();
		}
		
		//TODO remove this after tests
		try {
			throw new InstantiationException();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
//	>------------------------------------{ Barrier }------------------------------------<
	
	private void addBarrierToList(Player player, PlayerBarrier barrier) {
		_playersBarriers.put(player, barrier);
	}
	
	private void removeBarrierFromList(Player player) {
		hideBarrier(player);
		
		_playersBarriers.remove(player);
	}
	
	private PlayerBarrier getBarrier(Player player) {
		return _playersBarriers.get(player);
	}
	
	public void hideBarrier(Player player) {
		PlayerBarrier barrier = getBarrier(player);
		if (barrier == null) {
			return;
		}
		
		barrier.removeBarrier(player);
	}
	
	public void showBarrier(Player player, Location loc) {
		PlayerBarrier barrier = getBarrier(player);
		if (barrier == null) {
			barrier = new PlayerBarrier();
			addBarrierToList(player, barrier);
		}

		Location centralBlock = getCentralBlock(loc);
		
		barrier.placeBarrier(player, centralBlock, isXAxis(centralBlock), getFixedCord(centralBlock));
	}
	
//	>-------------------------------------{ Player }-------------------------------------<
	
	public void leftCombat(Player player) {
		removeBarrierFromList(player);
	}
	
}
