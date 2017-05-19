package net.tkarura.resourcedungeons.core.session;

import net.tkarura.resourcedungeons.core.script.GenerateHandle;
import net.tkarura.resourcedungeons.core.server.DungeonWorld;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagCompound;

@SessionHandle(name = "setblock")
public class SetBlockSession implements Session {

    @Override
    public void run(GenerateHandle handle, DNBTTagCompound data) {
	
	DungeonWorld world = handle.getWorld();
	String block_id = data.getString("block_id");
	byte damage = data.getByte("block_data");
	int x = data.getInt("x");
	int y = data.getInt("y");
	int z = data.getInt("z");

	block_id = !block_id.equals("") ? block_id : world.getBlockId(x, y, z);
	
	world.setBlockId(block_id, x, y, z);
	world.setBlockData(damage, x, y, z);
	
    }
    
}
