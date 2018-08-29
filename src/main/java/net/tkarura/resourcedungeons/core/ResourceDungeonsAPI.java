package net.tkarura.resourcedungeons.core;

import net.tkarura.resourcedungeons.core.command.CommandManager;
import net.tkarura.resourcedungeons.core.command.DungeonCommand;
import net.tkarura.resourcedungeons.core.dungeon.DungeonManager;
import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.generate.DungeonGenerateCheck;
import net.tkarura.resourcedungeons.core.loader.FileDungeonLoader;
import net.tkarura.resourcedungeons.core.script.DungeonScriptEngine;
import net.tkarura.resourcedungeons.core.script.GenerateHandle;
import net.tkarura.resourcedungeons.core.server.IDungeonWorld;
import net.tkarura.resourcedungeons.core.session.SessionManager;

import java.io.File;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * ResouceDungeonsに対し簡易的なアクセスを提供します。
 */
public final class ResourceDungeonsAPI {

    private final static ResourceDungeons instance = new ResourceDungeons();

    private ResourceDungeonsAPI() {}

    /**
     * ログ情報を設定します。
     * @param logger ログ情報
     * @throws IllegalArgumentException logger引数にnullを指定した場合
     */
    public static void setLogger(Logger logger) {
        instance.setLogger(logger);
    }

    public static void setLibraryDirectory(File dir) {
        instance.setLibraryDirectory(dir);
    }

    /**
     * ダンジョンを読み込む為のディレクトリ情報を指定します。
     * @param dir ダンジョンディレクトリ
     * @throws IllegalArgumentException dir引数にnullを指定した場合
     */
    public static void setDungeonDirectory(File dir) {
        instance.setDungeonDirectory(dir);
    }

    /**
     * ResouceDungeonsの初期化を行います。
     *
     * コマンド情報の読み込み
     * ダンジョンディレクトリからのダンジョン情報の読み込み
     * ダンジョン生成に必要なセッション情報の読み込み
     */
    public static void init() {
        instance.init();
    }

    /**
     * コマンドを登録します。
     * 登録済みのコマンド名を使用すると上書きされます。
     * @param command 登録するコマンド情報
     */
    public static void registerCommand(DungeonCommand command) {
        instance.getCommandManager().register(command);
    }

    /**
     * 登録されたコマンド情報を削除します。
     * 登録されていないコマンド名を指定した場合は何も処理されずに終了します。
     * @param command
     */
    public static void unRegisterCommand(String command) {
        instance.getCommandManager().unRegister(command);
    }

    /**
     * ダンジョン情報を読み込む為のローダを登録します。
     *
     * @param loader 登録するローダ情報
     * @param extendz 対応する拡張子
     */
    public static void addFileDungeonLoader(FileDungeonLoader loader, String... extendz) {
        instance.getDungeonManager().addFileDungeonLoader(loader, extendz);
    }

    /**
     * ファイル情報からダンジョン情報を読み込みます。
     *
     * @param dir
     */
    public static void loadDungeon(File dir) {
        instance.getDungeonManager().loadDungeonFile(dir);
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
    public static IDungeon getDungeon(String id) {
        return instance.getDungeonManager().getDungeon(id);
    }

    /**
     * 登録されたダンジョン情報一覧を返します。
     *
     * @return 登録されたダンジョン一覧情報
     */
    public static Collection<IDungeon> getDungeons() {
        return instance.getDungeonManager().getDungeons();
    }

    /**
     * このクラスが提供するインスタンスを返します。
     *
     * @return インスタンス
     */
    public static ResourceDungeons getInstance() {
        return instance;
    }

    /**
     * このクラスで設定されたログ情報を返します。
     *
     * @return ログ情報
     */
    public static Logger getLogger() {
        return ResourceDungeons.getLogger();
    }

    /**
     * ダンジョン情報を格納するマネージャーを返します。
     *
     * @return DungeonManagerクラス
     */
    public static DungeonManager getDungeonManager() {
        return instance.getDungeonManager();
    }

    /**
     * スクリプトから処理を行わせる処理を格納するマネージャーを返します。
     *
     * @return SessionManagerクラス
     */
    public static SessionManager getSessionManager() {
        return instance.getSessionManager();
    }

    /**
     * コマンド情報を格納するマネージャーを返します。
     *
     * @return CommandManagerクラス
     */
    public static CommandManager getCommandManager() {
        return instance.getCommandManager();
    }

    public static DungeonGenerateCheck createGenerateCheck(IDungeonWorld world, int x, int y, int z, int width, int height, int length) {
        DungeonGenerateCheck dgc = new DungeonGenerateCheck(world, x, y, z, width, height, length);
        dgc.setWorlds(instance.getEnableWorlds());
        dgc.setDungeons(instance.getDungeonManager().getDungeons());
        return dgc;
    }

    public static DungeonScriptEngine createScriptEngine(GenerateHandle handle) {
        return createScriptEngine(handle.getDungeon(), handle.getWorld(), handle.getBaseX(), handle.getBaseY(), handle.getBaseZ());
    }

    public static DungeonScriptEngine createScriptEngine(GenerateHandle handle, ClassLoader class_loader) {
        return createScriptEngine(handle.getDungeon(), handle.getWorld(), handle.getBaseX(), handle.getBaseY(), handle.getBaseZ(), class_loader);
    }

    public static DungeonScriptEngine createScriptEngine(IDungeon dungeon, IDungeonWorld world, int base_x, int base_y, int base_z) {
        return settingScriptEngine(new DungeonScriptEngine(dungeon), world, base_x, base_y, base_z);
    }

    public static DungeonScriptEngine createScriptEngine(IDungeon dungeon, IDungeonWorld world, int base_x, int base_y, int base_z, ClassLoader class_loader) {
        return settingScriptEngine(new DungeonScriptEngine(dungeon, class_loader), world, base_x, base_y, base_z);
    }

    private static DungeonScriptEngine settingScriptEngine(DungeonScriptEngine engine, IDungeonWorld world, int base_x, int base_y, int base_z) {
        engine.setWorld(world);
        engine.setBaseLocation(base_x, base_y, base_z);
        engine.setScriptLibraryDir(instance.getLibraryDirectory());
        engine.setSessionManager(instance.getSessionManager());
        return engine;
    }

}
