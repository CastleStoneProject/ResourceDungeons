package net.tkarura.resourcedungeons.core.generate;

import net.tkarura.resourcedungeons.core.dungeon.Dungeon;
import net.tkarura.resourcedungeons.core.server.DungeonServer;
import net.tkarura.resourcedungeons.core.server.DungeonWorld;

/**
 * スクリプト側に渡す処理のハンドルを表します。
 * スクリプト側によるサーバへの処理の受け渡しはこのインタフェースによる実装クラスを通じて行われます。
 * 
 * @author the_karura
 */
public interface GenerateHandle {
    
    /**
     * 生成を行うダンジョン情報を返します。
     * @return ダンジョン情報
     */
    public Dungeon getDungeon();
    
    /**
     * 動作しているサーバのクラスを返します。
     * @return サーバ情報
     */
    public DungeonServer getServer();
    
    /**
     * 生成を行うワールド情報を返します。
     * @return ワールド情報
     */
    public DungeonWorld getWorld();
    
    /**
     * 生成を行う起点X座標を返します。
     * ワールドに対し絶対座標値で返します。
     * @return 起点X座標
     */
    public int getGenX();

    /**
     * 生成を行う起点Y座標を返します。
     * ワールドに対し絶対座標値で返します。
     * @return 起点Y座標
     */
    public int getGenY();

    /**
     * 生成を行う起点Z座標を返します。
     * ワールドに対し絶対座標値で返します。
     * @return 起点Z座標
     */
    public int getGenZ();
    
}
