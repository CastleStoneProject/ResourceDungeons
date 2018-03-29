package net.tkarura.resourcedungeons.core.dungeon;

import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class DungeonScriptText implements IDungeonScript {

    private String location;
    private String text;

    public DungeonScriptText(String text) {
        Validate.notNull(text, "text can not be null.");
        this.text = text;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public Reader getReader() throws IOException {
        return new StringReader(text);
    }

    @Override
    public String getLocation() {
        return location != null ? location : "unknown location.";
    }

    @Override
    public String toString() {
        return "DungeonScriptText{" +
                "text='" + text + '\'' +
                '}';
    }

}
