package net.tkarura.resourcedungeons.core.dungeon;

import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class DungeonScriptFile implements IDungeonScript {

    private File file;

    public DungeonScriptFile(File file) {
        this.file = file;
    }

    @Override
    public Reader getReader() throws IOException {

        Validate.notNull(file, "file is null.");

        return new FileReader(file);
    }

}
