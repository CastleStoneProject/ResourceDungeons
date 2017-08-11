package net.tkarura.resourcedungeons.core.generate;

import net.tkarura.resourcedungeons.core.dungeon.DungeonGenerateOption;
import net.tkarura.resourcedungeons.core.dungeon.IDungeon;

public class DungeonCheckPoint {

    // 生成位置
    private int x;
    private int y;
    private int z;

    // 生成可能なダンジョン
    private IDungeon dungeon;

    // 適用されたオプション
    private DungeonGenerateOption option;

    public DungeonCheckPoint(int x, int y, int z, IDungeon dungeon, DungeonGenerateOption option) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dungeon = dungeon;
        this.option = option;
    }

    public IDungeon getDungeon() {
        return dungeon;
    }

    public DungeonGenerateOption getOption() {
        return option;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

}
