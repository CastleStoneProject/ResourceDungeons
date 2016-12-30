package net.tkarura.resourcedungeons.core.script;

import javax.script.ScriptEngine;

import net.tkarura.resourcedungeons.core.exception.DungeonScriptException;

public interface IDungeonScript {
    
    public void run(ScriptEngine engine) throws DungeonScriptException;
    
}
