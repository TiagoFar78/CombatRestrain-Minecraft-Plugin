package redehexen.combatRestrain;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.minelink.ctplus.CombatTagPlus;
import redehexen.combatRestrain.commands.CombatRestrainCommand;

public class CombatRestrain extends JavaPlugin {
	
	public static final String SET_REGION_PERMISSION = "TF_CombatRestrain.SetPosition";
	public static final String RELOAD_PERMISSION = "TF_CombatRestrain.Reload";
	
	@Override
	public void onEnable() {		
		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveDefaultConfig();
		}
		
		getServer().getPluginManager().registerEvents(new Events(), this);
		
		getCommand("CombatRestrain").setExecutor(new CombatRestrainCommand());
	}
	
	public static YamlConfiguration getYamlConfiguration() {
		return YamlConfiguration.loadConfiguration(configFile());
	}
	
	private static File configFile() {
		return new File(getCombatRestrain().getDataFolder(), "config.yml");
	}
	
	public static CombatRestrain getCombatRestrain() {
		return (CombatRestrain)Bukkit.getServer().getPluginManager().getPlugin("TF_CombatRestrain");
	}
	
	public static void saveConfiguration(YamlConfiguration config) {
		File configFile = configFile();
		
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static CombatTagPlus getCombatTagPlus() {
		return (CombatTagPlus)Bukkit.getServer().getPluginManager().getPlugin("CombatTagPlus");
	}

}
