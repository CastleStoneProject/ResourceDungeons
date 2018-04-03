package net.tkarura.resourcedungeons.core.command;

import net.tkarura.resourcedungeons.core.dungeon.*;
import net.tkarura.resourcedungeons.core.exception.DungeonCommandException;

public class DungeonInfoCommand extends DungeonCommand {

    public final static String COMMAND_NAME = "info";
    private DungeonManager dungeon_manager;

    public DungeonInfoCommand() {
        super(COMMAND_NAME);
        this.description = "ダンジョンの詳細を表示します。";
        this.permission = "ResourceDungeons.Command.Info";
    }

    public void setDungeonManager(DungeonManager dungeon_manager) {
        this.dungeon_manager = dungeon_manager;
    }

    @Override
    public void execute(DungeonCommandSender sender) throws DungeonCommandException {

        if (sender.getArgs().length <= 1) {
            sender.sendMessage("引数が足りません。dungeon idを指定してください。");
            return;
        }

        IDungeon dungeon = dungeon_manager.getDungeon(sender.getArgs()[1]);

        if (dungeon == null) {
            sender.sendMessage("該当のdungeon idはありません。");
            return;
        }

        sender.sendMessage("==================================================");
        sender.sendMessage("id: " + dungeon.getId());
        sender.sendMessage("name:    " + dungeon.getName());
        sender.sendMessage("version: " + dungeon.getVersion());
        sender.sendMessage("--------------------------------------------------");
        sender.sendMessage(dungeon.getDescription());
        sender.sendMessage("--------------------------------------------------");
        sender.sendMessage("authors: ");
        if (dungeon.getAuthors().isEmpty()) {
            sender.sendMessage("author not fount.");
        }
        for (DungeonUser user : dungeon.getAuthors()) {
            sender.sendMessage("name: " + user.getName() + " uuid: " + user.getUUID());
        }
        sender.sendMessage("--------------------------------------------------");
        sender.sendMessage("contributors: ");
        if (dungeon.getAuthors().isEmpty()) {
            sender.sendMessage("contributor not fount.");
        }
        for (DungeonUser user : dungeon.getContributors()) {
            sender.sendMessage("name: " + user.getName() + " uuid: " + user.getUUID());
        }
        sender.sendMessage("--------------------------------------------------");
        sender.sendMessage("scripts: ");
        if (dungeon.getScripts().isEmpty()) {
            sender.sendMessage("script not found.");
        } else {
            sender.sendMessage("script " + dungeon.getScripts().size() + " loaded.");
        }
        for (IDungeonScript script : dungeon.getScripts()) {
            sender.sendMessage(script.getLocation());
        }
        sender.sendMessage("--------------------------------------------------");
        sender.sendMessage("options: ");
        if (dungeon.getGenerateOptions().isEmpty()) {
            sender.sendMessage("options not found.");
        }
        for (DungeonGenerateOption option : dungeon.getGenerateOptions()) {
            sender.sendMessage("function: " + option.getFunctionName() + " percent: " + option.getPercent() +
                    " biomes: " + getListToString(option.getBiomes()));
        }
        sender.sendMessage("==================================================");

    }

    private String getListToString(String[] list) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.length; i++) {
            builder.append(list[i]).append(" ");
        }
        return builder.toString();
    }

}
