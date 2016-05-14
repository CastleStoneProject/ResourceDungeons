package net.tkarura.resourcedungeons.core.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tkarura.resourcedungeons.core.exception.CommandException;
import net.tkarura.resourcedungeons.core.server.DungeonSender;

/**
 * コマンド情報を管理するマネージャクラスです。
 * @author the_karura
 */
public class CommandManager {
	
	// コマンド情報を保管する場所
	private Map<String, Command> commands = new HashMap<String, Command>();
	
	// 内部コマンド名
	private List<String> internal_names = new ArrayList<String>();
	
	/**
	 * クラスを生成します。
	 */
	public CommandManager() {
		registerInternalCommand(new HelpCommand());
		registerInternalCommand(new ListCommand());
		registerInternalCommand(new InfoCommand());
		registerInternalCommand(new ReloadCommand());
	}
	
	/**
	 * コマンドを実行します。
	 * @param sender 送信者
	 * @param args 引数
	 * @throws CommandException 実行中に起きた例外
	 */
	public void onCommand(DungeonSender sender, String[] args) throws CommandException {
		
		Command help = this.commands.get("help");
		
		// 引数を確認します。
		if (args.length <= 0) {
			if (sender.hasPermission(help.getPermission())) {
				help.execute(sender, args);
			}
			return;
		}
		
		// 登録されたコマンド情報を取得
		Command command = this.commands.get(args[0].toLowerCase());
		
		// 登録されたコマンドで無ければヘルプを返して終了
		if (command == null) {
			commands.get("help").execute(sender, args);
			return;
		}
		
		// プレイヤーのみの実行でありなおかつコンソールからの実行であるかを確認します。
		if (command.isPlayer() && sender.isConsole())
			throw new CommandException("Player Only Command.");
		
		// プレイヤーがコマンド実行に必要な権限を持つっているのかを確認します。
		if (!command.isPlayer() && !sender.isConsole()) {
			
			if (!sender.hasPermission(command.getPermission()))
				throw new CommandException("You don't have " + command.getPermission());
				
		}
		
		// 条件が一致すればコマンドを実行
		command.execute(sender, args);
		
	}
	
	/**
	 * 内部コマンドを登録します。
	 * 内部コマンドは動作に必須なコマンドの登録に使用します。
	 * このメソッドから登録されたコマンドは
	 * {@link #removeCommand(String)}を使用しての削除は出来ません。
	 * @param command
	 */
	private void registerInternalCommand(Command command) {
		this.registerCommand(command);
		this.internal_names.add(command.getCommand());
	}
	
	/**[
	 * コマンドを登録します。
	 * 同名のコマンドの登録を行う場合{@link IllegalArgumentException}が発生します。
	 * @param command コマンド情報
	 */
	public void registerCommand(Command command) {
		
		String name = command.getCommand().toLowerCase();
		
		if (commands.get(name) != null)
			throw new IllegalArgumentException("Already Register Command.");
		
		this.commands.put(name, command);
	}
	
	/**
	 * 既に登録されたコマンド名であるかを確認します。
	 * @param command コマンド名
	 * @return 既に登録されている場合trueを返します。
	 */
	public boolean isCommand(String command) {
		return this.commands.get(command) != null;
	}
	
	/**
	 * コマンド情報を削除します。
	 * <p>登録されていないコマンドの削除を行う場合の例外は起こりませんが
	 * 内部コマンドの削除を行う場合{@link IllegalArgumentException}が発生します。
	 * @param command コマンド情報
	 */
	public void removeCommand(String command) {
		
		command = command.toLowerCase();
		
		if (commands.get(command) == null)
			return;
		
		if (internal_names.contains(command))
			throw new IllegalArgumentException("Can Not Be Remove Command.");
		
		this.commands.remove(command.toLowerCase());
	}
	
}
