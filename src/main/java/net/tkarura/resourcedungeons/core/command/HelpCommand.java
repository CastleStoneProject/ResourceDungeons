package net.tkarura.resourcedungeons.core.command;

import net.tkarura.resourcedungeons.core.exception.CommandException;
import net.tkarura.resourcedungeons.core.server.sender.DungeonSender;

/**
 * コマンドの使い方一覧を表示するコマンドクラスです。
 * @author the_karura
 */
public final class HelpCommand implements Command {
	
	private final String command = "help";
	private final String permission = "ResourceDungeons.Command.Help";
	private final boolean cliant = false;
	
	@Override
	public void execute(DungeonSender sender, String[] args) throws CommandException {
		
		sender.sendMessage("==================================================");
		sender.sendMessage("ヘルプガイド");
		sender.sendMessage("メインコマンド /resourcedungeons 省略 /rd");
		sender.sendMessage("/rd help => ヘルプガイドを表示します");
		sender.sendMessage("/rd reload => 設定の読み直しをします。");
		sender.sendMessage("/rd list => 読み込んだダンジョン情報を一覧として返します。");
		sender.sendMessage("/rd info [dungeon id] => 指定したダンジョンの詳細を表示します。");
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
