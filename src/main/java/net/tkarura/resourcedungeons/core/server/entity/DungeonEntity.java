package net.tkarura.resourcedungeons.core.server.entity;

import java.util.UUID;

import net.tkarura.resourcedungeons.core.server.DungeonLocation;

/**
 * Entityに関わる処理を表します。
 * @author the_karura
 *
 */
public interface DungeonEntity {
	
	/**
	 * UUIDを返します。
	 * @return UUID
	 */
	public UUID getUUID();
	
	/**
	 * 位置情報を返します。
	 * @return 位置情報
	 */
	public DungeonLocation getLocation();
	
}
