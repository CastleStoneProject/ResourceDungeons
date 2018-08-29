package net.tkarura.resourcedungeons.core;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import net.tkarura.resourcedungeons.core.command.*;
import net.tkarura.resourcedungeons.core.dungeon.DungeonManager;
import net.tkarura.resourcedungeons.core.dungeon.DungeonPackage;
import net.tkarura.resourcedungeons.core.loader.XMLDungeonLoader;
import net.tkarura.resourcedungeons.core.session.SessionManager;
import net.tkarura.resourcedungeons.core.session.SetBlockSession;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;

/**
 * ResourceDungeonsの本体 ResourceDungeonsに関わるクラスの初期化とクラスの管理を行います。
 * 
 * このクラスで提供される機能は以下の通りです。
 * <ul>
 *     <li>パッケージの管理機能</li>
 *     <li>DungeonManagerの管理</li>
 *     <li>XML読み込みのサポート</li>
 *     <li>SessionManagerの管理</li>
 *     <li>幾つかのセッション機能をサポート</li>
 * </ul>
 * 
 * @author the_karura
 */
public final class ResourceDungeons {

    public final static String PREFIX = "[RD]";
    public final static String PREFIX_MES = "&b[&6RD&f&b]&r";

    // version
    public final static String VERSION = "1.0.2";

    // logger
    private static Logger log = Logger.getLogger("ResouceDungeons");

    // setting parameters.
    private File library_dir = new File("ResouceDungeons.jar");
    private File dungeons_dir = new File("Dungeons");
    private File temp_dir = new File("temp");
    private List<String> enable_worlds = new ArrayList<>();

    // Managers
    private final DungeonManager dungeons = new DungeonManager();
    private final SessionManager sessions = new SessionManager();
    private final CommandManager commands = new CommandManager();

    // Packages
    private final DungeonPackage defaultPackage = new DungeonPackage();
    private final DungeonPackage dungeonPackage = new DungeonPackage();

    private DungeonHelpCommand help_command = new DungeonHelpCommand();
    private DungeonListCommand list_command = new DungeonListCommand();
    private DungeonGenerateCommand generate_command = new DungeonGenerateCommand();
    private DungeonInfoCommand info_command = new DungeonInfoCommand();

    public ResourceDungeons() {
        defaultPackage.setPrefix("def_");
        dungeonPackage.setPrefix("pack_");
    }

    public static void setLogger(Logger log) {
        Validate.notNull(log, "log can not be null.");
        ResourceDungeons.log = log;
    }

    public void setLibraryDirectory(File dir) {
        Validate.notNull(dir, "dir can not be null.");
        this.library_dir = dir;
    }

    public void setDungeonDirectory(File dir) {
        Validate.notNull(dir, "dir can not be null.");
        this.dungeons_dir = dir;
    }

    public void setTemporaryDirectory(File temp_dir) {
        this.temp_dir = temp_dir;
    }

    public void addEnableWorld(String world_name) {
        this.enable_worlds.add(world_name);
    }

    public void removeEnableWorld(String world_name) {
        this.enable_worlds.remove(world_name);
    }

    /**
     * ResourceDungeonsの初期化を行います。
     * <b><u>ResourceDungeonsに関わるあらゆる処理は全てこのメソッドの後に定義してください。</u></b>
     */
    public void init() {

        log.info("Start Resource Dungeons Initialize.");

        // DungeonManagerを初期化
        this.dungeons.init();

        // xmlローダーを登録
        this.dungeons.addFileDungeonLoader(new XMLDungeonLoader(), "xml");

        // jar、zipなどに格納されたダンジョンデータを読み込み
        try {
            openDungeons();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Dungeonの読み込み
        this.dungeons.loadDungeons(this.dungeons_dir);

        // 一時ディレクトリからのダンジョンの読み込み
        this.dungeons.loadDungeons(this.temp_dir);

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

    private void openDungeons() throws IOException {

        // 既に生成されているディレクトリ情報を削除
        FileUtils.deleteDirectory(temp_dir);

        // 再度ディレクトリ情報を作成
        if (!temp_dir.mkdirs() && !temp_dir.exists()) {
            throw new IOException("can not create directory.");
        }

        // サポートするダンジョンを展開
        try {
            defaultPackage.openLibraryDungeons(library_dir, temp_dir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // zipファイルからダンジョンフォルダーを展開
        this.dungeonPackage.openPackageDungeons(dungeons_dir, temp_dir);

    }

    public static Logger getLogger() {
        return log;
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

    public Collection<String> getEnableWorlds() {
        return this.enable_worlds;
    }

    public File getLibraryDirectory() {
        return this.library_dir;
    }

}
