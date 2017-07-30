package net.tkarura.resourcedungeons.core.command;

import net.tkarura.resourcedungeons.core.exception.DungeonCommandException;

public abstract class DungeonCommand {

	protected final String command_name;
	protected boolean player_only = false;
	protected String permission;

	public DungeonCommand(String command_name) {
		this.command_name = command_name;
	}

	public void runCommand(DungeonCommandSender sender) {

		// コンソールでの実行を許可しない場合
		// コンソールからの実行であるかを判定します
		if (this.player_only && sender.getUUID() == null) {
			sender.sendMessage("Player Only.");
			return;
		}

		// コマンドに権限が設定されている場合
		// 送信者が必要な権限を所持しているかを判定します
		if (permission != null && !sender.hasPermission(permission)) {
			sender.sendMessage("I do not have permission.");
			return;
		}

		try {

			// コマンドの実行
			this.execute(sender);

		} catch (DungeonCommandException e) {

			// 例外が発生した場合 送信者に通知します。
			e.printStackTrace();
			sender.sendMessage(e.getLocalizedMessage());

		}

	}

	public abstract void execute(DungeonCommandSender sender) throws DungeonCommandException;

	public String getName() {
		return this.command_name;
	}

}
