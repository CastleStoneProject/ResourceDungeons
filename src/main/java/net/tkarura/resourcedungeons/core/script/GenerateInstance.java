package net.tkarura.resourcedungeons.core.script;

import java.util.UUID;

import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagCompound;
import net.tkarura.resourcedungeons.core.script.handle.Handle;
import net.tkarura.resourcedungeons.core.script.handle.HandleManager;
import net.tkarura.resourcedungeons.core.server.DungeonWorld;

public final class GenerateInstance {

    private final UUID uuid;
    private HandleManager handles;
    private IDungeon dungeon;
    private DungeonWorld world;
    private DNBTTagCompound nbt;
    
    public GenerateInstance(IDungeon dungeon, DungeonWorld world, DNBTTagCompound nbt) {
	this.uuid = UUID.randomUUID();
	this.dungeon = dungeon;
	this.world = world;
	this.nbt = nbt;
    }
    
    public void setHandles(HandleManager handles) {
	this.handles = handles;
    }
    
    public UUID getUUID() {
	return this.uuid;
    }
    
    public Handle getHandle(String handle_name) {
	return this.handles.getHandle(this, handle_name);
    }
    
    public IDungeon getDungeon() {
	return this.dungeon;
    }
    
    public DungeonWorld getWorld() {
	return this.world;
    }
    
    public DNBTTagCompound getParameters() {
	return this.nbt;
    }
    
}
