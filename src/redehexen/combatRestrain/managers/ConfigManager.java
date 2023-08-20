package redehexen.combatRestrain.managers;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import redehexen.combatRestrain.CombatRestrain;

public class ConfigManager {
	
	private static ConfigManager instance = new ConfigManager();
	
	public static ConfigManager getInstance() {
		return instance;
	}
	
	public static void reload() {
		instance = new ConfigManager();
	}
	
	private int _wallDistanceToShowBlocks;
	private int _wallHeight;
	private int _wallWidth;
	private Material _wallMaterial;
	private byte _wallDataValue;
	
	private String _positionSetMessage;
	private String _reloadedMessage;
	
	private String _notAllowedMessage;
	private String _wrongIndexMessage;
	private String _mustBePlayerMessage;
	
	private String _setPositionUsageMessage;
	private String _reloadUsageMessage;
	
	private ConfigManager() {
		YamlConfiguration config = CombatRestrain.getYamlConfiguration();
		
		_wallDistanceToShowBlocks = config.getInt("WallDistanceToShowBlocks");
		_wallHeight = config.getInt("WallHeight");
		_wallWidth = config.getInt("WallWidth");
		_wallMaterial = Material.getMaterial(config.getString("WallMaterial"));
		_wallDataValue = (byte) config.getInt("WallMaterial");
		
		_positionSetMessage = config.getString("Messages.Warnings.PositionSet").replace("&", "§");
		_reloadedMessage = config.getString("Messages.Warnings.Reloaded").replace("&", "§");
		
		_notAllowedMessage = config.getString("Messages.Errors.NotAllowed").replace("&", "§");
		_wrongIndexMessage = config.getString("Messages.Errors.WrongIndex").replace("&", "§");
		_mustBePlayerMessage = config.getString("Messages.Errors.MustBePlayer").replace("&", "§");
		
		_setPositionUsageMessage = config.getString("Messages.Usage.SetPosition").replace("&", "§");
		_reloadUsageMessage = config.getString("Messages.Usage.Reload").replace("&", "§");
	}
	
	public int getWallDistanceToShowBlocks() {
        return _wallDistanceToShowBlocks;
    }
	
	public int getWallHeight() {
		return _wallHeight;
	}
	
	public int getWallWidth() {
		return _wallWidth;
	}
	
	public Material getWallMaterial() {
		return _wallMaterial;
	}
	
	public byte getWallDataValue() {
		return _wallDataValue;
	}
	
	public String getPositionSetMessage() {
        return _positionSetMessage;
    }

    public String getReloadedMessage() {
        return _reloadedMessage;
    }
    
    public String getNotAllowedMessage() {
        return _notAllowedMessage;
    }

    public String getWrongIndexMessage() {
        return _wrongIndexMessage;
    }

    public String getMustBePlayerMessage() {
        return _mustBePlayerMessage;
    }

    public String getSetPositionUsageMessage() {
        return _setPositionUsageMessage;
    }

    public String getReloadUsageMessage() {
        return _reloadUsageMessage;
    }

}
