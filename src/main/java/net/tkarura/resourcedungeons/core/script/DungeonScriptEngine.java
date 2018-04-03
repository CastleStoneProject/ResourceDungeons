package net.tkarura.resourcedungeons.core.script;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.dungeon.IDungeonScript;
import net.tkarura.resourcedungeons.core.exception.DungeonScriptException;
import net.tkarura.resourcedungeons.core.exception.DungeonScriptRunException;
import org.apache.commons.lang3.Validate;

import javax.script.*;
import java.io.*;
import java.security.Policy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 一つのスクリプト動作を表すクラスです。
 */
public final class DungeonScriptEngine {

    private DungeonScriptParameter param;       // スクリプト引数情報
    private GenerateHandle handle;              // ハンドル情報 スクリプト実行結果もこのクラス内に格納されます。
    private ScriptEngine engine;                // スクリプト処理に使用するクラス

    /**
     * 引数と実行結果を格納する情報を引数にスクリプト動作を生成
     *
     * @param param スクリプト引数
     */
    public DungeonScriptEngine(DungeonScriptParameter param) {
        Validate.notNull(param, "param can not be null.");
        this.param = param;
        this.handle = new GenerateHandle(param.getDungeon(), param.getWorld(), param.getSessions());
        setupScriptEngine();
    }

    private void setupScriptEngine() {

        // Java呼び出しを禁止した状態のエンジンを生成
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine("--no-java");
        this.engine = engine;

    }

    /**
     * ダンジョンに登録されたスクリプト情報を読み込みます。
     *
     * @throws DungeonScriptException
     */
    public void loadScripts() throws DungeonScriptException {

        // 関数呼び出しがサポートされているかを確認します。
        if (!(engine instanceof Invocable)) {
            throw new DungeonScriptRunException("Engine not supporting function processing.");
        }

        ClassLoader loader = this.param.getUseClassLoader();
        DungeonScriptAPI dsa = new DungeonScriptAPI(loader != null ? loader : ClassLoader.getSystemClassLoader());
        dsa.load();
        dsa.loadScripts(this.engine);

        handle.setBaseLoc(param.getBaseX(), param.getBaseY(), param.getBaseZ());

        // ダンジョン情報を取得
        IDungeon dungeon = param.getDungeon();

        // ダンジョン情報に格納されたスクリプト情報を読み込み
        for (IDungeonScript script : dungeon.getScripts()) {

            try {
                script.read(engine);
            } catch (IOException | ScriptException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * クラス内で定義したメイン関数を呼び出します。
     *
     * @throws DungeonScriptException 関数呼び出しがサポートされていないスクリプト言語が選択されていた場合
     *                                実行中にエラーが起きた場合
     */
    public void callMainFunction() throws DungeonScriptException {
        this.callFunction(this.param.getMainFunctionName());
    }

    /**
     * 関数名と引数を指定して関数を実行します。
     *
     * @param function_name 実行する関数名
     * @param args          呼び出す引数情報
     * @throws DungeonScriptException 関数呼び出しがサポートされていないスクリプト言語が選択されていた場合
     *                                実行中にエラーが起きた場合
     */
    public void callFunction(String function_name, Object... args) throws DungeonScriptException {

        // 実行中の処理
        try {

            ((Invocable) engine).invokeFunction(function_name, handle, args);

        } catch (NoSuchMethodException e) {

            // 呼び出し先の関数が見つからない場合
            throw new DungeonScriptException("No Such Function. " + function_name);

        } catch (ScriptException e) {

            // 呼び出し元へ返す例外メッセージ
            throw new DungeonScriptRunException(e.getLocalizedMessage());

        }

    }

    /**
     * スクリプトエンジンの実行結果から格納されたセッション情報を全て実行します。
     */
    public void runSessions() {
        this.handle.runSessions();
    }

    public GenerateHandle getHandle() {
        return this.handle;
    }

}
