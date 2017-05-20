package net.tkarura.resourcedungeons.core.command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private final Map<String, DungeonCommand> commands = new HashMap<>();

    public void init() {
	this.commands.clear();
    }

    public void register(DungeonCommand command) {
	this.commands.put(command.getName(), command);
    }

    public DungeonCommand getCommand(String name) {
	return this.commands.get(name);
    }

}
