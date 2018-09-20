package net.tkarura.resourcedungeons.core.command;

import net.tkarura.resourcedungeons.core.dungeon.*;
import net.tkarura.resourcedungeons.core.exception.DungeonCommandException;

import java.math.BigDecimal;

import static net.tkarura.resourcedungeons.core.ResourceDungeons.PREFIX_MES;

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
            sender.sendMessage(PREFIX_MES + " &c引数が足りません。dungeon idを指定してください。");
            return;
        }

        IDungeon dungeon = dungeon_manager.getDungeon(sender.getArgs()[1]);

        if (dungeon == null) {
            sender.sendMessage(PREFIX_MES + " &c該当のdungeon idはありません。");
            return;
        }

        sender.sendMessage(PREFIX_MES + " =============================================&r");
        sender.sendMessage(PREFIX_MES + " id: " + dungeon.getId() + "&r");
        sender.sendMessage(PREFIX_MES + " name:    " + dungeon.getName() + "&r");
        sender.sendMessage(PREFIX_MES + " version: " + dungeon.getVersion() + "&r");
        sender.sendMessage(PREFIX_MES + " ---------------------------------------------&r");
        sender.sendMessage(PREFIX_MES + " " + dungeon.getDescription() + "&r");
        sender.sendMessage(PREFIX_MES + " ---------------------------------------------&r");
        sender.sendMessage(PREFIX_MES + " &bauthors:&r");
        if (dungeon.getAuthors().isEmpty()) {
            sender.sendMessage(PREFIX_MES + " &rauthor not fount.&r");
        }
        for (DungeonUser user : dungeon.getAuthors()) {
            sender.sendMessage(PREFIX_MES + " > name: " + user.getName() + " uuid: " + user.getUUID() + "&r");
        }
        sender.sendMessage(PREFIX_MES + " ---------------------------------------------&r");
        sender.sendMessage(PREFIX_MES + " &bcontributors:&r");
        if (dungeon.getAuthors().isEmpty()) {
            sender.sendMessage(PREFIX_MES + " &rcontributor not fount.&r");
        }
        for (DungeonUser user : dungeon.getContributors()) {
            sender.sendMessage(PREFIX_MES + " > name: " + user.getName() + " uuid: " + user.getUUID() + "&r");
        }
        sender.sendMessage(PREFIX_MES + " ---------------------------------------------&r");
        sender.sendMessage(PREFIX_MES + " &bscripts:&r");
        if (dungeon.getScripts().isEmpty()) {
            sender.sendMessage(PREFIX_MES + " &rscript not found.&r");
        } else {
            sender.sendMessage(PREFIX_MES + " script " + dungeon.getScripts().size() + " loaded.&r");
        }
        for (IDungeonScript script : dungeon.getScripts()) {
            sender.sendMessage(PREFIX_MES + " > " + script.getLocation() + "&r");
        }
        sender.sendMessage(PREFIX_MES + " ---------------------------------------------&r");
        sender.sendMessage(PREFIX_MES + " &boptions:&r");
        if (dungeon.getGenerateOptions().isEmpty()) {
            sender.sendMessage(PREFIX_MES + " &roptions not found.&r");
        }
        for (DungeonGenerateOption option : dungeon.getGenerateOptions()) {
            sender.sendMessage(PREFIX_MES + " > function: " + option.getFunctionName() +
                    " percent: " + toParcentString(option.getPercent()) +
                    " biomes: " + getListToString(option.getBiomes()) + "&r");
        }
        sender.sendMessage(PREFIX_MES + " =============================================&r");

    }

    private String getListToString(String[] list) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.length; i++) {
            builder.append(list[i]).append(" ");
        }
        return builder.toString();
    }

    private String toParcentString(double v) {
        return BigDecimal.valueOf(v).toPlainString();
    }

}
