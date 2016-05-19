package net.tkarura.resourcedungeons.core.server.sender;

/**
 * 権限に関する処理を表します。
 * @author the_karura
 */
public interface DungeonPremission {
	
	/**
	 * 指定された権限を所持しているかを確認します。
	 * @param permission 確認する権限
	 * @return 所持している場合trueに返します。
	 */
	public boolean hasPermission(String permission);
	
}
