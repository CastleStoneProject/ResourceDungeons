package net.tkarura.resourcedungeons.core.loader;

import java.io.InputStream;

import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.exception.DungeonLoadException;
import net.tkarura.resourcedungeons.core.loader.handle.LoadLogHandleList;

public interface DungeonLoader {

    public IDungeon loadDungeon(InputStream is) throws DungeonLoadException;

    public LoadLogHandleList getLogHandleList();

}
