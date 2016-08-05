package net.tkarura.resourcedungeons.core.command;

import java.io.File;

import net.tkarura.resourcedungeons.core.SettingManager;
import net.tkarura.resourcedungeons.core.exception.CommandException;
import net.tkarura.resourcedungeons.core.server.sender.DungeonSender;

public final class SetCommand implements Command {
	
	private final String command = "set";
	private final String permission = "ResourceDungeons.Command.Info";
	private final boolean cliant = false;
	
	private SettingManager setting = SettingManager.getInstance();
	
	// /rd set <set-tag> <args>
	
	@Override
	public void execute(DungeonSender sender, String[] args) throws CommandException {
		
		if (args.length < 3)
			throw new CommandException("Command Args is Not Enough.");
		
		switch (args[1]) {
		
		case "settings-dir": {
			setting.setSettingDirectory(new File(args[2]));
			sender.sendMessage("setting \"SettingsDirectory\" -> " + args[2]);
		}
		break;
		
		case "dungeons-dir": {
			setting.setDungeonDirectory(new File(args[2]));
			sender.sendMessage("setting \"DungeonsDirectory\" -> " + args[2]);
		}
		break;
		
		case "scripts-dir": {
			setting.setScriptDirectory(new File(args[2]));
			sender.sendMessage("setting \"ScriptsDirectory\" -> " + args[2]);
		}
		break;
		
		case "generate-world": {
			if (setting.isGenerateWorld(args[2])) {
				setting.removeGenerateWorld(args[2]);
				sender.sendMessage("setting \"GenerateWorlds\" remove " + args[2]);
			} else {
				setting.addGenerateWorld(args[2]);
				sender.sendMessage("setting \"GenerateWorlds\" add " + args[2]);
			}
		}
		break;
		
		}
		
	}
	
	@Override
	public String getCommand() {
		return this.command;
	}
	
	@Override
	public String getPermission() {
		return this.permission;
	}
	
	@Override
	public boolean isPlayer() {
		return cliant;
	}
	
}
