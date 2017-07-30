package net.tkarura.resourcedungeons.core.loader.handle;

import java.util.logging.Level;

public class LoadLogHandle {

    private Level level;
    private String message;

    public LoadLogHandle(Level level, String message) {
        this.level = level;
        this.message = message;
    }

    public Level getLevel() {
        return this.level;
    }

    public String getMessage() {
        return this.message;
    }

}
