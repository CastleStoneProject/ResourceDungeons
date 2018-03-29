package net.tkarura.resourcedungeons.core.dungeon;

import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class DungeonScriptFile implements IDungeonScript {

    private File file;

    public DungeonScriptFile(File file) {
        Validate.notNull(file, "file can not be null.");
        this.file = file;
    }

    @Override
    public Reader getReader() throws IOException {
        return new FileReader(file);
    }

    @Override
    public String getLocation() {
        return file.getName();
    }

    @Override
    public String toString() {
        return "DungeonScriptFile{" +
                "file=" + file +
                '}';
    }

}
