package net.tkarura.resourcedungeons.core.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.tkarura.resourcedungeons.core.ResourceDungeons;
import net.tkarura.resourcedungeons.core.dungeon.Dungeon;
import net.tkarura.resourcedungeons.core.dungeon.GenerateOption;
import net.tkarura.resourcedungeons.core.exception.DungeonGenerateException;
import net.tkarura.resourcedungeons.core.script.JavaScriptExecutor;
import net.tkarura.resourcedungeons.core.script.ScriptExecutor;
import net.tkarura.resourcedungeons.core.server.DungeonLocation;
import net.tkarura.resourcedungeons.core.server.world.DungeonChunk;

/**
 * 自動生成処理を管理するクラスです。
 * クラスへのアクセスには{@link #getInstance()}を使用します。
 * <p>
 * クラスを使用する手続きは以下の手順に従います。
 * <ol>
 * <li>{@link #init()} クラスの初期化処理
 * 		特別な理由がない限りは{@link #getInstance()}の後に呼び出してください。</li>
 * <li>{@link #searchGenerateChunk(DungeonChunk)}もしくは
 * 		{@link #searchGenerateLocation(DungeonLocation)}からダンジョン生成確認</li>
 * <li>{@link #generate()}からダンジョン生成</li>
 * </ol>
 * 
 * @author the_karura
 */
public class DungeonGenerate {
	
	private static DungeonGenerate instance = new DungeonGenerate();
	
	private final List<DungeonStorage> gen_dungeons = new ArrayList<DungeonStorage>();
	
	// 検索した結果を補完する内部クラス
	private class DungeonStorage {
		
		// 生成するダンジョン
		public Dungeon dungeon;
		
		// 呼び出す関数
		public GenerateOption option;
		
		// 生成する位置
		public DungeonLocation loc;
		
	}
	
	private DungeonGenerate() {}
	
	/**
	 * クラス内の情報を初期化します。
	 */
	public void init() {
		this.gen_dungeons.clear();
	}
	
	/**
	 * チャンク単位での検索処理を行います。
	 * @param chunk 検索を行うチャンク情報
	 * @throws DungeonGenerateException 生成中に例外が発生した場合
	 */
	public void searchGenerateChunk(DungeonChunk chunk) {
		
		for (int y = 0; y < chunk.getWorld().getMaxHeight(); y++) {
			
			// 生成位置を設定
			DungeonLocation loc = chunk.getLocation(DungeonChunk.MAX_X / 2, y, DungeonChunk.MAX_Z / 2);
			
			// 単域のブロック検索を行います。
			searchGenerateLocation(loc);
			
		}
		
	}
	
	/**
	 * ブロック単位での検索処理を行います。
	 * @param loc 検索を行う位置情報
	 */
	public void searchGenerateLocation(DungeonLocation loc) {
		
		Dungeon[] dungeons = ResourceDungeons.getInstance().getDungeonManager().getDungeons();
		Random random = new Random(
			loc.getWorld().getSeed() * loc.getBlockX() * loc.getBlockY() * loc.getBlockZ());
		
		for (Dungeon dungeon : dungeons) {
			
			// 現在の位置情報から生成可能なオプションを検索します。
			List<GenerateOption> options = filterOptions(dungeon, loc);
			
			// 該当するオプションがない場合は次へ
			if (options.isEmpty())
				continue;
			
			// 複数のオプションが該当した場合ランダムからオプションを選びます。
			Collections.shuffle(options, random);
			
			// 生成可能なダンジョンであればクラスに保管します。
			DungeonStorage storage = new DungeonStorage();
			storage.dungeon = dungeon;
			storage.option = options.get(0);
			storage.loc = loc;
			this.gen_dungeons.add(storage);
			
		}
		
	}
	
	private List<GenerateOption> filterOptions(Dungeon dungeon, DungeonLocation loc) {
		
		// 返し値用の変数を宣言
		List<GenerateOption> result = new ArrayList<GenerateOption>();
		
		// ダンジョンが持つ生成オプション一覧をループで取得
		for (GenerateOption option : dungeon.getGenerates()) {
			
			// 位置情報から条件と一致するか比較します。
			if (option.isMatchOptions(loc)) {
				result.add(option);
			}
			
		}
		
		return result;
		
	}
	
	public void generate() throws DungeonGenerateException {
		
		// 生成可能なダンジョンがない場合は処理をしない
		if (gen_dungeons.isEmpty())
			return;
		
		// 生成可能なダンジョン一覧をシャッフルします。
		Collections.shuffle(gen_dungeons);
		
		// シャッフルしたリストから先頭を取得します。
		DungeonStorage storage = gen_dungeons.get(0);
		DungeonLocation loc = storage.loc;
		GenerateOption option = storage.option;
		
		// ワールドseed値 * x * y * z の値からランダムを生成
		Random random = new Random(
				loc.getWorld().getSeed() * loc.getBlockX() * loc.getBlockY() * loc.getBlockZ());
		
		// 確率判定を行い実行するかの確認をします。
		if (random.nextDouble() > option.getPercent()) {
			return;
		}
		
		// 取得した情報からスクリプトを実行します。
		ScriptExecutor script = new JavaScriptExecutor(storage.dungeon);
		script.init();
		script.execute(loc, option.getFunction());
		
		ResourceDungeons.getLogger().info("[gen] generated " + storage.dungeon.getID() + " where loc: " + loc);
		
	}
	
	/**
	 * クラスのインスタンスを返します。
	 * @return インスタンス
	 */
	public static DungeonGenerate getInstance() {
		return instance;
	}
	
}
