package net.tkarura.resourcedungeons.core.dungeon;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.io.Reader;

/**
 * ダンジョンスクリプトを表すクラスです。
 */
public interface IDungeonScript {

    void read(ScriptEngine engine) throws IOException, ScriptException;

    String getLocation();

}
