package net.tkarura.resourcedungeons.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

import net.tkarura.resourcedungeons.core.dungeon.Dungeon;
import net.tkarura.resourcedungeons.core.dungeon.builder.DungeonBuilder;
import net.tkarura.resourcedungeons.core.dungeon.builder.XMLDungeonBuilder;
import net.tkarura.resourcedungeons.core.exception.DungeonLoadException;
import net.tkarura.resourcedungeons.core.util.FileHandler;

/**
 * {@link Dungeon}に関する情報の管理を行うクラスです。
 * 
 * @author the_karura
 */
public final class DungeonManager {
	
	// ダンジョン管理情報
	private Map<String, Dungeon> dungeons = new HashMap<String, Dungeon>();
	
	/**
	 * 同パッケージ外からの生成を防止
	 */
	DungeonManager() {}
	
	/**
	 * 初期化します。
	 */
	public void init() {
		
		// ダンジョン情報を全て削除します。
		this.dungeons.clear();
		
	}
	
	public void load(File dir) {
		
		// 必要なクラスの宣言
		File hedder;
		DungeonBuilder builder;
		Dungeon dungeon;
		
		// Dungeonsディレクトリを検索します。
		for (File dungeon_dir : dir.listFiles(FileHandler.FOLDER_FILTER)) {
			hedder = new File(dungeon_dir, "dungeon.xml");
			
			// 該当のファイルがあるかを確認します。
			if (hedder.exists()) {
				
				try {
					
					// ビルダークラスにファイル情報を渡します。
					builder = new XMLDungeonBuilder(new FileInputStream(hedder), dungeon_dir);
					
					// ビルダークラスから結果を取得します。
					dungeon = builder.getResult();
					
					// ダンジョン情報を登録処理へ
					registerDungeon(dungeon);
					
					ResourceDungeons.getLogger().info("dungeon loaded \"" + dungeon.getID() + "\"");
					
				} catch (DungeonLoadException | FileNotFoundException | IllegalArgumentException
						| NullPointerException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	/**
	 * ダンジョン情報を登録します。
	 * @param dungeon 登録するダンジョン情報
	 * @throws IllegalArgumentException {@link Dungeon#getID()}が返す値が既に登録された情報と一致する場合
	 */
	public void registerDungeon(Dungeon dungeon) {
		
		// nullチェック
		Validate.notNull(dungeon, "dungeon is null.");
		
		// HashMapに格納された情報と被っていないかを確認します。
		if (isDungeon(dungeon.getID()))
			throw new IllegalArgumentException();
		
		// ダンジョン情報を登録
		this.dungeons.put(dungeon.getID(), dungeon);
		
	}
	
	/**
	 * 指定したIDが既に登録されているかを確認します。
	 * 
	 * @param id 確認するID
	 * @return 既に登録されている場合trueを返します。
	 */
	public boolean isDungeon(String id) {
		Validate.notNull(id, "id is null.");
		return this.dungeons.containsKey(id.toLowerCase());
	}
	
	/**
	 * 識別IDから登録されたダンジョン情報を返します。
	 * 
	 * @param id 識別ID
	 * @return ダンジョン情報 もし登録されていない場合はnullを返します。
	 */
	public Dungeon getDungeon(String id) {
		Validate.notNull(id, "id is null.");
		return this.dungeons.get(id);
	}
	
	/**
	 * 登録されたダンジョン情報一覧を返します。
	 * 返される配列の並びは処理の結果によります。
	 * 
	 * @return 登録されたダンジョン情報一覧
	 */
	public Dungeon[] getDungeons() {
		return this.dungeons.values().toArray(new Dungeon[0]);
	}
	
}
