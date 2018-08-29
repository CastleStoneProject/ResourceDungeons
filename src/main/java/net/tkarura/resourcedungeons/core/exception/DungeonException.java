package net.tkarura.resourcedungeons.core.exception;

import net.tkarura.resourcedungeons.core.dungeon.IDungeon;

/**
 * ResourceDungeonsに関する全ての処理に関わる例外を表します。
 */
public class DungeonException extends Exception {

    private static final long serialVersionUID = 1L;

    protected IDungeon dungeon = null;

    public DungeonException() {
        super();
    }

    public DungeonException(String message) {
        super(message);
    }

    public DungeonException(IDungeon dungeon) {
        super();
        this.dungeon = dungeon;
    }

    public DungeonException(String message, IDungeon dungeon) {
        super(message);
        this.dungeon = dungeon;
    }

    public IDungeon getDungeon() {
        return this.dungeon;
    }

}
