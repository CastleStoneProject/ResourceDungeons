package net.tkarura.resourcedungeons.core;

import java.io.File;
import java.util.logging.Logger;

import net.tkarura.resourcedungeons.core.command.CommandManager;
import net.tkarura.resourcedungeons.core.command.DungeonGenerateCommand;
import net.tkarura.resourcedungeons.core.command.DungeonHelpCommand;
import net.tkarura.resourcedungeons.core.command.DungeonListCommand;
import net.tkarura.resourcedungeons.core.dungeon.DungeonManager;
import net.tkarura.resourcedungeons.core.loader.XMLDungeonLoader;
import net.tkarura.resourcedungeons.core.session.SessionManager;
import net.tkarura.resourcedungeons.core.session.SetBlockSession;

/**
 * ResourceDungeonsの本体 ResourceDungeonsに関わるクラスの初期化とクラスの管理を行います。
 * 
 * このクラスで提供される機能は以下の通りです。
 * <ul>
 * <li>DungeonManagerの管理</li>
 * <li>XML読み込みのサポート</li>
 * <li>SessionManagerの管理</li>
 * <li>幾つかのセッション機能をサポート</li>
 * </ul>
 * 
 * @author the_karura
 */
public final class ResourceDungeons {

    // version
    public final static String VERSION = "Alpha-1.0.0";

    // dungeon directory.
    private File dungeons_dir = new File("Dungeons");

    // logger
    private Logger log = Logger.getLogger("ResouceDungeons");

    // Managers
    private final DungeonManager dungeons = new DungeonManager();
    private final SessionManager sessions = new SessionManager();
    private final CommandManager commands = new CommandManager();

    private DungeonHelpCommand help_command = new DungeonHelpCommand();
    private DungeonListCommand list_command = new DungeonListCommand();
    private DungeonGenerateCommand generate_command = new DungeonGenerateCommand();

    public ResourceDungeons() {
    }

    public void setDungeonDirectory(File dir) {
        this.dungeons_dir = dir;
    }

    public void setLogger(Logger log) {
        this.log = log;
    }

    /**
     * ResourceDungeonsの初期化を行います。
     * <b><u>ResourceDungeonsに関わるあらゆる処理は全てこのメソッドの後に定義してください。</u></b>
     * nullを設定した状態で呼び出した場合
     */
    public void init() {

        log.info("Start Resource Dungeons Initialize.");

        // このクラスのLoggerを設定
        this.dungeons.setLogger(this.log);

        // DungeonManagerを初期化
        this.dungeons.init();

        // xmlローダーを登録
        String[] xml_extends = {"xml"};
        this.dungeons.addFileDungeonLoader(new XMLDungeonLoader(), xml_extends);

        // Dungeonの読み込み
        this.dungeons.loadDungeons(this.dungeons_dir);

        // ダンジョンが一つも読み込まれていない場合に通知
        if (this.dungeons.isEmpty()) {
            log.warning("not dungeon loaded.");
        }

        // SessionManagerを初期化
        this.sessions.init();

        // サポートするSessionを登録
        this.sessions.registerSession(new SetBlockSession());

        // CommandManagerを初期化
        this.commands.init();

        // サポートするコマンドを登録
        this.commands.register(this.help_command);
        this.list_command.setDungeonManager(this.dungeons);
        this.commands.register(this.list_command);
        this.commands.register(this.generate_command);
        this.generate_command.setDungeonManager(this.dungeons);
        this.generate_command.setSessionManager(this.sessions);

        log.info("End Resource Dungeons Initialized.");

    }

    /**
     * Dungeon管理クラスを返します。
     *
     * @return DungeonManagerクラス
     */
    public DungeonManager getDungeonManager() {
        return this.dungeons;
    }

    public SessionManager getSessionManager() {
        return this.sessions;
    }

    public CommandManager getCommandManager() {
        return this.commands;
    }

}
