package net.tkarura.resourcedungeons.core.server.world;

import net.tkarura.resourcedungeons.core.server.DungeonLocation;

/**
 * チャンクに関する処理を表します・
 * {@link DungeonWorld}におけるチャンク単位での相対的な指定に使用されます。
 * 
 * @author the_karura
 */
public interface DungeonChunk {
	
	/** 一チャンク内のx軸最大幅を表す定数です。 */
	public final int MAX_X = 16;
	
	/** 一チャンク内のz軸最大幅を表す定数です。 */
	public final int MAX_Z = 16;
	
	/**
	 * ワールド情報を返します。
	 * @return ワールド情報
	 */
	public DungeonWorld getWorld();
	
	/**
	 * チャンク内座標を基点に位置情報を返します。
	 * @param x 相対 x 座標
	 * @param y 相対 y 座標
	 * @param z 相対 z 座標
	 * @return 絶対座標に変換された位置情報
	 */
	public DungeonLocation getLocation(int x, int y, int z);
	
	/**
	 * チャンク内座標を基点にバイオーム情報を返します。
	 * @param x 相対 x 座標
	 * @param z 相対 z 座標
	 * @return 該当の座標に位置するバイオーム情報
	 */
	public String getBiome(int x, int z);
	
	/**
	 * チャンク内座標を基点にブロック情報を返します。
	 * @param x 相対 x 座標
	 * @param y 相対 y 座標
	 * @param z 相対 z 座標
	 * @return 該当の座標に位置するブロック情報
	 */
	public DungeonBlock getBlock(int x, int y, int z);
	
	/**
	 * チャンク内座標を基点にブロックIDを返します。
	 * @param x 相対 x 座標
	 * @param y 相対 y 座標
	 * @param z 相対 z 座標
	 * @return 該当の座標に位置するブロックのID
	 */
	public String getBlockID(int x, int y, int z);
	
	/**
	 * チャンク内座標を基点にブロックIDを返します。
	 * @param x 相対 x 座標
	 * @param y 相対 y 座標
	 * @param z 相対 z 座標
	 * @return 該当の座標に位置するブロックのdamage値
	 */
	public byte getDamage(int x, int y, int z);
	
}
