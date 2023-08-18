package redehexen.combatRestrain.commands;

import org.bukkit.command.CommandSender;

public interface CombatRestrainSubcommand {

	void execute(CommandSender sender, String cmdlabel, String[] args);

}
