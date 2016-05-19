package net.tkarura.resourcedungeons.core.server;

import java.util.logging.Logger;

import net.tkarura.resourcedungeons.core.server.world.DungeonWorld;

/**
 * サーバー関連の処理を表します。
 * @author the_karura
 */
public interface DungeonServer {
	
	/**
	 * サーバーログを取得します。 
	 * @return サーバーログ
	 */
	public Logger getLogger();
	
	/**
	 * ワールド情報を取得します。
	 * @param name ワールド名
	 * @return 該当のワールド情報 もし存在しない場合nullを返します。
	 */
	public DungeonWorld getWorld(String name);
	
}
