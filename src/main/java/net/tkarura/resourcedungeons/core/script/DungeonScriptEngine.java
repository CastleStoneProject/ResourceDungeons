package net.tkarura.resourcedungeons.core.script;

import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.dungeon.IDungeonScript;
import net.tkarura.resourcedungeons.core.exception.DungeonScriptException;
import net.tkarura.resourcedungeons.core.exception.DungeonScriptRunException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;

/**
 * 一つのスクリプト動作を表すクラスです。
 */
public final class DungeonScriptEngine {

    private DungeonScriptParameter param;       // スクリプト引数情報
    private GenerateHandle handle;              // ハンドル情報 スクリプト実行結果もこのクラス内に格納されます。
    private ScriptEngineManager engine_manager; // スクリプトエンジンのマネージャー
    private ScriptEngine engine;                // スクリプト処理に使用するクラス

    /**
     * 引数と実行結果を格納する情報を引数にスクリプト動作を生成
     *
     * @param param スクリプト引数
     */
    public DungeonScriptEngine(DungeonScriptParameter param) {
        this.param = param;
        this.handle = new GenerateHandle(param.getDungeon(), param.getWorld(), param.getSessions());

        this.engine_manager = new ScriptEngineManager(param.getUseClassLoader());
        this.engine = engine_manager.getEngineByName(param.getEngineName());
    }

    /**
     * ダンジョンに登録されたスクリプト情報を読み込みます。
     *
     * @throws DungeonScriptException
     */
    public void runScript() throws DungeonScriptException {

        // 関数呼び出しがサポートされているかを確認します。
        if (!(engine instanceof Invocable)) {
            throw new DungeonScriptRunException("Engine not supporting function processing.");
        }

        IDungeon dungeon = param.getDungeon();

        for (IDungeonScript script : dungeon.getScripts()) {

            try {
                this.engine.eval(script.getReader());
            } catch (ScriptException | IOException e) {
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

        } catch (Exception e) {

            // デバッグ用
            e.printStackTrace();

            // 呼び出し元へ返す例外メッセージ
            throw new DungeonScriptRunException(e.getLocalizedMessage());

        }

    }

    /**
     * マネージャーに登録されたハンドル情報を返します。
     * @return ハンドル情報
     */
    public GenerateHandle getHandler() {
        return this.handle;
    }

}
