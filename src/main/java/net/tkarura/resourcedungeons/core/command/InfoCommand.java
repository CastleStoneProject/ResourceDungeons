package net.tkarura.resourcedungeons.core.command;

import net.tkarura.resourcedungeons.core.DungeonManager;
import net.tkarura.resourcedungeons.core.ResourceDungeons;
import net.tkarura.resourcedungeons.core.dungeon.Dungeon;
import net.tkarura.resourcedungeons.core.dungeon.GenerateOption;
import net.tkarura.resourcedungeons.core.exception.CommandException;
import net.tkarura.resourcedungeons.core.server.DungeonSender;

/**
 * 読み込んだダンジョンの詳細情報を表示するコマンドクラスです。
 * @author the_karura
 */
public class InfoCommand implements Command {
	
	private final String command = "info";
	private final String permission = "ResourceDungeons.Command.Info";
	private final boolean cliant = false;
	
	@Override
	public void execute(DungeonSender sender, String[] args) throws CommandException {
		
		if (args.length < 2)
			throw new CommandException("Command Args is Not Enough.");
		
		DungeonManager dm = ResourceDungeons.getInstance().getDungeonManager();
		Dungeon dungeon = dm.getDungeon(args[1]);
		
		if (dungeon == null)
			throw new CommandException("Dungeon is Not Dound.");
		
		sender.sendMessage("==================================================");
		sender.sendMessage("name : " + dungeon.getName() + " id : " + dungeon.getID());
		sender.sendMessage("version : " + dungeon.getVersion() + " proto : " + dungeon.getSuppoert());
		if (!dungeon.getGenerates().isEmpty()) {
			for (GenerateOption option : dungeon.getGenerates()) {
				sender.sendMessage(option.toString());
			}
		}
		sender.sendMessage("==================================================");
		
	}
	
	@Override
	public String getCommand() {
		return command;
	}
	
	@Override
	public String getPermission() {
		return permission;
	}
	
	@Override
	public boolean isPlayer() {
		return cliant;
	}
	
}
