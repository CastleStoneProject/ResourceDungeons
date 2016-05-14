package net.tkarura.resourcedungeons.core.command;

import net.tkarura.resourcedungeons.core.ResourceDungeons;
import net.tkarura.resourcedungeons.core.exception.CommandException;
import net.tkarura.resourcedungeons.core.server.DungeonSender;

/**
 * 設定を再読み込みするコマンドクラスです。
 * @author the_karura
 */
public class ReloadCommand implements Command {
	
	private final String command = "reload";
	private final String permission = "ResourceDungeons.Command.Reload";
	private final boolean cliant = false;
	
	@Override
	public void execute(DungeonSender sender, String[] args) throws CommandException {
		ResourceDungeons.getInstance().init();
		sender.sendMessage("plugin has reloaded.");
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
