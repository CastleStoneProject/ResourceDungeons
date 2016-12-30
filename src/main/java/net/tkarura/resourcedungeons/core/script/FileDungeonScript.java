package net.tkarura.resourcedungeons.core.script;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import net.tkarura.resourcedungeons.core.exception.DungeonScriptException;
import net.tkarura.resourcedungeons.core.exception.DungeonScriptRunException;

public class FileDungeonScript implements IDungeonScript {

    private final File file;
    
    public FileDungeonScript(File file) {
	this.file = file;
    }
    
    @Override
    public void run(ScriptEngine engine) throws DungeonScriptException {
	
	try {
	    
	    engine.eval(new FileReader(file));
	    
	} catch (FileNotFoundException | ScriptException e) {
	    
	    // 例外情報を独自例外にラッピングして返します。
	    throw new DungeonScriptRunException(e.getMessage());
	    
	}
	
    }

    /* (非 Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "FileDungeonScript [" + (file != null ? "file=" + file : "") + "]";
    }

}
