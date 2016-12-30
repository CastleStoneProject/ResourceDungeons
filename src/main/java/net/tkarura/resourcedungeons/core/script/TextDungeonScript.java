package net.tkarura.resourcedungeons.core.script;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import net.tkarura.resourcedungeons.core.exception.DungeonScriptException;
import net.tkarura.resourcedungeons.core.exception.DungeonScriptRunException;

public class TextDungeonScript implements IDungeonScript {
    
    private final String text;
    
    public TextDungeonScript(String text) {
	this.text = text;
    }
    
    @Override
    public void run(ScriptEngine engine) throws DungeonScriptException {
	
	try {
	    
	    engine.eval(text);
	    
	} catch (ScriptException e) {

	    // 例外情報を独自例外にラッピングして返します。
	    throw new DungeonScriptRunException(e.getMessage());
	    
	}
	
    }

    /* (非 Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "TextDungeonScript [" + (text != null ? "text=" + text : "") + "]";
    }

}
