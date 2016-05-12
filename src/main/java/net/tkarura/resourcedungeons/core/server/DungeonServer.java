package net.tkarura.resourcedungeons.core.server;

import java.util.logging.Logger;

/**
 * サーバー関連の処理を受け渡す中間クラスです。
 * @author the_karura
 */
public abstract class DungeonServer {
	
	/**
	 * サーバー全体にメッセージを送ります。
	 * @param message メッセージ
	 */
	public abstract void broadcast(String message);
	
	/**
	 * サーバーログを取得します。 
	 * @return サーバーログ
	 */
	public abstract Logger getLogger();
	
	/**
	 * ワールド情報を取得します。
	 * @param name ワールド名
	 * @return 該当のワールド情報 もし存在しない場合nullを返します。
	 */
	public abstract DungeonWorld getWorld(String name);
	
}
