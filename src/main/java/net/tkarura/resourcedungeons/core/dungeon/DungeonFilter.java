package net.tkarura.resourcedungeons.core.dungeon;

/**
 * ダンジョン情報のフィルターです。
 * 
 * @author the_karura
 */
public interface DungeonFilter {
	
	/**
	 * 引数で渡されたダンジョン情報がフィルターの条件に含まれているかを判別します。
	 * 
	 * @param dungeon フィルターにかけるダンジョン情報
	 * @return ダンジョンがフィルターの条件に一致する場合trueを返します・
	 */
	public boolean accept(Dungeon dungeon);
	
}
