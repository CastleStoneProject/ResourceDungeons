package net.tkarura.resourcedungeons.core.script.handle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import net.tkarura.resourcedungeons.core.nbt.DNBTTagCompound;
import net.tkarura.resourcedungeons.core.nbt.stream.DNBTDataInputStream;
import net.tkarura.resourcedungeons.core.schematic.Schematic;
import net.tkarura.resourcedungeons.core.script.GenerateInstance;
import net.tkarura.resourcedungeons.core.server.DungeonWorld;

@HandleParameter(group = "resourcedungeon" ,name = "schematic")
public final class SchematicHandle extends Handle {
    
    public SchematicHandle(GenerateInstance instance) {
	super(instance);
    }
    
    public void importFile(String file, int x, int y, int z, boolean copy_air) {
	
	File dir = new File(instance.getDungeon().getDirectory(), file);
	DNBTDataInputStream is = null;
	Schematic schematic;
	int base_x = instance.getParameters().getInt("gen_x") + x;
	int base_y = instance.getParameters().getInt("gen_y") + y;
	int base_z = instance.getParameters().getInt("gen_z") + z;
	
	try {
	    
	    is = new DNBTDataInputStream(new FileInputStream(dir));
	    schematic = new Schematic(is.getCompound());
	    
	    setBlocks(schematic, base_x, base_y, base_z);
	    setEntities(schematic, base_x, base_y, base_z);
	    setTileEntities(schematic, base_x, base_y, base_z);
	    
	} catch (IOException e) {
	    IOUtils.closeQuietly(is);
	}
	
    }
    
    private void setBlocks(Schematic schematic, int base_x, int base_y, int base_z) {
	
	int block_id;
	byte block_data;
	int gen_x;
	int gen_y;
	int gen_z;
	DungeonWorld world = instance.getWorld();
	
	for (int x = 0; x < schematic.getWidth(); x++) {
	    
	    for (int y = 0; y < schematic.getHeight(); y++) {
		
		for (int z = 0; z < schematic.getLength(); z++) {
		    
		    block_id = schematic.getBlock(x, y, z);
		    block_data = schematic.getData(x, y, z);
		    
		    gen_x = base_x + x;
		    gen_y = base_y + y;
		    gen_z = base_z + z;
		    
		    world.setBlockId(block_id, gen_x, gen_y, gen_z);;
		    world.setBlockData(block_data, gen_x, gen_y, gen_z);
		    
		}
	    }
	}
	
    }
    
    private void setEntities(Schematic schematic, int base_x, int base_y, int base_z) {
	
	DNBTTagCompound entity_nbt;
	int x;
	int y;
	int z;
	
	for (int i = 0; i < schematic.getEntitySize(); i++) {
	    
	    entity_nbt = schematic.getEntity(i);
	    x = entity_nbt.getList("pos").getInt(0) + base_x;
	    y = entity_nbt.getList("pos").getInt(1) + base_y;
	    z = entity_nbt.getList("pos").getInt(2) + base_z;
	    
	    instance.getWorld().spawnEntityFromNBT(entity_nbt, x, y, z);
	    
	}
	
    }
    
    private void setTileEntities(Schematic schematic, int base_x, int base_y, int base_z) {
	
	DNBTTagCompound tile_nbt;
	int x;
	int y;
	int z;
	
	for (int i = 0; i < schematic.getTileEntitySize(); i++) {
	    
	    tile_nbt = schematic.getTileEntity(i);
	    x = tile_nbt.getInt("x") + base_x;
	    y = tile_nbt.getInt("y") + base_y;
	    z = tile_nbt.getInt("z") + base_z;
	    
	    instance.getWorld().setTileEntityFromNBT(tile_nbt, x, y, z);
	    
	}
	
    }
    
    @Override
    public Handle copy(GenerateInstance instance) {
	return new SchematicHandle(instance);
    }
    
}
