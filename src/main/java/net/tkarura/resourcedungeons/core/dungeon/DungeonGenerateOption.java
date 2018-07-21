package net.tkarura.resourcedungeons.core.dungeon;

import net.tkarura.resourcedungeons.core.server.IDungeonWorld;
import org.apache.commons.lang3.Range;

import java.util.ArrayList;
import java.util.List;

public class DungeonGenerateOption {

    public final static double DEFAULT_PERCENT = 0.001d;

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

    private final String function_name;
    private double percent = DEFAULT_PERCENT;

    private List<String> biomes;
    private List<BlockOption> blocks;
    private List<Range<Integer>> heights;

    public DungeonGenerateOption(String function_name) {
        this.function_name = function_name;
        this.biomes = new ArrayList<>(default_biomes);
        this.blocks = new ArrayList<>(default_blocks);
        this.heights = new ArrayList<>();
    }

    public void setPercent(double percent) {
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

    public void includeHeight(int min, int max) {
        this.heights.add(Range.between(min, max));
    }

    public boolean checkGenerate(IDungeonWorld world, int x, int y, int z) {

        if (!this.biomes.contains(world.getBiome(x, z))) {
            return false;
        }

        if (!checkHeight(y)) {
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

    private boolean checkHeight(int y) {

        for (Range<Integer> height : heights) {
            if (height.contains(y)) {
                return true;
            }
        }

        return false;
    }

    public String getFunctionName() {
        return this.function_name;
    }

    public double getPercent() { return this.percent; }

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

        @Override
        public String toString() {
            return "BlockOption{" +
                    "block_id='" + block_id + '\'' +
                    ", x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "DungeonGenerateOption{" +
                "function_name='" + function_name + '\'' +
                ", percent=" + percent +
                ", default_biomes_flag=" + default_biomes_flag +
                ", default_blocks_flag=" + default_blocks_flag +
                ", biomes=" + biomes +
                ", blocks=" + blocks +
                '}';
    }

}
