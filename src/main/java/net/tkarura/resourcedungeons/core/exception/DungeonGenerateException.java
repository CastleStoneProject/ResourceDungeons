package net.tkarura.resourcedungeons.core.exception;

import net.tkarura.resourcedungeons.core.dungeon.Dungeon;

/**
 * ダンジョンの生成中に起こりうる例外を表すクラスです。
 * @author the_karura
 */
public class DungeonGenerateException extends DungeonException {
	
	// シリアライズバージョンID
	private static final long serialVersionUID = 1L;
	
	/**
	 * 例外を生成します。
	 */
	public DungeonGenerateException() {
		super();
	}
	
	/**
	 * 理由付きで生成します。
	 * @param message 理由
	 */
	public DungeonGenerateException(String message) {
		super(message);
	}
	
	/**
	 * ダンジョン情報付きで生成します。
	 * @param dungeon ダンジョン情報
	 */
	public DungeonGenerateException(Dungeon dungeon) {
		this();
		this.dungeon = dungeon;
	}
	
	/**
	 * 理由とダンジョン情報を付けて生成します。
	 * @param message 理由
	 * @param dungeon ダンジョン情報
	 */
	public DungeonGenerateException(String message, Dungeon dungeon) {
		this(message);
		this.dungeon = dungeon;
	}
	
}
