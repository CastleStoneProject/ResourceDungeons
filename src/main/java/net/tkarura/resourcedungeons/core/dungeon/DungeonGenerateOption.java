package net.tkarura.resourcedungeons.core.dungeon;

import net.tkarura.resourcedungeons.core.server.IDungeonWorld;

import java.util.ArrayList;
import java.util.List;

public class DungeonGenerateOption {

    public final static float DEFAULT_PERCENT = 0.0001f;

    private final String function_name;
    private float percent = DungeonGenerateOption.DEFAULT_PERCENT;

    private static List<String> default_biomes = new ArrayList<>();
    static {
        default_biomes.add("minecraft:plains");
        default_biomes.add("minecraft:desert");
        default_biomes.add("minecraft:forest");
        default_biomes.add("minecraft:taiga");
        default_biomes.add("minecraft:ice_flats");
        default_biomes.add("minecraft:jungle");
        default_biomes.add("minecraft:savanna");
    }
    private boolean default_biomes_flag = true;

    private static List<BlockOption> default_blocks = new ArrayList<>();
    static {
        default_blocks.add(new BlockOption("minecraft:grass", 0, -1, 0));
        default_blocks.add(new BlockOption("minecraft:air", 0, 2, 0));
        default_blocks.add(new BlockOption("minecraft:air", 0, 3, 0));
    }
    private boolean default_blocks_flag = true;

    private List<String> biomes;
    private List<BlockOption> blocks;

    public DungeonGenerateOption(String function_name) {
        this.function_name = function_name;
        this.biomes = new ArrayList<>(default_biomes);
        this.blocks = new ArrayList<>(default_blocks);
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public void includeBiome(String biome) {
        if (default_biomes_flag) {
            default_biomes_flag = false;
            this.biomes.clear();
        }
        this.biomes.add(biome);
    }

    public void includeBlock(String block_id, int x, int y, int z) {
        if (default_blocks_flag) {
            default_blocks_flag = false;
            this.blocks.clear();
        }
        this.blocks.add(new BlockOption(block_id, x, y, z));
    }

    public boolean checkGenerate(IDungeonWorld world, int x, int y, int z) {

        if (!this.biomes.contains(world.getBiome(x, z))) {
            return false;
        }

        for (BlockOption option : this.blocks) {

            if (!world.getBlockId(option.x + x, option.y + y, option.z + z)
                    .equalsIgnoreCase(option.block_id)) {
                return false;
            }

        }

        return true;
    }

    public String getFunctionName() {
        return this.function_name;
    }

    public float getPercent() { return this.percent; }

    public String[] getBiomes() {
        return this.biomes.toArray(new String[0]);
    }

    private static class BlockOption {

        String block_id;

        int x;
        int y;
        int z;

        BlockOption(String block_id, int x, int y, int z) {
            this.block_id = block_id;
            this.x = x;
            this.y = y;
            this.z = z;
        }

    }

}
