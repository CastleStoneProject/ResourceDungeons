package net.tkarura.resourcedungeons.core;

import java.io.File;
import java.util.logging.Logger;

import net.tkarura.resourcedungeons.core.command.CommandManager;
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
	
	// DungeonManager.
	private final DungeonManager dungeons = new DungeonManager();
	
	// logger.
	private Logger log = Logger.getLogger("ResourceDungeons");
	
	// Directors
	private File directory = new File("");
	private File dungeon_dir = new File(directory, "Dungeons");
	private File script_dir = new File(directory, "scripts");
	
	// Commands
	private CommandManager commands = new CommandManager();
	
	// Server Bridge
	private DungeonServer server;
	
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
	 * 処理を受け渡す中間クラスを設定します。
	 * @param server サーバー情報
	 */
	public void setServer(DungeonServer server) {
		this.server = server;
	}
	
	/**
	 * 初期化を行います。
	 * @throws NullPointerException 
	 * 初期化を行う前に{@link #setServer(DungeonServer)}でサーバー情報を設定せずに実行もしくは
	 * nullを設定した状態で呼び出した場合
	 */
	public void init() {
		
		if (server == null)
			throw new NullPointerException("DungeonServer is null. please call method setServer(DungeonServer)");
		
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
		
		// DungeonManagerを初期化
		dungeons.init();
		
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
	 * サーバー中間クラスを返します。
	 * @return サーバー中間クラス
	 * @throws NullPointerException 
	 * 初期化を行う前に{@link #setServer(DungeonServer)}でサーバー情報を設定せずに実行もしくは
	 * nullを設定した状態で呼び出した場合
	 */
	public static DungeonServer getServer() {
		if (instance.server == null)
			throw new NullPointerException("DungeonServer is null. please call method setServer(DungeonServer)");
		return instance.server;
	}

	/**
	 * ログクラスを返します。
	 * @return Loggerクラス
	 */
	public static Logger getLogger() {
		return instance.log;
	}
	
	/**
	 * Dungeon管理クラスを返します。
	 * @return DungeonManagerクラス
	 */
	public DungeonManager getDungeonManager() {
		return this.dungeons;
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
		return this.dungeon_dir;
	}
	
	/**
	 * スクリプト情報を格納するディレクトリを返します。
	 * @return ディレクトリ情報
	 */
	public File getScriptsDirectory() {
		return this.script_dir;
	}
	
	public CommandManager getCommands() {
		return this.commands;
	}
	
}
