package net.tkarura.resourcedungeons.core.dungeon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

import net.tkarura.resourcedungeons.core.DungeonManager;

/**
 * ダンジョン情報を保持するクラスです。
 * 
 * @author the_karura
 */
public class Dungeon {
	
	/**
	 * 本体が識別するの必要な情報
	 */
	protected final String id;
	
	protected short support;
	protected boolean enable = true;
	protected File directory;
	
	// オプション情報 (ユーザ側が設定する情報)
	protected String name = "Empty Name.";
	protected String version = "Empty Version.";
	protected String discription = "";
	protected List<UserData> authors = new ArrayList<UserData>();
	protected List<UserData> contributors = new ArrayList<UserData>();
	
	/**
	 * 識別用のIDを指定して生成します。
	 * @param id 識別用ID
	 */
	public Dungeon(String id) {
		Validate.notNull(id, "id is null.");
		this.id = id;
	}
	
	/**
	 * ダンジョン情報を複製します。
	 * @param dungeon 複製する情報
	 */
	public Dungeon(Dungeon dungeon) {
		this(dungeon, dungeon.id);
	}
	
	/**
	 * 識別用IDを変更してダンジョン情報を複製します。
	 * @param dungeon 複製するダンジョン情報
	 * @param id 変更する識別ID情報
	 */
	public Dungeon(Dungeon dungeon, String id) {
		Validate.notNull(dungeon, "dungeon is null.");
		Validate.notNull(id, "id is null.");
		this.id = new String(id);
		this.support = dungeon.support;
		this.enable = dungeon.enable;
		this.directory = new File(dungeon.directory.getPath());
		this.name = new String(dungeon.name);
		this.version = new String(dungeon.version);
		this.discription = new String(dungeon.discription);
		this.authors = new ArrayList<UserData>(dungeon.authors);
		this.contributors = new ArrayList<UserData>(dungeon.contributors);
	}
	
	// Setter.
	
	/**
	 * サポート情報を設定します。
	 * @param support サポートバージョン
	 */
	public void setSupport(short support) {
		Validate.notNull(support, "support is null.");
		this.support = support;
	}
	
	/**
	 * ダンジョンディレクトリを設定します。
	 * @param directory ダンジョン情報が格納されたディレクトリ
	 */
	public void setDirectory(File directory) {
		Validate.notNull(directory, "directory is null.");
		this.directory = directory;
	}
	
	/**
	 * ダンジョンの名前を設定します。
	 * @param name ダンジョン名
	 */
	public void setName(String name) {
		Validate.notNull(name, "name is null.");
		this.name = name;
	}
	
	/**
	 * ダンジョンのバージョンを設定します。
	 * @param version バージョン情報
	 */
	public void setVersion(String version) {
		Validate.notNull(version, "version is null.");
		this.version = version;
	}
	
	/**
	 * ダンジョンの説明を設定します・
	 * @param discription ダンジョンの説明
	 */
	public void setDiscription(String discription) {
		Validate.notNull(discription, "discription is null.");
		this.discription = discription;
	}
	
	/**
	 * 製作者情報を追加します。
	 * @param user 追加する製作者情報
	 */
	public void addAuthor(UserData user) {
		Validate.notNull(user, "user is null.");
		this.authors.add(user);
	}
	
	/**
	 * 貢献者情報を追加します。
	 * @param user 追加する貢献者情報
	 */
	public void addContributor(UserData user) {
		Validate.notNull(user, "user is null.");
		this.contributors.add(user);
	}
	
	// Executor.
	
	/**
	 * ダンジョン情報を無効化します。
	 * 無効化されたダンジョン情報は生成処理が実行されなくなります。
	 * 有効化に戻す事は出来ません。
	 */
	public void disable() {
		this.enable = false;
	}
	
	// Getter.
	
	/**
	 * 識別IDを返します。
	 * 識別IDは{@link DungeonManager}が複数あるダンジョン情報を識別する為のIDです。
	 * @return 識別ID
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * サポート情報を返します。
	 * ResourceDungeonsがサポート可能なバージョン値であるかを識別するのに使用します。
	 * @return サポートバージョン
	 */
	public short getSuppoert() {
		return this.support;
	}
	
	/**
	 * ダンジョンディレクトリを返します。
	 * ダンジョン情報を構成するフォルダー直下のファイルにアクセスする場合に使用します。
	 * @return ダンジョン情報が格納されたディレクトリ
	 */
	public File getDirectory() {
		return this.directory;
	}
	
	/**
	 * ダンジョン情報が有効であるかを返します。
	 * @return 有効化の場合はtrue
	 */
	public boolean isEnable() {
		return this.enable;
	}
	
	/**
	 * ダンジョンの名前を返します。
	 * @return ダンジョン名
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * バージョン情報を返します。
	 * @return バージョン情報
	 */
	public String getVersion() {
		return this.version;
	}
	
	/**
	 * ダンジョンに関する説明を返します。
	 * @return ダンジョンに関する説明
	 */
	public String getDiscription() {
		return this.discription;
	}
	
	/**
	 * 製作者情報一覧を返します。
	 * @return 製作者情報が格納されたList情報
	 */
	public List<UserData> getAuthors() {
		return new ArrayList<UserData>(authors);
	}
	
	/**
	 * 貢献者情報一覧を返します。
	 * @return 貢献者情報が格納されたList情報
	 */
	public List<UserData> getContributors() {
		return new ArrayList<UserData>(contributors);
	}
	
	/** Eclipseからの自動生成 */
	@Override
	public String toString() {
		return "Dungeon [" + (id != null ? "id=" + id + ", " : "") + "support=" + support + ", enable=" + enable + ", "
				+ (directory != null ? "directory=" + directory + ", " : "")
				+ (name != null ? "name=" + name + ", " : "") + (version != null ? "version=" + version + ", " : "")
				+ (discription != null ? "discription=" + discription + ", " : "")
				+ (authors != null ? "authors=" + authors + ", " : "")
				+ (contributors != null ? "contributors=" + contributors + ", " : "")
				+ "]";
	}
	
}
