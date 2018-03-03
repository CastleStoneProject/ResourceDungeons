package net.tkarura.resourcedungeons.core.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import net.tkarura.resourcedungeons.core.dungeon.DungeonImpl;
import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.exception.DungeonLoadException;
import net.tkarura.resourcedungeons.core.loader.handle.LoadLogHandleList;

public abstract class FileDungeonLoader implements DungeonLoader {

    protected File file;
    protected File dir;
    protected final LoadLogHandleList logs = new LoadLogHandleList();

    public IDungeon loadFileDungeon(File file) throws DungeonLoadException {

        this.file = file;
        this.dir = file.getParentFile();

        try {

            return this.loadDungeon(new FileInputStream(file));

        } catch (FileNotFoundException e) {
            throw new DungeonLoadException(e.getLocalizedMessage());
        }

    }

    @Override
    public LoadLogHandleList getLogHandleList() {
        return this.logs;
    }

}
