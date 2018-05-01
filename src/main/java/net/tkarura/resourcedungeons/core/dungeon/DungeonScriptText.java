package net.tkarura.resourcedungeons.core.dungeon;

import org.apache.commons.lang3.Validate;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.IOException;

public class DungeonScriptText implements IDungeonScript {

    private String text;

    public DungeonScriptText(String text) {
        Validate.notNull(text, "text can not be null.");
        this.text = text;
    }

    @Override
    public void read(ScriptEngine engine) throws ScriptException {
        engine.eval(text);
    }

    @Override
    public String getLocation() {
        return "<text>";
    }

    @Override
    public String toString() {
        return "DungeonScriptText{" +
                "text='" + text + '\'' +
                '}';
    }

}
