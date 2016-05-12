package net.tkarura.resourcedungeons.core.server;

import java.util.UUID;

/**
 * 送信者に関する処理を受け渡す中間クラスです。
 * @author the_karura
 */
public abstract class DungeonSender {
	
	protected final UUID uuid;
	protected String name;
	protected DungeonLocation loc;
	protected boolean consle = false;
	
	/**
	 * コンソール情報として生成します。
	 */
	public DungeonSender() {
		this.uuid = null;
	}
	
	/**
	 * UUIDと位置情報を指定して生成します。
	 * @param uuid 送信者のUUID
	 */
	public DungeonSender(UUID uuid, DungeonLocation loc) {
		this.uuid = uuid;
		this.loc = loc;
	}
	
	/**
	 * 送信者にメッセージを送信します。
	 * @param message メッセージ
	 */
	public abstract void sendMessage(String message);
	
	/**
	 * UUIDを返します。
	 * @return 送信者のUUID
	 */
	public UUID getUUID() {
		return this.uuid;
	}
	
	/**
	 * 名前を返します。
	 * @return 送信者の名前
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 権限情報を確認します。
	 * @param permission 確認する権限情報
	 * @return 送信者が権限を所有していた場合trueを返します。
	 */
	public abstract boolean hasPermission(String permission);
	
	/**
	 * 位置情報を返します。
	 * @return 送信の位置情報
	 */
	public DungeonLocation getLocation() {
		return this.loc;
	}
	
	/**
	 * コンソールからの送信であるかを確認します。
	 * @return コンソールからの送信であればtrueを返します。
	 */
	public boolean isConsole() {
		return this.consle;
	}
	
}
