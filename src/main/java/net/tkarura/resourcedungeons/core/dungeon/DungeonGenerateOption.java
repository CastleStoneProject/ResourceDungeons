package net.tkarura.resourcedungeons.core.dungeon;

import java.util.ArrayList;
import java.util.List;

public class DungeonGenerateOption {

    private String function_name;

    private List<String> biomes;
    private List<String> blocks;

    public DungeonGenerateOption(String function_name) {
        this.function_name = function_name;
        this.biomes = new ArrayList<String>();
        this.blocks = new ArrayList<String>();
    }

    public void includeBiome(String biome) {
        this.biomes.add(biome);
    }

    public void includeBlock(String block) {
        this.blocks.add(block);
    }

    public void ignoreBiome(String biome) {
        this.biomes.remove(biome);
    }

    public void ignoreBlock(String block) {
        this.blocks.remove(block);
    }

    public String getFunctionName() {
        return this.function_name;
    }

    public String[] getBiomes() {
        return this.biomes.toArray(new String[0]);
    }

    public String[] getBlocks() {
        return this.blocks.toArray(new String[0]);
    }

}
