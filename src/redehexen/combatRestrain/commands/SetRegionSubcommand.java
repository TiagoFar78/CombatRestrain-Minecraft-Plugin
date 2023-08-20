package redehexen.combatRestrain.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import redehexen.combatRestrain.CombatRestrain;
import redehexen.combatRestrain.managers.ConfigManager;

public class SetRegionSubcommand implements CombatRestrainSubcommand {

	@Override
	public void execute(CommandSender sender, String cmdlabel, String[] args) {
		ConfigManager configManager = ConfigManager.getInstance();
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(configManager.getMustBePlayerMessage());
			return;
		}
		
		if (!sender.hasPermission(CombatRestrain.SET_REGION_PERMISSION)) {
			sender.sendMessage(configManager.getNotAllowedMessage());
			return;
		}
		
		if (args.length != 2) {
			sender.sendMessage(configManager.getSetPositionUsageMessage());
			return;
		}
		
		String regionName = args[0];
		int positionIndex = convertToInteger(args[1]);
		if (positionIndex != 1 && positionIndex != 2) {
			sender.sendMessage(configManager.getWrongIndexMessage());
			return;
		}
		
		Location playerLoc = ((Player) sender).getLocation();
		String worldName = playerLoc.getWorld().getName();
		int x = playerLoc.getBlockX();
		int y = playerLoc.getBlockY();
		int z = playerLoc.getBlockZ();
		
		String path = "Walls." + regionName + ".";
		
		YamlConfiguration config = CombatRestrain.getYamlConfiguration();
		
		config.set(path + "World", worldName);
		path += "Loc" + positionIndex + ".";
		config.set(path + "X", x);
		config.set(path + "Y", y);
		config.set(path + "Z", z);
		
		CombatRestrain.saveConfiguration(config);
		
		sender.sendMessage(configManager.getPositionSetMessage());
	}
	
	private int convertToInteger(String arg) {
		try {
			return Integer.parseInt(arg);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

}
