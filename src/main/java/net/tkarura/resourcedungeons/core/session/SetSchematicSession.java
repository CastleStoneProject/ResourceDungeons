package net.tkarura.resourcedungeons.core.session;

import net.tkarura.resourcedungeons.core.exception.DungeonSessionException;
import net.tkarura.resourcedungeons.core.schematic.Schematic;
import net.tkarura.resourcedungeons.core.script.GenerateHandle;
import net.tkarura.resourcedungeons.core.server.IDungeonWorld;
import net.tkarura.resourcedungeons.core.util.FileUtil;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagCompound;

import java.io.File;
import java.io.IOException;

@SessionHandle(name = "schematic")
public class SetSchematicSession implements ISession {

    @Override
    public void run(GenerateHandle handler, DNBTTagCompound data) throws DungeonSessionException {

        IDungeonWorld world = handler.getWorld();
        int pos_x = data.getInt("x");
        int pos_y = data.getInt("y");
        int pos_z = data.getInt("z");
        File dungeon_dir = handler.getDungeon().getDirectory();
        File schematic_format = new File(dungeon_dir, data.getString("schematic"));

        if (!FileUtil.isDirectoryInPath(dungeon_dir, schematic_format)) {
            throw new DungeonSessionException("ダンジョンディレクトリ外を参照をしています！");
        }

        Schematic schematic;

        try {

            schematic = Schematic.loadSchematic(schematic_format);

            int block_id;
            byte damage;

            for (int x = 0; x < schematic.getWidth(); x++) {
                for (int y = 0; y < schematic.getHeight(); y++) {
                    for (int z = 0; z < schematic.getLength(); z++) {

                        block_id = schematic.getBlock(x, y, z);
                        damage = schematic.getData(x, y, z);
                        world.setBlockId(block_id, pos_x + x, pos_y + y, pos_z + z);
                        world.setBlockData(damage, pos_x + x, pos_y + y, pos_z + z);

                    }
                }
            }

            for (int i = 0; i < schematic.getTileEntitySize(); i++) {

                DNBTTagCompound nbt = schematic.getTileEntity(i);
                int tile_pox_x = nbt.getInt("x");
                int tile_pos_y = nbt.getInt("y");
                int tile_pos_z = nbt.getInt("z");

                world.setTileEntityFromNBT(nbt,
                        tile_pox_x + pos_x,
                        tile_pos_y + pos_y,
                        tile_pos_z + pos_z);

            }

        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        }

    }

}
