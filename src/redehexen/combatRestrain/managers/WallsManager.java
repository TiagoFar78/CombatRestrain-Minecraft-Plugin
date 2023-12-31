package redehexen.combatRestrain.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import redehexen.combatRestrain.CombatRestrain;
import redehexen.combatRestrain.walls.Wall;

public class WallsManager {
	
	private static final String WALL_PREFIX = "Walls";
	
	private static List<Wall> walls = populateWalls();
	
	private static List<Wall> populateWalls() {
		YamlConfiguration config = CombatRestrain.getYamlConfiguration();
		
		List<Wall> wallsList = new ArrayList<Wall>();
		
		List<String> wallsPath = config.getKeys(true).stream().filter(p -> p.startsWith(WALL_PREFIX) && p.lastIndexOf(".") == WALL_PREFIX.length()).collect(Collectors.toList());
		
		for (String wallPath : wallsPath) {
			Location loc1 = getLocationFromConfig(config, wallPath, "1");
			Location loc2 = getLocationFromConfig(config, wallPath, "2");
			
			wallsList.add(new Wall(loc1, loc2));
		}
		
		return wallsList;
	}
	
	public static void reload() {
		walls = populateWalls();
	}
	
	private static Location getLocationFromConfig(YamlConfiguration config, String path, String locationIndex) {
		String worldName = config.getString(path + ".World");
		int x = config.getInt(path + ".Loc" + locationIndex + ".X");
		int y = config.getInt(path + ".Loc" + locationIndex + ".Y");
		int z = config.getInt(path + ".Loc" + locationIndex + ".Z");
		
		return new Location(Bukkit.getWorld(worldName), x, y, z);
	}
	
	public static void analyzeMovement(Player player, Location loc) {
		boolean isInCombat = CombatRestrain.getCombatTagPlus().getTagManager().isTagged(player.getUniqueId());
		if (!isInCombat) {
			playerLeftCombat(player);
			return;
		}
		
		for (Wall wall : walls) {
			wall.hideBarrier(player);
			if (wall.isCloseToWall(loc)) {
				wall.showBarrier(player, loc);
			}
		}
	}
	
	public static void playerEnteredCombat(Player player) {
		Location loc = player.getLocation();
		
		Wall closeWall = getCloseWall(loc);
		if (closeWall == null) {
			return;
		}
		
		closeWall.showBarrier(player, loc);
	}
	
	public static void playerLeftCombat(Player player) {		
		for (Wall wall : walls) {
			wall.hideBarrier(player);
			wall.leftCombat(player);
		}
	}
	
	private static Wall getCloseWall(Location loc) {
		for (Wall wall : walls) {
			if (wall.isCloseToWall(loc)) {
				return wall;
			}
		}
		
		return null;
	}

}
