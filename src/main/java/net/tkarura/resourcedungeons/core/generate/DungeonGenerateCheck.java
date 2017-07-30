package net.tkarura.resourcedungeons.core.generate;

import java.util.ArrayList;
import java.util.List;

import net.tkarura.resourcedungeons.core.dungeon.DungeonGenerateOption;
import net.tkarura.resourcedungeons.core.dungeon.DungeonManager;
import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.server.DungeonWorld;

public class DungeonGenerateCheck {

    // 検索するワールド情報
    private DungeonWorld world;

    // 検索するダンジョン一覧を格納したマネージャークラス
    private DungeonManager manager;

    // 検索基点座標
    private int base_x = 0;
    private int base_y = 0;
    private int base_z = 0;

    // 検索範囲
    private int width = 0;
    private int height = 0;
    private int length = 0;

    // 検索結果を格納するクラス
    private List<CheckPoint> list = new ArrayList<>();

    /**
     * 検索する範囲を指定して生成します。
     *
     * @param base_x 検索基点x
     * @param base_y 検索基点y
     * @param base_z 検索基点z
     * @param width  横幅の検索範囲
     * @param height 高さの検索範囲
     * @param length
     */
    public DungeonGenerateCheck(int base_x, int base_y, int base_z, int width, int height, int length) {
        this.base_x = base_x;
        this.base_y = base_y;
        this.base_z = base_z;
        this.width = width;
        this.height = height;
        this.length = length;
    }

    /**
     * 検索するダンジョン一覧情報を設定します。
     *
     * @param manager ダンジョン一覧情報
     */
    public void setDungeonManager(DungeonManager manager) {
        this.manager = manager;
    }

    /**
     * 検索するワールド情報を設定します。
     *
     * @param world ワールド情報
     */
    public void setWorld(DungeonWorld world) {
        this.world = world;
    }

    /**
     * 検索を開始します。
     */
    public void search() {

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                for (int z = 0; z < length; z++) {

                    // ブロック単位での検索を始めます。
                    this.search(base_x + x, base_y + y, base_z + z);

                }
            }
        }

    }

    /**
     * 一ブロックの検索をします。
     *
     * @param x 検索する座標
     * @param y 検索する座標
     * @param z 検索する座標
     */
    public void search(int x, int y, int z) {

        for (IDungeon dungeon : this.manager.getDungeons()) {

            DungeonGenerateOption option = this.searchDungeon(dungeon, x, y, z);

            if (option != null) {

                // 検索結果が一致した場合
                // チェックポイントを作成してリストに追加します。
                CheckPoint point = new CheckPoint();
                point.dungeon = dungeon;
                point.option = option;
                point.x = x;
                point.y = y;
                point.z = z;
                this.list.add(point);

            }

        }

    }

    /**
     * ダンジョンとその座標の検索をします。
     *
     * @param dungeon 検索する座標
     * @param x       検索するx座標
     * @param y       検索するy座標
     * @param z       検索するz座標
     * @return 検索の条件に一致した場合 true を返します。
     */
    public DungeonGenerateOption searchDungeon(IDungeon dungeon, int x, int y, int z) {

        for (DungeonGenerateOption option : dungeon.getGenerateOptions()) {

            // バイオーム検索
            if (!checkBiome(option.getBiomes(), x, y, z)) {
                continue;
            }

            // ブロック検索
            if (!checkBlock(option.getBlocks(), x, y, z)) {
                continue;
            }

            // 全ての条件に一致した場合
            return option;

        }

        // 生成オプションが無い場合はnull
        return null;

    }

    private boolean checkBiome(String[] biomes, int x, int y, int z) {

        for (String biome : biomes) {
//            if (this.world.getBiome(x, z)) {
//                return true;
//            }
        }

        return false;
    }

    private boolean checkBlock(String[] blocks, int x, int y, int z) {

        for (String block : blocks) {
            if (this.world.getBlockId(x, y, z).equalsIgnoreCase(block)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 検索結果の情報を格納する内部クラスです。
     */
    private class CheckPoint {

        // 生成位置
        int x;
        int y;
        int z;

        // 生成可能なダンジョン
        IDungeon dungeon;

        // 適用されたオプション
        DungeonGenerateOption option;

    }

}
