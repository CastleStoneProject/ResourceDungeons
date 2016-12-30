package net.tkarura.resourcedungeons.core.exception;

import net.tkarura.resourcedungeons.core.dungeon.IDungeon;

public class DungeonLoadException extends DungeonException {

    private static final long serialVersionUID = 1L;

    public DungeonLoadException() {
	super();
    }

    public DungeonLoadException(String message) {
	super(message);
    }

    public DungeonLoadException(IDungeon dungeon) {
	super();
    }

    public DungeonLoadException(String message, IDungeon dungeon) {
	super(message);
    }
    
}
