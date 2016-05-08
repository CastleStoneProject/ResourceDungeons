package net.tkarura.resourcedungeons.core.exception;

import net.tkarura.resourcedungeons.core.dungeon.Dungeon;

/**
 * {@link Dungeons}に関わるクラスで使用する例外を取り扱うクラスです。
 * @author the_karura
 */
public class DungeonException extends Exception {
	
	// シリアライズバージョンID
	private static final long serialVersionUID = 1L;
	
	/**
	 * 例外に関わるダンジョン情報
	 */
	protected Dungeon dungeon = null;
	
	/**
	 * 例外を生成します。
	 */
	public DungeonException() {
		super();
	}
	
	/**
	 * 理由付きで生成します。
	 * @param message 理由
	 */
	public DungeonException(String message) {
		super(message);
	}
	
	/**
	 * ダンジョン情報付きで生成します。
	 * @param dungeon ダンジョン情報
	 */
	public DungeonException(Dungeon dungeon) {
		this();
		this.dungeon = dungeon;
	}
	
	/**
	 * 理由とダンジョン情報を付けて生成します。
	 * @param message 理由
	 * @param dungeon ダンジョン情報
	 */
	public DungeonException(String message, Dungeon dungeon) {
		this(message);
		this.dungeon = dungeon;
	}
	
	/**
	 * 例外に関わるダンジョン情報を返します。
	 * @return ダンジョン情報
	 */
	public Dungeon getDungeon() {
		return this.dungeon;
	}
	
}
