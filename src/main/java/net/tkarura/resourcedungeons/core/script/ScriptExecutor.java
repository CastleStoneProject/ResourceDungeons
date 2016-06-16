package net.tkarura.resourcedungeons.core.script;

import net.tkarura.resourcedungeons.core.exception.DungeonGenerateException;
import net.tkarura.resourcedungeons.core.server.DungeonLocation;

public interface ScriptExecutor {
	
	public void init() throws DungeonGenerateException;
	
	public void execute(DungeonLocation loc, String function) throws DungeonGenerateException;
	
}
