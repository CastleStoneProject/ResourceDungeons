package net.tkarura.resourcedungeons.core.generator;

import javax.script.Invocable;

import net.tkarura.resourcedungeons.core.dungeon.Dungeon;
import net.tkarura.resourcedungeons.core.server.DungeonLocation;

/**
 * 関数に呼び出すハンドル情報です。
 * @deprecated  {@link Invocable#invokeFunction(String, Object...)}を介して使用される為他のクラスで呼び出さないでください。
 * @author the_karura
 */
@Deprecated 
public final class GenerateHandle {
	
	private Dungeon dungeon;
	private DungeonLocation loc;
	
	/**
	 * ハンドルを生成します。
	 * @param dungeon ダンジョン情報
	 * @param loc 生成する位置情報
	 */
	public GenerateHandle(Dungeon dungeon, DungeonLocation loc) {
		this.dungeon = dungeon;
		this.loc = loc;
	}
	
	/**
	 * ダンジョン情報を返します。
	 * @return ダンジョン情報
	 */
	public Dungeon getDungeon() {
		return this.dungeon;
	}
	
	/**
	 * 位置情報を返します。
	 * @return 位置情報
	 */
	public DungeonLocation getLocation() {
		return this.loc;
	}
	
}
