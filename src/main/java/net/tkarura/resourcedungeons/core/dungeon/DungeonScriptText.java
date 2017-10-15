package net.tkarura.resourcedungeons.core.dungeon;

import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class DungeonScriptText implements IDungeonScript {

    private String text;

    public DungeonScriptText(String text) {
        this.text = text;
    }

    @Override
    public Reader getReader() throws IOException {

        Validate.notNull(text, "text is null.");

        return new StringReader(text);
    }

}
