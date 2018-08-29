package net.tkarura.resourcedungeons.core.session.structure;

import net.tkarura.resourcedungeons.core.server.IDungeonWorld;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagCompound;

import java.util.List;

public class Structure {

    private class Palette {
        private String name;

    }

    private class Block {
        private int x, y, z;
        private int palette;
    }

    private List<Palette> palettes;
    private List<Block> blocks;
    private String author;
    private int width, height, length;
    private int version;

    public Structure(DNBTTagCompound dnbtTagCompound) {



        this.author = dnbtTagCompound.getString("author");

        int[] size = dnbtTagCompound.getIntArray("size");
        this.width = size[0];
        this.height = size[1];
        this.length = size[2];

        this.version = dnbtTagCompound.getInt("version");

    }

    public void generate(IDungeonWorld world, int base_x, int base_y, int base_z) {

        for (Block block : blocks) {

            Palette palette = palettes.get(block.palette);

            world.setBlockId(palette.name, block.x + base_x, block.y + base_y, block.z + base_z);

        }

    }

}
