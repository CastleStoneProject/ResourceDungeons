package net.tkarura.resourcedungeons.core;

import java.io.File;
import java.util.logging.Logger;

import net.tkarura.resourcedungeons.core.dungeon.DungeonManager;
import net.tkarura.resourcedungeons.core.loader.XMLDungeonLoader;
import net.tkarura.resourcedungeons.core.script.handle.GenerateHandle;
import net.tkarura.resourcedungeons.core.script.handle.HandleManager;
import net.tkarura.resourcedungeons.core.script.handle.SchematicHandle;

/**
 * ResourceDungeonsの本体 ResourceDungeonsに関わるクラスの初期化とクラスの管理を行います。
 * 
 * <p>
 * クラスを使用する際は以下に注意してください。
 * <ul>
 * <li>クラスの呼び出しには{@link #getInstance()}を使用してください。
 * <li>クラス情報の初期化には{@link #init()}を呼び出してください。
 * <li>初期化する前に設定をしたい場合は{@link #init}を呼び出す前に行ってください。
 * </ul>
 * 
 * @author the_karura
 */
public final class ResourceDungeons {

    // version
    public final static String VERSION = "Alpha-1.0.0";

    // logger.
    public final static Logger LOG = Logger.getLogger("ResourceDungeons");

    // instance.
    private final static ResourceDungeons instance = new ResourceDungeons();

    // dungeon directory.
    private File dungeons_dir = new File("Dungeons");
    
    // Managers
    private final DungeonManager dungeons = new DungeonManager();
    private final HandleManager handles = new HandleManager();
    
    // Contractor.
    private ResourceDungeons() {}
    
    public void setDungeonDirectory(File dir) {
	this.dungeons_dir = dir;
    }
    
    /**
     * ResourceDungeonsの初期化を行います。
     * <b><u>ResourceDungeonsに関わるあらゆる処理は全てこのメソッドの後に定義してください。</u></b>
     * nullを設定した状態で呼び出した場合
     */
    public void init() {

	LOG.info("Start Initialize."); // 初期化開始時の通知

	// DungeonManagerを初期化
	this.dungeons.init();
	
	LOG.info("Initialize DungeonManager.");

	// xmlローダーを登録
	String[] xml_extends = {"xml"};
	this.dungeons.addFileDungeonLoader(new XMLDungeonLoader(), xml_extends);
	
	// Dungeonの読み込み
	this.dungeons.loadDungeons(this.dungeons_dir);
	
	this.handles.init();
	
	this.handles.registerHandle(new GenerateHandle(null));
	this.handles.registerHandle(new SchematicHandle(null));
	
	LOG.info("Loading DungeonManager.");
	
	if (this.dungeons.isEmpty()) {
	    LOG.warning("not dungeon load.");
	}

	LOG.info("Complate Initialize."); // 初期化終了の通知

    }

    /**
     * クラスインスタンスを返します。
     * 
     * @return instance
     */
    public static ResourceDungeons getInstance() {
	return instance;
    }
    
    /**
     * Dungeon管理クラスを返します。
     * 
     * @return DungeonManagerクラス
     */
    public DungeonManager getDungeonManager() {
	return this.dungeons;
    }
    
    public HandleManager getHandleManager() {
	return this.handles;
    }
    
}
