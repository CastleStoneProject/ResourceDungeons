package net.tkarura.resourcedungeons.core.server.world;

import net.tkarura.resourcedungeons.core.nbt.DNBTTagCompound;

/**
 * ワールドに関する処理を表します。
 * @author the_karura
 */
public interface DungeonWorld {
	
	/**
	 * ブロックを配置します。
	 * @param x 配置するx座標
	 * @param y 配置するy座標
	 * @param z 配置するz座標
	 * @param block ブロックID
	 * @param damage ダメージ値
	 * @param applyPhysics 周囲の更新の有無
	 */
	public void setBlock(int x, int y, int z, String block, byte damage, boolean applyPhysics);
	
	/**
	 * タイル情報を設定します。
	 * @param x ブロックの位置x座標
	 * @param y ブロックの位置y座標
	 * @param z ブロックの位置z座標
	 * @param nbt 設定するNBT情報
	 */
	public void setTileEntityNBT(int x, int y, int z, DNBTTagCompound nbt);
	
	/**
	 * ブロック情報を更新します。
	 * @param x 更新する位置x座標
	 * @param y 更新する位置y座標
	 * @param z 更新する位置z座標
	 */
	public void update(int x, int y, int z);
	
	/**
	 * バイオーム情報を設定します。
	 * @param x 設定する位置x座標
	 * @param y 設定する位置x座標
	 * @param z 設定する位置x座標
	 * @param biome バイオームID
	 */
	public void setBiome(int x, int z, String biome);
	
	/**
	 * ワールド名を返します。
	 * @return ワールド名
	 */
	public String getName();
	
	/**
	 * ワールドのシード値を返します。
	 * @return シード値
	 */
	public long getSeed();
	
	/**
	 * ワールド最大高度を返します。
	 * @return 最大高度
	 */
	public int getMaxHeight();
	
	/**
	 * 指定した位置情報のブロック情報を返します。
	 * @param x 位置x座標
	 * @param y 位置y座標
	 * @param z 位置z座標
	 * @return ブロック情報
	 */
	public DungeonBlock getBlock(int x, int y, int z);
	
	/**
	 * 指定した位置情報のブロックIDを返します。
	 * @param x 位置x座標
	 * @param y 位置y座標
	 * @param z 位置z座標
	 * @return ブロックID
	 */
	public String getBlockID(int x, int y, int z);
	
	/**
	 * 指定した位置情報のダメージ値を返します。
	 * @param x 位置x座標
	 * @param y 位置y座標
	 * @param z 位置z座標
	 * @return ダメージ値
	 */
	public byte getData(int x, int y, int z);
	
	/**
	 * 指定した位置情報が空の情報であるかを確認します。
	 * @param x 位置x座標
	 * @param y 位置y座標
	 * @param z 位置z座標
	 * @return 空である場合はtrueを返します。
	 */
	public boolean isEmpty(int x, int y, int z);
	
	/**
	 * 指定した位置情報のブロックがTileEntityであるかを確認します。
	 * @param x 位置x座標
	 * @param y 位置y座標
	 * @param z 位置z座標
	 * @return TileEntityであればtrueを返します。
	 */
	public boolean isTileEntity(int x, int y, int z);
	
	/**
	 * 指定した位置情報のTileEntityのNBTTagを取得します。
	 * @param x 位置x座標
	 * @param y 位置y座標
	 * @param z 位置z座標
	 * @return TileEntityのNBTTagCompound もしTileEntityで無ければnullを返します。
	 */
	public DNBTTagCompound getTileEntityNBT(int x, int y, int z);
	
	/**
	 * 指定した位置情報のバイオームIDを返します。
	 * @param x 位置x座標
	 * @param z 位置z座標
	 * @return バイオームID
	 */
	public String getBiome(int x, int z);
	
}
