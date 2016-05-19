package net.tkarura.resourcedungeons.core.server.sender;

import net.tkarura.resourcedungeons.core.server.DungeonServer;

/**
 * 送信者に関する処理を表します。
 * @author the_karura
 */
public interface DungeonSender extends DungeonPremission {
	
	/**
	 * 送信者にメッセージを送信します。
	 * @param message メッセージ
	 */
	public void sendMessage(String message);
	
	/**
	 * 送信者の名前を返します。
	 * @return 送信者の名前
	 */
	public String getName();
	
	/**
	 * 送信元のサーバー情報を返します。
	 * @return サーバー情報
	 */
	public DungeonServer getServer();
	
}
