package net.tkarura.resourcedungeons.core.generate.handle;

import java.util.UUID;

import net.tkarura.resourcedungeons.core.ResourceDungeons;
import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.server.DungeonWorld;

/**
 * スクリプト側に渡す処理のハンドルを表します。
 * スクリプト側によるサーバへの処理の受け渡しはこのインタフェースによる実装クラスを通じて行われます。
 * 
 * @author the_karura
 */
public class GenerateHandle {
    
    /**
     * この値はscript制御側へ受け渡たさないでください。
     * 重大なセキュリティ違反を起こす元になります。
     */
    protected final ResourceDungeons core = ResourceDungeons.getInstance();

    protected final UUID uuid;
    protected final IDungeon dungeon;
    protected final DungeonWorld world;
    protected int genX = 0;
    protected int genY = 0;
    protected int genZ = 0;
    
    /**
     * ダンジョン情報と生成するワールド情報を指定して生成します。
     * @param dungeon ダンジョン情報
     * @param world ワールド情報
     */
    public GenerateHandle(IDungeon dungeon, DungeonWorld world) {
	this.uuid = UUID.randomUUID();
	this.dungeon = dungeon;
	this.world = world;
    }
    
    /**
     * 生成起点を設定します。
     * @param x x座標
     * @param y y座標
     * @param z z座標
     */
    public void setGeneratePos(int x, int y, int z) {
	this.genX = x;
	this.genY = y;
	this.genZ = z;
    }
    
    /**
     * このハンドルのIDを返します。
     * @return ハンドルのUUID
     */
    public UUID getUUID() {
	return this.uuid;
    }
    
    /**
     * 生成を行うダンジョン情報を返します。
     * @return ダンジョン情報
     */
    public IDungeon getDungeon() {
	return this.dungeon;
    }
    
    /**
     * 生成を行うワールド情報を返します。
     * @return ワールド情報
     */
    public DungeonWorld getWorld() {
	return this.world;
    }
    
    /**
     * 生成を行う起点X座標を返します。
     * ワールドに対し絶対座標値で返します。
     * @return 起点X座標
     */
    public int getGenX() {
	return this.genX;
    }

    /**
     * 生成を行う起点Y座標を返します。
     * ワールドに対し絶対座標値で返します。
     * @return 起点Y座標
     */
    public int getGenY() {
	return this.genY;
    }

    /**
     * 生成を行う起点Z座標を返します。
     * ワールドに対し絶対座標値で返します。
     * @return 起点Z座標
     */
    public int getGenZ() {
	return this.genZ;
    }
    
}
