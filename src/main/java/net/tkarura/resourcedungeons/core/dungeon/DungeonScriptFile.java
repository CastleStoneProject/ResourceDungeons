package net.tkarura.resourcedungeons.core.dungeon;

import org.apache.commons.lang3.Validate;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.*;

public class DungeonScriptFile implements IDungeonScript {

    private File file;

    public DungeonScriptFile(File file) {
        Validate.notNull(file, "file can not be null.");
        this.file = file;
    }

    @Override
    public void read(ScriptEngine engine) throws IOException, ScriptException {
        engine.eval(new FileReader(file));
    }

    @Override
    public String getLocation() {
        return this.file.getName();
    }

    @Override
    public String toString() {
        return "DungeonScriptFile{" +
                "file=" + file +
                '}';
    }

}
