package net.tkarura.resourcedungeons.core;

import java.io.File;
import java.util.logging.Logger;

import net.tkarura.resourcedungeons.core.command.*;
import net.tkarura.resourcedungeons.core.dungeon.DungeonManager;
import net.tkarura.resourcedungeons.core.loader.XMLDungeonLoader;
import net.tkarura.resourcedungeons.core.session.SessionManager;
import net.tkarura.resourcedungeons.core.session.SetBlockSession;
import net.tkarura.resourcedungeons.core.session.SetSchematicSession;

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

    public final static String PREFIX = "[RD]";
    public final static String PREFIX_MES = "&b[&6RD&f&b]&r";

    // version
    public final static String VERSION = "1.0.1";

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
    private DungeonInfoCommand info_command = new DungeonInfoCommand();

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
     * nullを設定した状態で呼び出した場合 {@link NullPointerException} が発生する恐れがあります。
     */
    public void init() {

        log.info("Start Resource Dungeons Initialize.");

        // このクラスのLoggerを設定
        this.dungeons.setLogger(this.log);

        // DungeonManagerを初期化
        this.dungeons.init();

        // xmlローダーを登録
        this.dungeons.addFileDungeonLoader(new XMLDungeonLoader(), "xml");

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
        this.sessions.registerSession(new SetSchematicSession());

        // CommandManagerを初期化
        this.commands.init();

        // サポートするコマンドを登録
        this.help_command.setCommandManager(this.commands);
        this.commands.register(this.help_command);
        this.list_command.setDungeonManager(this.dungeons);
        this.commands.register(this.list_command);
        this.commands.register(this.generate_command);
        this.generate_command.setDungeonManager(this.dungeons);
        this.generate_command.setSessionManager(this.sessions);
        this.info_command.setDungeonManager(this.dungeons);
        this.commands.register(this.info_command);

        log.info("End Resource Dungeons Initialized.");

    }

    /**
     * このクラスで設定されたログ情報を返します。
     *
     * @return ログ情報
     */
    public Logger getLogger() {
        return this.log;
    }

    /**
     * ダンジョン情報を格納するマネージャーを返します。
     *
     * @return DungeonManagerクラス
     */
    public DungeonManager getDungeonManager() {
        return this.dungeons;
    }

    /**
     * スクリプトから処理を行わせる処理を格納するマネージャーを返します。
     *
     * @return SessionManagerクラス
     */
    public SessionManager getSessionManager() {
        return this.sessions;
    }

    /**
     * コマンド情報を格納するマネージャーを返します。
     *
     * @return CommandManagerクラス
     */
    public CommandManager getCommandManager() {
        return this.commands;
    }

}
