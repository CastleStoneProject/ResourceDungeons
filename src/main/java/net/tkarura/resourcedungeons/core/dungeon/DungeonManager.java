package net.tkarura.resourcedungeons.core.dungeon;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang3.Validate;

import net.tkarura.resourcedungeons.core.exception.DungeonLoadException;
import net.tkarura.resourcedungeons.core.loader.FileDungeonLoader;
import net.tkarura.resourcedungeons.core.loader.handle.LoadLogHandle;
import net.tkarura.resourcedungeons.core.loader.handle.LoadLogHandleList;

/**
 * ダンジョンに関わる情報を管理するクラスです。
 *
 * @author the_karura
 */
public final class DungeonManager {

	private Logger log = Logger.getLogger("ResourceDungeons");

	// ダンジョン管理情報
	private Map<String, IDungeon> dungeons = new HashMap<String, IDungeon>();

	// 読み込みクラスの識別
	private Map<String, FileDungeonLoader> file_loaders = new HashMap<String, FileDungeonLoader>();

	// 再帰処理で読み込める回数
	private int depth_limit = 15;

	/**
	 * {@link DungeonManager}を生成します。
	 */
	public DungeonManager() {
		this.init();
	}

	public void setLogger(Logger log) {
		this.log = log;
	}

	/**
	 * 初期化します。
	 */
	public void init() {

		// ダンジョン情報を全て削除します。
		this.dungeons.clear();

		// 読み取りクラスの情報を全て削除します。
		this.file_loaders.clear();

	}

	/**
	 * ダンジョン情報を読み込む為のローダを登録します。
	 * 登録したローダは{{@link #loadDungeon(File)}にて使用されます。
	 *
	 * @param loader 登録するローダ情報
	 * @param extendz 対応する拡張子
	 */
	public void addFileDungeonLoader(FileDungeonLoader loader, String[] extendz) {

		Validate.notEmpty(extendz, "extends can not be empty.");

		for (String extend : extendz) {
			this.file_loaders.put(extend, loader);
		}

	}

	/**
	 * 指定したディレクトリからダンジョン情報を読み取ります。
	 *
	 * @param dir
	 *            読み込みを行うディレクトリ情報
	 */
	public void loadDungeons(File dir) {

		Validate.notNull(dir, "dir is null.");

		// ディレクトリを読み込める深さを記録
		int depth = 0;

		// 再帰処理でディレクトリを読み込みます。
		load(dir, depth);

	}

	/**
	 * {@link #loadDungeons(File)}の再帰処理用メソッド
	 *
	 * @param dir
	 *            ファイル情報
	 * @param depth
	 *            読み込んでるディレクトリの深さ
	 */
	private void load(File dir, int depth) {

		if (dir.isDirectory() && !(depth >= depth_limit)) {

			// 再帰回数のカウントをあげます。
			depth++;

			// ディレクトリ内のファイルおよびフォルダーを検索します。
			for (File dir_ : dir.listFiles()) {

				// 再帰的にディレクトリに検索をかけます。
				load(dir_, depth);

			}

			// 再帰回数のカウントをさげます。
			depth--;

		} else {

			// ファイルであればダンジョン読み込み処理へ
			if (dir.isFile() && dir.canRead() && isDungeonHeaderFile(dir)) {

				loadDungeon(dir);

			}

		}

	}

	/**
	 * ダンジョンの定義ファイルであるかの判定を行います。
	 * @param file 判定を行うファイル
	 * @return ダンジョン定義ファイルであれば true を返します。
	 */
	private boolean isDungeonHeaderFile(File file) {
		return file.getName().toLowerCase().startsWith("dungeon");
	}

	/**
	 * ファイル情報からダンジョン情報を読み込みます。
	 * 読み込みには{{@link #addFileDungeonLoader(FileDungeonLoader, String[])}にて登録されたローダが使用されます。
	 * 読み込み時に呼び出されるローダはファイル拡張子により識別されます。
	 *
	 * @param file
	 */
	public void loadDungeon(File file) {

		Validate.notNull(file, "file is null.");

		// 読み込みに使用するローダ情報
		FileDungeonLoader loader = null;

		try {

			// ファイルの拡張子からローダーを取得します。
			String[] split = file.getName().split("\\.");
			loader = this.file_loaders.get(split[split.length - 1]);

			// 登録された拡張子であれば読み取り処理
			if (loader != null) {

				// ファイルからダンジョン情報を取得します。
				IDungeon dungeon = loader.loadFileDungeon(file);

				// ダンジョン情報を登録します。
				registerDungeon(dungeon);

			}

		} catch (DungeonLoadException e) {

		} catch (IllegalArgumentException e) {

		} finally {

			// 読み込み時のログ情報を出力
			if (loader != null) {

				// 読み込み時のログハンドルを取得
				LoadLogHandleList list = loader.getLogHandleList();

				// ログがある場合はログ案内を通知します。
				if (!list.getLogs().isEmpty()) {

					log.info("dungeon validate logs. dir : \"" + file.getPath() + "\"");

				}

				// ログ情報を出力します。
				for (LoadLogHandle handle : list.getLogs()) {

					log.log(handle.getLevel(), handle.getMessage());

				}
			}
		}

	}

	/**
	 * ダンジョン情報を登録します。
	 *
	 * @param dungeon
	 *            登録するダンジョン情報
	 * @throws IllegalArgumentException
	 */
	public void registerDungeon(IDungeon dungeon) {

		// nullチェック
		Validate.notNull(dungeon, "dungeon is null.");

		// HashMapに格納された情報と被っていないかを確認します。
		if (isDungeon(dungeon.getId()))
			throw new IllegalArgumentException();

		// ダンジョン情報を登録
		this.dungeons.put(dungeon.getId(), dungeon);

	}

	/**
	 * 指定したIDが既に登録されているかを確認します。
	 *
	 * @param id
	 *            確認するID
	 * @return 既に登録されている場合trueを返します。
	 * @throws IllegalArgumentException
	 *             nullの値を引数にした場合
	 */
	public boolean isDungeon(String id) {
		Validate.notNull(id, "id is null.");
		return this.dungeons.containsKey(id.toLowerCase());
	}

	/**
	 * 識別IDから登録されたダンジョン情報を返します。
	 *
	 * @param id
	 *            識別ID
	 * @return ダンジョン情報 もし登録されていない場合はnullを返します。
	 * @throws IllegalArgumentException
	 *             nullの値を引数にした場合
	 */
	public IDungeon getDungeon(String id) {
		Validate.notNull(id, "id is null.");
		return this.dungeons.get(id);
	}

	/**
	 * 登録されたダンジョン情報一覧を返します。
	 *
	 * @return 登録されたダンジョン一覧情報
	 */
	public Collection<IDungeon> getDungeons() {
		return this.dungeons.values();
	}

	/**
	 * 登録されたダンジョン情報が一つも無い場合<code>true</code>を返します。
	 *
	 * @return 登録されたダンジョン情報が一つも無い場合は<code>true</code>
	 */
	public boolean isEmpty() {
		return this.dungeons.isEmpty();
	}

}
