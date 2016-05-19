package net.tkarura.resourcedungeons.core.command;

import net.tkarura.resourcedungeons.core.exception.CommandException;
import net.tkarura.resourcedungeons.core.server.sender.DungeonSender;

/**
 * コマンドに関する処理を表します。
 * {@link CommandManager}にコマンドを追加するにはこのインタフェースを実装する必要があります。
 * @author the_karura
 */
public interface Command {
	
	/**
	 * コマンドの処理を行います。
	 * 実行中のエラーは{@link CommandException}を発生させてください。
	 * @param sender 送信者
	 * @param args 引数
	 * @throws CommandException 実行中の例外
	 */
	public void execute(DungeonSender sender, String[] args) throws CommandException;
	
	/**
	 * 実装クラスが持つコマンド名を返します。
	 * {@link CommandManager}が登録したコマンド情報の識別の為に使用します。
	 * @return コマンド名
	 */
	public String getCommand();
	
	/**
	 * コマンド実行に必要なコマンド情報を返します。
	 * @return 権限情報
	 */
	public String getPermission();
	
	/**
	 * プレイヤーからの送信のみ受け付けるから確認します。
	 * @return 受け付ける場合はtrueを返します。
	 */
	public boolean isPlayer();
	
}
