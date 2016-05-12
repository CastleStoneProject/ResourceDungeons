package net.tkarura.resourcedungeons.core.server;

/**
 * ワールドに関する処理を受け渡す中間クラスです。
 * @author the_karura
 */
public abstract class DungeonWorld {
	
	/**
	 * ブロックを配置します。
	 * @param loc 配置する位置情報
	 * @param block ブロックID
	 * @param damage ダメージ値
	 * @param applyPhysics 周囲の更新の有無
	 */
	public void setBlock(DungeonLocation loc, String block, byte damage, boolean applyPhysics) {
		setBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), block, damage, applyPhysics);
	}
	
	/**
	 * ブロックを配置します。
	 * @param x 配置するx座標
	 * @param y 配置するy座標
	 * @param z 配置するz座標
	 * @param block ブロックID
	 * @param damage ダメージ値
	 * @param applyPhysics 周囲の更新の有無
	 */
	public abstract void setBlock(int x, int y, int z, String block, byte damage, boolean applyPhysics);
	
	/**
	 * ブロック情報を更新します。
	 * @param loc 更新する位置情報
	 */
	public void update(DungeonLocation loc) {
		update(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	/**
	 * ブロック情報を更新します。
	 * @param x 更新する位置x座標
	 * @param y 更新する位置y座標
	 * @param z 更新する位置z座標
	 */
	public abstract void update(int x, int y, int z);
	
	/**
	 * バイオーム情報を設定します。
	 * @param loc 設定する位置情報
	 * @param biome バイオームID
	 */
	public void setBiome(DungeonLocation loc, String biome) {
		setBiome(loc.getBlockX(), loc.getBlockZ(), biome);
	}
	
	/**
	 * バイオーム情報を設定します。
	 * @param x 設定する位置x座標
	 * @param y 設定する位置x座標
	 * @param z 設定する位置x座標
	 * @param biome バイオームID
	 */
	public abstract void setBiome(int x, int z, String biome);
	
	/**
	 * ワールド名を返します。
	 * @return ワールド名
	 */
	public abstract String getName();
	
	/**
	 * ワールドのシード値を返します。
	 * @return シード値
	 */
	public abstract long getSeed();
	
	/**
	 * ワールド最大高度を返します。
	 * @return 最大高度
	 */
	public abstract int getMaxHeight();
	
	/**
	 * 指定した位置情報のブロックIDを返します。
	 * @param loc 位置情報
	 * @return ブロックID
	 */
	public String getBlockID(DungeonLocation loc) {
		return getBlockID(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	/**
	 * 指定した位置情報のブロックIDを返します。
	 * @param x 位置x座標
	 * @param y 位置y座標
	 * @param z 位置z座標
	 * @return ブロックID
	 */
	public abstract String getBlockID(int x, int y, int z);
	
	/**
	 * 指定した位置情報のダメージ値を返します。
	 * @param loc 位置情報
	 * @return ダメージ値
	 */
	public byte getData(DungeonLocation loc) {
		return getData(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	/**
	 * 指定した位置情報のダメージ値を返します。
	 * @param x 位置x座標
	 * @param y 位置y座標
	 * @param z 位置z座標
	 * @return ダメージ値
	 */
	public abstract byte getData(int x, int y, int z);
	
	/**
	 * 指定した位置情報が空の情報であるかを確認します。
	 * @param loc 位置情報
	 * @return 空である場合はtrueを返します。
	 */
	public boolean isEmpty(DungeonLocation loc) {
		return isEmpty(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	/**
	 * 指定した位置情報が空の情報であるかを確認します。
	 * @param x 位置x座標
	 * @param y 位置y座標
	 * @param z 位置z座標
	 * @return 空である場合はtrueを返します。
	 */
	public abstract boolean isEmpty(int x, int y, int z);
	
	/**
	 * 指定した位置情報のバイオームIDを返します。
	 * @param loc 位置情報
	 * @return バイオームID
	 */
	public String getBiome(DungeonLocation loc) {
		return getBiome(loc.getBlockX(), loc.getBlockZ());
	}
	
	/**
	 * 指定した位置情報のバイオームIDを返します。
	 * @param x 位置x座標
	 * @param z 位置z座標
	 * @return バイオームID
	 */
	public abstract String getBiome(int x, int z);
	
}
