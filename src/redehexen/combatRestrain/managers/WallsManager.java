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
		// TODO check if player is in combat and return if not
		
		for (Wall wall : walls) {
			if (wall.isCloseToWall(loc)) {
				// TODO ativar os vidros para o jogador
				return;
			}
		}
	}

}
