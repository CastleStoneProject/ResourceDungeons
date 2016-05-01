package net.tkarura.resourcedungeons.core;

import java.io.File;
import java.util.logging.Logger;

import net.tkarura.resourcedungeons.core.command.Commands;
import net.tkarura.resourcedungeons.core.server.DungeonServer;

/**
 * ResourceDungeonsの本体 ResourceDungeonsに関わるクラスの初期化とクラスの管理を行います。
 * 
 * <p>クラスを使用する際は以下に注意してください。
 * <ul>
 * <li>クラスの呼び出しには{@link #getInstance()}を使用してください。
 * <li>クラス情報の初期化には{@link #init()}を呼び出してください。
 * <li>初期化する前に設定をしたい場合は{@link #init}を呼び出す前に行ってください。
 * </ul>
 * @author the_karura
 */
public final class ResourceDungeons {
	
	// instance.
	private final static ResourceDungeons instance = new ResourceDungeons();
	
	// logger.
	private Logger log = Logger.getLogger("ResourceDungeons");
	
	// Directors
	private File directory = new File("");
	private File dungeon_dir = new File(directory, "Dungeons");
	private File script_dir = new File(directory, "scripts");
	
	// Contractor.
	private ResourceDungeons() {}
	
	/**
	 * ResourceDungeonsが使用するディレクトリの設定をします。
	 * {@link #init()}の実行前に指定してください。
	 * @param dir 設定するディレクトリ
	 */
	public void setDirectory(String dir) {
		this.directory = new File(dir);
		this.dungeon_dir = new File(directory, "Dungeons");
		this.script_dir = new File(directory, "scripts");
	}
	
	/**
	 * 初期化を行います。
	 */
	public void init() {
		
		// 初期化開始の通知
		log.info("Initialize ResourceDungeons.");
		
		// フォルダーの生成
		if (!directory.exists()) {
			directory.mkdirs();
		}
		
		// ダンジョンフォルダーの生成
		if (!dungeon_dir.exists()) {
			dungeon_dir.mkdirs();
		}
		
		// 初期化終了の通知
		log.info("Complate Initialize.");
		
	}
	
	/**
	 * クラスインスタンスを返します。
	 * @return instance
	 */
	public static ResourceDungeons getInstance() {
		return instance;
	}
	
	/**
	 * ログクラスを返します。
	 * @return Loggerクラス
	 */
	public static Logger getLogger() {
		return instance.log;
	}
	
	/**
	 * ResourceDungeonsのディレクトリを返します。
	 * @return ディレクトリ情報
	 */
	public File getDirectory() {
		return this.directory;
	}
	
	/**
	 * ダンジョン情報を格納するディレクトリを返します。
	 * @return ディレクトリ情報
	 */
	public File getDungeonsDirectory() {
		return this.directory;
	}
	
	/**
	 * スクリプト情報を格納するディレクトリを返します。
	 * @return ディレクトリ情報
	 */
	public File getScriptsDirectory() {
		return this.script_dir;
	}
	
}
