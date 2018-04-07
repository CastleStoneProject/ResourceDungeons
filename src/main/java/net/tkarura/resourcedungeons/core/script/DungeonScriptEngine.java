package net.tkarura.resourcedungeons.core.script;

import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.dungeon.IDungeonScript;
import net.tkarura.resourcedungeons.core.exception.DungeonScriptException;
import net.tkarura.resourcedungeons.core.exception.DungeonScriptRunException;
import net.tkarura.resourcedungeons.core.server.IDungeonWorld;
import net.tkarura.resourcedungeons.core.session.SessionManager;

import javax.script.*;
import java.beans.Expression;
import java.io.*;
import java.util.ArrayList;

/**
 * 一つのスクリプト動作を表すクラスです。
 */
public final class DungeonScriptEngine {

    public final static String DEFAULT_MAIN_FUNCTION_NAME   = "generate";

    private GenerateHandle handle;
    private ScriptEngine engine;

    private String main_function_name                       = DEFAULT_MAIN_FUNCTION_NAME;
    private File script_ext_lib;

    public DungeonScriptEngine(IDungeon dungeon) {
        this(dungeon, null);
    }

    public DungeonScriptEngine(IDungeon dungeon, ClassLoader loader) {
        this.handle = new GenerateHandle();
        this.handle.dungeon = dungeon;
        setupScriptEngine(loader);
    }

    private void setupScriptEngine(ClassLoader loader) {

        // スクリプトを用意
        ScriptEngineManager sem = new ScriptEngineManager(loader);
        this.engine = getNashronEngine(sem);

    }

    private ScriptEngine getNashronEngine(ScriptEngineManager engineManager) {
        ScriptEngine engine = null;

        for (ScriptEngineFactory factory : engineManager.getEngineFactories()) {
            if (!factory.getNames().contains("nashorn")) {
                continue;
            }

            // 特定の環境がクラスパスを通していない可能性があるので
            // リフレクションを使いNashornScriptEngineFactory#getScriptEngine(String...)に介します。
            // --no-java オプションを使いスクリプトからJavaへのアクセスを抑制します。
            // ただしJavaから渡されたオブジェクトなどは扱えます。
            ArrayList<String> options = new ArrayList<>();
            options.add("--no-java");
            Expression exp = new Expression(factory, "getScriptEngine", new Object[] {options.toArray(new String[options.size()])});
            try {
                engine = (ScriptEngine) exp.getValue();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return engine;
    }

    public void setWorld(IDungeonWorld world) {
        handle.world = world;
    }

    public void setSessionManager(SessionManager sessions) {
        handle.sessions = sessions;
    }

    public void setBaseLocation(int x, int y, int z) {
        handle.base_x = x;
        handle.base_y = y;
        handle.base_z = z;
    }

    public void setScriptLibraryDir(File file) {
        this.script_ext_lib = file;
    }

    public void setMainFunctionName(String function_name) {
        this.main_function_name = function_name;
    }

    /**
     * ダンジョンに登録されたスクリプト情報を読み込みます。
     *
     * @throws DungeonScriptException
     */
    public void loadScripts() throws DungeonScriptException {

        if (engine == null) {
            throw new DungeonScriptException("engine is null.");
        }

        // APIの読み込み
        if (script_ext_lib != null) {

            try {
                DungeonScriptAPI dsa = new DungeonScriptAPI(script_ext_lib);
                dsa.load();
                dsa.loadScripts(this.engine);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        // ダンジョン情報に格納されたスクリプト情報を読み込み
        for (IDungeonScript script : handle.dungeon.getScripts()) {
            readScript(script);
        }

    }

    public void readScript(IDungeonScript script) throws DungeonScriptException {

        if (engine == null) {
            throw new DungeonScriptException("engine is null.");
        }

        try {
            script.read(engine);
        } catch (IOException | ScriptException e) {
            throw new DungeonScriptRunException(e.getLocalizedMessage());
        }

    }

    /**
     * クラス内で定義したメイン関数を呼び出します。
     *
     * @throws DungeonScriptException 関数呼び出しがサポートされていないスクリプト言語が選択されていた場合
     *                                実行中にエラーが起きた場合
     */
    public void callMainFunction() throws DungeonScriptException {
        this.callFunction(this.main_function_name);
    }

    /**
     * 関数名と引数を指定して関数を実行します。
     *
     * @param function_name 実行する関数名
     * @param args          呼び出す引数情報
     * @throws DungeonScriptException 実行中にエラーが起きた場合
     */
    public void callFunction(String function_name, Object... args) throws DungeonScriptException {

        if (engine == null) {
            throw new DungeonScriptException("engine is null.");
        }

        // 実行中の処理
        try {

            ((Invocable) engine).invokeFunction(function_name, handle, args);

        } catch (NoSuchMethodException e) {
            throw new DungeonScriptException(e.getLocalizedMessage());
        } catch (ScriptException e) {
            throw  new DungeonScriptException(e.getLocalizedMessage());
        }

    }

    /**
     * スクリプトエンジンの実行結果から格納されたセッション情報を全て実行します。
     */
    public void runSessions() {
        this.handle.runSessions();
    }

    public IDungeon getDungeon() {
        return handle.dungeon;
    }

    public IDungeonWorld getWorld() {
        return handle.world;
    }

    public SessionManager getSessionManager() {
        return handle.sessions;
    }

}
