package net.tkarura.resourcedungeons.core.command;

import java.util.Collection;

import net.tkarura.resourcedungeons.core.dungeon.DungeonManager;
import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.exception.DungeonCommandException;

import static net.tkarura.resourcedungeons.core.ResourceDungeons.PREFIX_MES;

public class DungeonListCommand extends DungeonCommand {

	private DungeonManager dungeon_manager;

	public DungeonListCommand() {
		super("list");
		this.description = "読み込んだダンジョン一覧を表示します。";
		this.permission = "ResourceDungeons.Command.List";
	}

	public void setDungeonManager(DungeonManager dungeon_manager) {
		this.dungeon_manager = dungeon_manager;
	}

	@Override
	public void execute(DungeonCommandSender sender) throws DungeonCommandException {

		// ダンジョンリストを取得
		Collection<IDungeon> dungeons = this.dungeon_manager.getDungeons();

		// ダンジョンが格納されていない場合は処理をしない
		if (dungeons.isEmpty()) {
			sender.sendMessage(PREFIX_MES + " &rdungeon is not loaded.");
			return;
		}

		// ダンジョンのリストを返します。
		sender.sendMessage(PREFIX_MES + " =============================================&r");
		sender.sendMessage(PREFIX_MES + " &rdungeon lists.&r");

		// リストの数をカウントをする変数
		int count = 0;

		// ダンジョン情報をループで取得
		for (IDungeon dungeon : dungeons) {
			sender.sendMessage(PREFIX_MES + " [" + (count++) + "] id: " + dungeon.getId() + " name: " + dungeon.getName() + "&r");
		}
		sender.sendMessage(PREFIX_MES + " =============================================&r");

	}

}
