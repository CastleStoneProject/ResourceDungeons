package net.tkarura.resourcedungeons.core.exception;

import net.tkarura.resourcedungeons.core.command.Command;
import net.tkarura.resourcedungeons.core.command.CommandManager;
import net.tkarura.resourcedungeons.core.dungeon.Dungeon;

/**
 * {@link Command}実装クラスおよび{@link CommandManager}クラスに関わる例外を取り扱う例外クラスです。
 * @author the_karura
 */
public class CommandException extends DungeonException {

	// シリアライズバージョンID
	private static final long serialVersionUID = 1L;
	
	/**
	 * 例外を生成します。
	 */
	public CommandException() {
		super();
	}
	
	/**
	 * 理由付きで生成します。
	 * @param message 理由
	 */
	public CommandException(String message) {
		super(message);
	}
	
	/**
	 * ダンジョン情報付きで生成します。
	 * @param dungeon ダンジョン情報
	 */
	public CommandException(Dungeon dungeon) {
		this();
		this.dungeon = dungeon;
	}
	
	/**
	 * 理由とダンジョン情報を付けて生成します。
	 * @param message 理由
	 * @param dungeon ダンジョン情報
	 */
	public CommandException(String message, Dungeon dungeon) {
		this(message);
		this.dungeon = dungeon;
	}
	
}
