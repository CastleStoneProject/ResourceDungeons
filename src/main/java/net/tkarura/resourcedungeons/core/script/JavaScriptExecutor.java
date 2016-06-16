package net.tkarura.resourcedungeons.core.script;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.tkarura.resourcedungeons.core.ResourceDungeons;
import net.tkarura.resourcedungeons.core.dungeon.Dungeon;
import net.tkarura.resourcedungeons.core.dungeon.DungeonScript;
import net.tkarura.resourcedungeons.core.exception.DungeonGenerateException;
import net.tkarura.resourcedungeons.core.nbt.DNBTBase;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagByte;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagByteArray;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagCompound;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagDouble;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagFloat;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagInt;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagIntArray;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagList;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagLong;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagShort;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagString;
import net.tkarura.resourcedungeons.core.server.DungeonLocation;
import net.tkarura.resourcedungeons.core.util.FileHandler;

/**
 * ダンジョンを生成するクラスです。
 * @author the_karura
 */
public class JavaScriptExecutor implements ScriptExecutor {
	
	// 実行に使用するスクリプト
	private ScriptEngine engine = null;
	
	// 生成処理を行うダンジョン情報
	private Dungeon dungeon;
	
	/**
	 * ダンジョン情報を指定して生成します。
	 * @param dungeon
	 */
	public JavaScriptExecutor(Dungeon dungeon) {
		this.dungeon = dungeon;
	}
	
	/**
	 * 実行前に必要な情報を初期化します。
	 * @throws FileNotFoundException
	 * @throws ScriptException
	 */
	@Override
	public void init() throws DungeonGenerateException {
		
		// スクリプトディレクトリを呼び出します。
		File script_dir = ResourceDungeons.getInstance().getScriptsDirectory();
		
		// javascriptを呼び出します。
		this.engine = new ScriptEngineManager().getEngineByName("javascript");
		
		try {
			
			// スクリプトに必要なクラスを関連付けします。
			this.registerJavaClass(DNBTBase.class);
			this.registerJavaClass(DNBTTagByte.class);
			this.registerJavaClass(DNBTTagShort.class);
			this.registerJavaClass(DNBTTagInt.class);
			this.registerJavaClass(DNBTTagLong.class);
			this.registerJavaClass(DNBTTagFloat.class);
			this.registerJavaClass(DNBTTagDouble.class);
			this.registerJavaClass(DNBTTagByteArray.class);
			this.registerJavaClass(DNBTTagString.class);
			this.registerJavaClass(DNBTTagList.class);
			this.registerJavaClass(DNBTTagCompound.class);
			this.registerJavaClass(DNBTTagIntArray.class);
			
			// スクリプトディレクトリが無ければ例外を出します。
			if (!script_dir.isDirectory()) {
				throw new FileNotFoundException("Directory is not folder.");
			}
			
			// スクリプトディレクトリ内のスクリプト情報を全て読み込みます。
			loadFolder(script_dir);
			
		} catch (FileNotFoundException | ScriptException e)	{
			throw new DungeonGenerateException(e.getMessage());
		}
		
	}
	
	// 引数で指定されたクラスをエンジンに登録します。
	private void registerJavaClass(Class<?> clazz) throws ScriptException {
		this.engine.put(clazz.getSimpleName() + "JavaClass", clazz);
		this.engine.eval("var " + clazz.getSimpleName() + " = " + clazz.getSimpleName() + "JavaClass.static;");
	}
	
	// 再帰処理を使用して登録された情報一覧を習得します。
	private void loadFolder(File folder) throws FileNotFoundException, ScriptException {
		
		for (File file : folder.listFiles()) {
			
			if (file.isDirectory()) {
				loadFolder(file);
			}
			
			if (FileHandler.JAVA_SCRIPT_FILTER.accept(file)) {
				engine.eval(new FileReader(file));
			}
		}
	}
	
	/**
	 * スクリプトを実行します。
	 * 実行する前に必ず一度{@link #init()}で情報を初期化してください。
	 * @param loc 実行する位置情報
	 * @param function_name 実行を開始する関数名 (関数には1つの引数が必要です。)
	 * @throws DungeonGenerateException 実行中に例外が発生した場合
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void execute(DungeonLocation loc, String function_name) throws DungeonGenerateException {
		
		try {
			
			// 登録されたスクリプト情報一覧をループで実行
			for (DungeonScript script : dungeon.getScripts()) {
				
				// スクリプト対応別に処理
				switch (script.getScriptType()) {
				
				// ファイル位置情報
				case FILE_LOCATION:
					
					engine.eval(new FileReader(new File(dungeon.getDirectory(), script.getScript())));
					
					break;
					
				// 文字列情報
				case TEXT_CONTENT:
					
					engine.eval(script.getScript());
					
					break;
				}
				
			}
			
			Invocable invocable = (Invocable) engine;
			
			// 非推奨クラスだが理由を元にサポートされます。
			invocable.invokeFunction(function_name, new ScriptHandle(dungeon, loc));
			
		} catch (ScriptException | FileNotFoundException | NoSuchMethodException e) {
			throw new DungeonGenerateException(e.getLocalizedMessage());
		}
		
	}
	
}
