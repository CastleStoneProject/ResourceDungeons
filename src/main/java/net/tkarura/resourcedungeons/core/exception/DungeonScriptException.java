package net.tkarura.resourcedungeons.core.exception;

import net.tkarura.resourcedungeons.core.dungeon.IDungeon;

public class DungeonScriptException extends DungeonException {

    private static final long serialVersionUID = 1L;

    public DungeonScriptException() {
        super();
    }

    public DungeonScriptException(String message) {
        super(message);
    }

    public DungeonScriptException(String message, IDungeon dungeon) {
        super(message, dungeon);
    }

}
