package net.tkarura.resourcedungeons.core;

import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import net.tkarura.resourcedungeons.core.command.CommandManager;

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
	
	public final static String VERSION = "Alpha-1.0.0";
	
	// instance.
	private final static ResourceDungeons instance = new ResourceDungeons();
	
	// DungeonManager.
	private final DungeonManager dungeons = new DungeonManager();
	
	// logger.
	private Logger log = Logger.getLogger("ResourceDungeons");
	
	// Setting
	private SettingManager setting = SettingManager.getInstance();
	
	// Commands
	private CommandManager commands = new CommandManager();
	
	// Contractor.
	private ResourceDungeons() {}
	
	/**
	 * ResourceDungeonsの初期化を行います。
	 * <b><u>ResourceDungeonsに関わるあらゆる処理は全てこのメソッドの後に定義してください。</u></b>
	 * nullを設定した状態で呼び出した場合
	 */
	public void init() {
		
		log.info("Start Initialize."); // 初期化開始時の通知
		
		// 設定構成を初期化
		setting.init();
		log.info("initialize Settings.");
		
		// 設定構成を読み込み
		setting.load();
		log.info("Loading Settings.");
		
		// Configの情報からディレクトリを生成
		setting.getSettingDirectory().mkdirs();
		setting.getDungeonsDirectory().mkdirs();
		setting.getScriptsDirectory().mkdirs();
		
		// DungeonManagerを初期化
		dungeons.init();
		log.info("Initialize DungeonManager.");
		
		// Dungeonの読み込み
		dungeons.load(SettingManager.getInstance().getDungeonsDirectory());
		log.info("Loading DungeonManager.");
		
		log.info("Complate Initialize."); // 初期化終了の通知
		
	}
	
	/**
	 * 現在の設定を保管します。
	 */
	public void save() {
		
		try {
			
			// 設定構成の保管
			setting.save();
			
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
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
	
	public static SettingManager getSetting() {
		return instance.setting;
	}
	
	/**
	 * Dungeon管理クラスを返します。
	 * @return DungeonManagerクラス
	 */
	public DungeonManager getDungeonManager() {
		return this.dungeons;
	}
	
	public CommandManager getCommands() {
		return this.commands;
	}
	
}
