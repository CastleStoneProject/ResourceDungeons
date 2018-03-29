package net.tkarura.resourcedungeons.core.dungeon;

import java.io.IOException;
import java.io.Reader;

/**
 * ダンジョンスクリプトを表すクラスです。
 */
public interface IDungeonScript {

    public Reader getReader() throws IOException;

    public String getLocation();

}
