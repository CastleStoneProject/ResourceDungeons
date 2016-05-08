package net.tkarura.resourcedungeons.core.dungeon;

/**
 * {@link DungeonScript}の格納している情報が何を指定しているかを表します。
 * 
 * <table>
 * <tr><th>Type</th><th>解説</th></tr>
 * <tr><td>{@link #TEXT_CONTENT}</td><td>テキスト指定を表します。</td></tr>
 * <tr><td>{@link #FILE_LOCATION}</td><td>ファイル指定を表します。</td></tr>
 * </table>
 * 
 * @author the_karura
 */
public enum ScriptType {
	
	/**
	 * テキスト指定であることを表します。
	 */
	TEXT_CONTENT,
	
	/**
	 * ファイル指定であることを表します。
	 */
	FILE_LOCATION
}
