package net.tkarura.resourcedungeons.core.exception;

import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.script.IDungeonScript;

public class DungeonScriptException extends DungeonException {
    
    private static final long serialVersionUID = 1L;
    
    protected IDungeonScript script;
    
    public DungeonScriptException() {
	super();
    }
    
    public DungeonScriptException(String message) {
	super(message);
    }
    
    public DungeonScriptException(String message, IDungeon dungeon) {
	super(message, dungeon);
    }
    
    public DungeonScriptException(String message, IDungeon dungeon, IDungeonScript script) {
	super(message, dungeon);
	this.script = script;
    }
    
}
