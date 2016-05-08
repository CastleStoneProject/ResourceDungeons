package net.tkarura.resourcedungeons.core.dungeon;

import org.apache.commons.lang.Validate;

/**
 * ダンジョン生成に関わるスクリプト情報を保管するクラスです。
 * @author the_karura
 */
public class DungeonScript {
	
	private ScriptType type;
	private String script = "";
	
	/**
	 * スクリプト情報とスクリプトタイプを指定して生成します。
	 * @param script スクリプト情報
	 * @param type スクリプトタイプ
	 */
	public DungeonScript(String script, ScriptType type) {
		Validate.notNull(script, "script is null.");
		Validate.notNull(type, "type is null.");
		this.script = script;
		this.type = type;
	}
	
	/**
	 * スクリプト情報を返します。
	 * スクリプトが何を表すかの情報は{{@link #getScriptType()}を使用してください。
	 * @return スクリプト情報
	 */
	public String getScript() {
		return this.script;
	}
	
	/**
	 * スクリプトタイプを表します。
	 * {@link #getScript()}を使用する前にこちらで何を表すかを明確にしてください。
	 * @return スクリプトタイプ
	 */
	public ScriptType getScriptType() {
		return this.type;
	}
	
}
