package net.tkarura.resourcedungeons.core.command;

import java.util.Collection;

import net.tkarura.resourcedungeons.core.dungeon.DungeonManager;
import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.exception.DungeonCommandException;

public class DungeonListCommand extends DungeonCommand {

    private DungeonManager dungeon_manager;
    
    public DungeonListCommand() {
	super("list");
	this.permission = "ResourceDungeons.Command.List";
    }
    
    public void setDungeonManager(DungeonManager dungeon_manager) {
	this.dungeon_manager = dungeon_manager;
    }
    
    @Override
    public void execute(DungeonCommandSender sender) throws DungeonCommandException {
	
	int count = 0;
	
	Collection<IDungeon> dungeons = this.dungeon_manager.getDungeons();
	
	if (dungeons.isEmpty()) {
	    sender.sendMessage("dungeon is not loaded.");
	    return;
	}
	
	for (IDungeon dungeon : dungeons) {
	    sender.sendMessage("[" + count++ + "] id: " + dungeon.getId() + " name: " + dungeon.getName());
	}
	
    }

}
