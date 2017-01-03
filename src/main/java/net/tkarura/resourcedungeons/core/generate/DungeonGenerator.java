package net.tkarura.resourcedungeons.core.generate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.exception.DungeonScriptException;
import net.tkarura.resourcedungeons.core.exception.DungeonScriptRunException;

/**
 * ダンジョンの生成を行うクラスです。 生成に使用するスクリプトを呼び出す処理の定義やスクリプト言語を指定する場合はこちらで指定してください。
 * 
 * @author the_karura
 */
public class DungeonGenerator {

    /**
     * スクリプト呼び出しに使用されるクラスローダのデフォルト状態を表します。
     */
    public final static ClassLoader DEFAULT_CLASS_LOADER = Thread.currentThread().getContextClassLoader();

    /**
     * 使用するスクリプト言語のデフォルト状態を表します。
     */
    public final static String DEFAULT_SCRIPT_NAME = "javascript";

    /**
     * 呼び出し先の関数のデフォルト状態を表します。
     */
    public final static String DEFAULT_MAIN_FUNCTION_NAME = "main";

    private final IDungeon dungeon;

    private ClassLoader class_loader = DEFAULT_CLASS_LOADER;
    private String engine_name = DEFAULT_SCRIPT_NAME;
    private String main_function_name = DEFAULT_MAIN_FUNCTION_NAME;

    private ScriptEngineManager manager;
    private ScriptEngine engine;

    /**
     * ダンジョン情報を指定して生成します。
     * 
     * @param dungeon
     *            ダンジョン情報
     */
    public DungeonGenerator(IDungeon dungeon) {
	this.dungeon = dungeon;
    }

    /**
     * スクリプト呼び出しに使用するクラスローダを設定します。 <u>クラスローダによってはスクリプト言語が呼び出せない場合もあります。</u>
     * 
     * @param loader
     *            クラスローダ
     */
    public void setScriptClassLoader(ClassLoader loader) {
	this.class_loader = loader;
    }

    /**
     * 使用するスクリプト言語を設定します。
     * 
     * @param script_name
     *            使用するスクリプト言語
     */
    public void setScriptName(String script_name) {
	this.engine_name = script_name;
    }

    /**
     * 最初に呼び出す関数名を指定します。 <u>一つの引数が指定されている関数名でないと呼び出されません。</u>
     * 
     * @param function_name
     */
    public void setMainFunctionName(String function_name) {
	this.main_function_name = function_name;
    }

    /**
     * ダンジョンに登録されたスクリプト情報を読み込みます。
     * 
     * @throws DungeonScriptException
     */
    public void runScript() throws DungeonScriptException {

	// スクリプトエンジンを呼び出します。
	this.manager = new ScriptEngineManager(this.class_loader);
	this.engine = this.manager.getEngineByName(this.engine_name);

	// 定義ファイルのディレクトリにあるjsファイルを読み込み
	this.readFile(this.dungeon.getDirectory());
	
    }
    
    private void readFile(File dir) throws DungeonScriptException {

	// ディレクトリであるかの判別
	if (dir.isDirectory()) {

	    // ディレクトリ以下のファイルを検索
	    for (File dir_ : dir.listFiles()) {
		readFile(dir_);
	    }

	} else if (dir.isFile()) {

	    // スクリプトファイルの識別
	    if (!isScriptFile(dir)) {
		return;
	    }

	    // エンジンにスクリプトファイルを解析させます。
	    try {
		this.engine.eval(new FileReader(dir));
	    } catch (FileNotFoundException | ScriptException e) {
		throw new DungeonScriptException(e.getLocalizedMessage());
	    }

	}
    }

    private boolean isScriptFile(File file) {
	
	// 登録された拡張子から判別させます。
	for (String extend : this.engine.getFactory().getExtensions()) {
	    if (file.getName().endsWith(extend)) {
		return true;
	    }
	}
	
	// 該当のファイルでなければ
	return false;
    }

    /**
     * クラス内で定義したメイン関数を呼び出します。
     * 
     * @throws DungeonScriptException
     *             関数呼び出しがサポートされていないスクリプト言語が選択されていた場合
     *             実行中にエラーが起きた場合
     */
    public void callMainFunction(GenerateHandle handle) throws DungeonScriptException {
	this.callFunction(this.main_function_name, handle);
    }

    /**
     * 関数名と引数を指定して関数を実行します。
     * 
     * @param function_name
     *            実行する関数名
     * @param args
     *            呼び出す引数情報
     * @throws DungeonScriptException
     *             関数呼び出しがサポートされていないスクリプト言語が選択されていた場合
     *             実行中にエラーが起きた場合
     */
    public void callFunction(String function_name, Object... args) throws DungeonScriptException {

	// 関数呼び出しがサポートされていないスクリプト言語が選択されていた場合例外を発生させます。
	// 標準のJavaScriptはサポートされています。
	if (!(engine instanceof Invocable)) {
	    throw new DungeonScriptRunException("Engine not supporting function processing.");
	}

	// 実行中の処理
	try {

	    ((Invocable) engine).invokeFunction(function_name, args);

	} catch (NoSuchMethodException e) {

	    // 呼び出し先の関数が無い場合に発生する例外をラップします。
	    throw new DungeonScriptException(e.getLocalizedMessage());

	} catch (ScriptException e) {

	    // スクリプト実行中における汎用的な例外をラップします。
	    throw new DungeonScriptRunException(e.getLocalizedMessage());

	}

    }

}
