package net.tkarura.resourcedungeons.core.exception;

import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.script.IDungeonScript;

public class DungeonScriptRunException extends DungeonScriptException {

    private static final long serialVersionUID = 1L;
    
    public DungeonScriptRunException() {
	super();
    }
    
    public DungeonScriptRunException(String message) {
	super(message);
    }
    
    public DungeonScriptRunException(String message, IDungeon dungeon) {
	super(message, dungeon);
    }
    
    public DungeonScriptRunException(String message, IDungeon dungeon, IDungeonScript script) {
	super(message, dungeon, script);
    }
    
}
