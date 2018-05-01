package net.tkarura.resourcedungeons.core.command;

import net.tkarura.resourcedungeons.core.ResourceDungeons;

public class DungeonHelpCommand extends DungeonCommand {

	private final static String WEB_SITE_URL = "https://rd.tkarura.net/";
	private CommandManager command_manager;

	public DungeonHelpCommand() {
		super("help");
		this.description = "ヘルプガイドを表示します。";
	}

	public void setCommandManager(CommandManager command_manager) {
		this.command_manager = command_manager;
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
		for (DungeonCommand dungeonCommand : command_manager.getCommands()) {
			sender.sendMessage("/ResourceDungeons " + dungeonCommand.getName() + " - " + dungeonCommand.getDescription());
		}
		sender.sendMessage("==================================================");

	}

}
