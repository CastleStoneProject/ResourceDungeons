package net.tkarura.resourcedungeons.core.command;

import net.tkarura.resourcedungeons.core.ResourceDungeons;

public class DungeonHelpCommand extends DungeonCommand {

	private final static String WEB_SITE_URL = "https://tkarura.net/resourcedungeons/wiki";

	public DungeonHelpCommand() {
		super("help");
	}

	@Override
	public void execute(DungeonCommandSender sender) {

		sender.sendMessage("==================================================");
		sender.sendMessage("ResourceDungeons " + ResourceDungeons.VERSION);
		sender.sendMessage("");
		sender.sendMessage("web guid:");
		sender.sendMessage(WEB_SITE_URL);
		sender.sendMessage("");
		sender.sendMessage("commands:");
		sender.sendMessage("/ResourceDungeons help - ヘルプガイドを表示");
		sender.sendMessage("/ResourceDungeons list - 読み込んだダンジョン一覧を表示");
		sender.sendMessage("/ResourceDungeons generate [dungeon] - その場にダンジョンを生成");
		sender.sendMessage("==================================================");

	}

}
