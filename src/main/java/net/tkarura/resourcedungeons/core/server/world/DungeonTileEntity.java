package net.tkarura.resourcedungeons.core.server.world;

import net.tkarura.resourcedungeons.core.nbt.DNBTTagCompound;

public interface DungeonTileEntity extends DungeonBlock {
	
	/**
	 * NBTタグ情報を返します。
	 * TileEntityで無い場合はnullを返します。
	 * @return NBTタグ情報
	 */
	public DNBTTagCompound getNBTTagCompound();
	
}
