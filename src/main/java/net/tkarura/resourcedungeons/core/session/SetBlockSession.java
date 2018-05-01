package net.tkarura.resourcedungeons.core.session;

import net.tkarura.resourcedungeons.core.script.GenerateHandle;
import net.tkarura.resourcedungeons.core.server.IDungeonWorld;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagCompound;

@SessionHandle(name = "setblock")
public class SetBlockSession implements ISession {

	@Override
	public void run(GenerateHandle handle, DNBTTagCompound data) {

		IDungeonWorld world = handle.getWorld();
		int x = data.getInt("x");
		int y = data.getInt("y");
		int z = data.getInt("z");
		String block_id = data.getString("block_id", world.getBlockId(x, y, z));
		byte damage = data.getByte("block_data", world.getBlockData(x, y, z));

		world.setBlockId(block_id, x, y, z);
		world.setBlockData(damage, x, y, z);

	}

}
