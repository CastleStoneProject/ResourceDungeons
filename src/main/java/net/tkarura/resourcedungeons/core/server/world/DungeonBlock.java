package net.tkarura.resourcedungeons.core.server.world;

import net.tkarura.resourcedungeons.core.server.DungeonLocation;

/**
 * ブロックに関す情報を保管するクラスです。
 * TileEntityに関する情報も取り扱います。
 * @author the_karura
 */
public interface DungeonBlock {
	
	/**
	 * ブロックIDを返します。
	 * @return ブロックID
	 */
	public String getID();
	
	/**
	 * ダメージ値を返します。
	 * @return ダメージ値
	 */
	public byte getDamage();
	
	/**
	 * 位置情報を返します。
	 * @return 位置情報
	 */
	public DungeonLocation getLocation();
	
}
