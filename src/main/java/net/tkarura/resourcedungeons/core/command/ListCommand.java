package net.tkarura.resourcedungeons.core.command;

import net.tkarura.resourcedungeons.core.DungeonManager;
import net.tkarura.resourcedungeons.core.ResourceDungeons;
import net.tkarura.resourcedungeons.core.dungeon.Dungeon;
import net.tkarura.resourcedungeons.core.exception.CommandException;
import net.tkarura.resourcedungeons.core.server.DungeonSender;

/**
 * 読み込んだダンジョン情報一覧を表示するコマンドクラスです。
 * @author the_karura
 */
public class ListCommand implements Command {
	
	private final String command = "list";
	private final String permission = "ResourceDungeons.Command.List";
	private final boolean cliant = false;
	
	@Override
	public void execute(DungeonSender sender, String[] args) throws CommandException {
		
		ResourceDungeons main = ResourceDungeons.getInstance();
		DungeonManager dm = main.getDungeonManager();
		
		sender.sendMessage("dungeon list.");
		
		sender.sendMessage("==================================================");
		if (dm.getDungeons().length != 0) {
			for (Dungeon dungeon : dm.getDungeons()) {
				
				sender.sendMessage((dungeon.isEnable() ? "&a" : "&c") + "id : " + dungeon.getID() + " name : "
						+ dungeon.getName() + " version : " + dungeon.getVersion());
				
			}
		} else {
			sender.sendMessage("dungeon is Empty.");
		}
		sender.sendMessage("==================================================");
		
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
		return this.cliant;
	}
	
}
