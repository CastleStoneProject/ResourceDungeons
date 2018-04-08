package net.tkarura.resourcedungeons.core.dungeon;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang3.SystemUtils;
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
	public void addFileDungeonLoader(FileDungeonLoader loader, String... extendz) {

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

		// 存在しない場合
		if (!dir.exists()) {
		    log.warning("directory not found. : " + dir.getName());
		    return;
        }

        // ファイルの場合
        if (!dir.isDirectory()) {
		    log.warning(dir.getName() + " is not directory.");
		    return;
        }

		loadFolders(dir);

	}

	private void loadFolders(File dirs) {

	    for (File dir : dirs.listFiles((FileFilter) FileFilterUtils.directoryFileFilter())) {
	        loadDungeonFolders(dir);
        }

    }

    private void loadDungeonFolders(File dirs) {

	    FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {

                if (!pathname.isFile()) {
                    return false;
                }

                String file_name = pathname.getName();

                int index = file_name.lastIndexOf('.');

                if (index == -1) {
                    return false;
                }

                String name = file_name.substring(0, index);

                if (!name.equalsIgnoreCase("dungeon")) {
                    return false;
                }

                return true;
            }
        };

	    for (File dir : dirs.listFiles((FileFilter) FileFilterUtils.directoryFileFilter())) {
	        for (File file : dir.listFiles(fileFilter)) {
	            loadDungeon(file);
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

				// エラーが発生した場合は登録をしない
				if (!loader.getLogHandleList().isErrorLog()) {

                    // ダンジョン情報を登録します。
                    registerDungeon(dungeon);

                }

			}

		} catch (DungeonLoadException | IllegalArgumentException e) {

		    if (loader != null) {
                LoadLogHandleList list = loader.getLogHandleList();
                list.add(Level.SEVERE, e.getLocalizedMessage());
            }

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
			throw new IllegalArgumentException("Double definition. id : " + dungeon.getId());

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
