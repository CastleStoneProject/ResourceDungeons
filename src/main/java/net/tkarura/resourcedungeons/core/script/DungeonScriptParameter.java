package net.tkarura.resourcedungeons.core.script;

import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.server.IDungeonWorld;
import net.tkarura.resourcedungeons.core.session.SessionManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * スクリプト動作を管理するマネージャークラスです。
 *
 * @author the_karura
 */
public final class DungeonScriptParameter {

    public final static ClassLoader DEFAULT_CLASS_LOADER = Thread.currentThread().getContextClassLoader();
    public final static String DEFAULT_SCRIPT_NAME = "javascript";
    public final static String DEFAULT_MAIN_FUNCTION_NAME = "main";
    public final static String ZIPFILE_ENCORD = "UTF-8";

    private ClassLoader class_loader = DEFAULT_CLASS_LOADER;            // スクリプト呼び出しに使用するクラスローダー
    private String engine_name = DEFAULT_SCRIPT_NAME;                    // スクリプトの種類
    private String main_function_name = DEFAULT_MAIN_FUNCTION_NAME;    // 最初に呼び出される関数名

    private IDungeon dungeon;
    private IDungeonWorld world;
    private SessionManager sessions;

    private int x = 0;
    private int y = 0;
    private int z = 0;

    public DungeonScriptParameter(IDungeon dungeon, IDungeonWorld world, SessionManager sessions) {
        this.dungeon = dungeon;
        this.world = world;
        this.sessions = sessions;
    }

    public void setBaseLoc(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * スクリプト呼び出しに使用するクラスローダを設定します。 <u>クラスローダによってはスクリプト言語が呼び出せない場合もあります。</u>
     *
     * @param loader クラスローダ
     */
    public void setScriptClassLoader(ClassLoader loader) {
        this.class_loader = loader;
    }

    /**
     * 使用するスクリプト言語を設定します。
     *
     * @param script_name 使用するスクリプト言語
     */
    public void setScriptName(String script_name) {
        this.engine_name = script_name;
    }

    /**
     * 最初に呼び出す関数名を指定します。 <u>一つの引数が指定されている関数名でないと呼び出されません。</u>
     * 例: main(handle) or main(handle, param1) etc...
     *
     * @param function_name 最初に呼び出す関数名を設定します。
     */
    public void setMainFunctionName(String function_name) {
        this.main_function_name = function_name;
    }

    /**
     * 使用するクラスローダを返します。
     *
     * @return クラスローダ
     */
    public ClassLoader getUseClassLoader() {
        return this.class_loader;
    }

    /**
     * 使用するエンジン名を返します。
     *
     * @return エンジン名
     */
    public String getEngineName() {
        return this.engine_name;
    }

    /**
     * 最初に呼び出される関数名を返します。
     *
     * @return 関数名
     */
    public String getMainFunctionName() {
        return this.main_function_name;
    }

    public IDungeon getDungeon() {
        return this.dungeon;
    }

    public IDungeonWorld getWorld() {
        return world;
    }

    public SessionManager getSessions() {
        return sessions;
    }

    public int getBaseX() {
	    return this.x;
    }

    public int getBaseY() {
        return this.y;
    }

    public int getBaseZ() {
        return this.z;
    }

}
