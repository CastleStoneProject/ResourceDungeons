package net.tkarura.resourcedungeons.core.generate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.tkarura.resourcedungeons.core.dungeon.DungeonGenerateOption;
import net.tkarura.resourcedungeons.core.dungeon.DungeonManager;
import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.server.IDungeonWorld;

public class DungeonGenerateCheck {

    // 検索するワールド情報
    private IDungeonWorld world;

    // 検索するダンジョン一覧を格納したマネージャークラス
    private DungeonManager manager;

    // 検索基点座標
    private int base_x = 0;
    private int base_y = 0;
    private int base_z = 0;

    // 検索範囲
    private int width = 0;
    private int height = 0;
    private int depth = 0;

    // 検索結果を格納するクラス
    private List<DungeonCheckPoint> list = new ArrayList<>();

    /**
     * 検索する範囲を指定して生成します。
     *
     * @param base_x 検索基点x
     * @param base_y 検索基点y
     * @param base_z 検索基点z
     * @param width  横幅の検索範囲
     * @param height 高さの検索範囲
     * @param depth  奥行きの検査範囲
     */
    public DungeonGenerateCheck(int base_x, int base_y, int base_z, int width, int height, int depth) {
        this.base_x = base_x;
        this.base_y = base_y;
        this.base_z = base_z;
        this.width = width;
        this.height = height;
        this.depth = depth;
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
    public void setWorld(IDungeonWorld world) {
        this.world = world;
    }

    public void search() {
        search(new Random(toPositionSeed() * world.getSeed()));
    }

    private int toPositionSeed() {
        return ((base_x * 123) + (base_y * 456) + (base_z * 789));
    }

    /**
     * 検索を開始します。
     * @param random 検索に使用する乱数
     */
    public void search(Random random) {

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                for (int z = 0; z < depth; z++) {

                    // ブロック単位での検索を始めます。
                    this.search(base_x + x, base_y + y, base_z + z, random);

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
     * @param random 検索に使用する乱数
     */
    public void search(int x, int y, int z, Random random) {

        for (IDungeon dungeon : this.manager.getDungeons()) {

            DungeonGenerateOption option = this.searchDungeon(dungeon, x, y, z);

            if (option != null) {

                // 確率から実際に配置するかどうかを判定
                if (option.getPercent() < random.nextDouble()) {
                    continue;
                }

                // 検索結果が一致した場合
                // チェックポイントを作成してリストに追加します。
                list.add(new DungeonCheckPoint(x, y, z, dungeon, option));

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

            // チェック処理
            if (!option.checkGenerate(world, x, y, z)) {
                continue;
            }

            // 全ての条件に一致した場合
            return option;

        }

        // 生成オプションが無い場合はnull
        return null;

    }

    /**
     * 検索結果を返します。
     * @return 検索結果
     */
    public List<DungeonCheckPoint> getCheckPoints() {
        return this.list;
    }

}
