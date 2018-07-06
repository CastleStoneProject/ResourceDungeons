package net.tkarura.resourcedungeons.core.command;

import net.tkarura.resourcedungeons.core.ResourceDungeons;

import static net.tkarura.resourcedungeons.core.ResourceDungeons.PREFIX_MES;

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

		sender.sendMessage(PREFIX_MES + " ========================================&f");
		sender.sendMessage(PREFIX_MES + " &6R&fesource&6D&fungeons &7v" + ResourceDungeons.VERSION + "&f");
		sender.sendMessage(PREFIX_MES + " ");
		sender.sendMessage(PREFIX_MES + " &bweb guid:" + "&f");
		sender.sendMessage(PREFIX_MES + " > &n" + WEB_SITE_URL + "&r");
		sender.sendMessage(PREFIX_MES + " ");
		sender.sendMessage(PREFIX_MES + " &bcommands:&f");
		for (DungeonCommand dungeonCommand : command_manager.getCommands()) {
			sender.sendMessage(PREFIX_MES + " > &2/ResourceDungeons &a" + dungeonCommand.getName() + " &f- &n&7" + dungeonCommand.getDescription() + "&f");
		}
		sender.sendMessage(PREFIX_MES + " ========================================&f");

	}

}
