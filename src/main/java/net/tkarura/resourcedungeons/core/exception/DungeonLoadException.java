package net.tkarura.resourcedungeons.core.exception;

import net.tkarura.resourcedungeons.core.dungeon.Dungeon;

/**
 * {@link Dungeon}に関する情報を読取る際に起こりうる例外を表します。
 * @author the_karura
 */
public class DungeonLoadException extends DungeonException {
	
	// シリアライズバージョンID
	private static final long serialVersionUID = 1L;
	
	/**
	 * 例外を生成します。
	 */
	public DungeonLoadException() {
		super();
	}
	
	/**
	 * 理由付きで生成します。
	 * @param message 理由
	 */
	public DungeonLoadException(String message) {
		super(message);
	}
	
	/**
	 * ダンジョン情報付きで生成します。
	 * @param dungeon ダンジョン情報
	 */
	public DungeonLoadException(Dungeon dungeon) {
		this();
		this.dungeon = dungeon;
	}
	
	/**
	 * 理由とダンジョン情報を付けて生成します。
	 * @param message 理由
	 * @param dungeon ダンジョン情報
	 */
	public DungeonLoadException(String message, Dungeon dungeon) {
		this(message);
		this.dungeon = dungeon;
	}
	
}
