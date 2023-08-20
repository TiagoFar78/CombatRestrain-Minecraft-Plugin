package redehexen.combatRestrain.commands;

import org.bukkit.command.CommandSender;

import redehexen.combatRestrain.CombatRestrain;
import redehexen.combatRestrain.managers.ConfigManager;
import redehexen.combatRestrain.managers.WallsManager;

public class ReloadSubcommand implements CombatRestrainSubcommand {

	@Override
	public void execute(CommandSender sender, String cmdlabel, String[] args) {
		ConfigManager configManager = ConfigManager.getInstance();
		
		if (!sender.hasPermission(CombatRestrain.SET_REGION_PERMISSION)) {
			sender.sendMessage(configManager.getNotAllowedMessage());
			return;
		}
		
		ConfigManager.reload();
		WallsManager.reload();
		
		sender.sendMessage(configManager.getReloadedMessage());
	}

}
