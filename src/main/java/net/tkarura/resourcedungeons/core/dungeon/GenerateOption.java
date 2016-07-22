package net.tkarura.resourcedungeons.core.dungeon;

import java.util.ArrayList;
import java.util.List;

import net.tkarura.resourcedungeons.core.server.DungeonLocation;

/**
 * 生成設定を保管するクラスです。
 * 
 * <p>クラス内で以下の要素を取り扱います。
 * <ul>
 * <li>条件に含めるブロック情報</li>
 * <li>条件に含めるバイオーム情報</li>
 * <li>条件に含める位置情報</li>
 * <li>条件が一致した時に呼び出される関数名</li>
 * </ul>
 * 
 * @author the_karura
 */
public class GenerateOption {
	
	private final String function;
	private final double percent;
	private final List<Integer> height = new ArrayList<Integer>();
	private final List<DungeonLocation> locs = new ArrayList<DungeonLocation>();
	private final List<String> blocks = new ArrayList<String>();
	private final List<String> biomes = new ArrayList<String>();
	
	/**
	 * 条件が一致した時に呼び出される関数名を指定して生成
	 * @param function 関数名
	 */
	public GenerateOption(String function) {
		this(function, 0.0d);
	}
	
	/**
	 * 条件が一致した時に呼び出される関数名と生成確率を指定して生成
	 * @param function 関数
	 * @param percent 確率
	 */
	public GenerateOption(String function, double percent) {
		this.function = function;
		this.percent = percent;
	}
	
	/**
	 * 条件に含める高さを追加します。
	 * @param min 最小座標
	 * @param max 最大座標
	 */
	public void addHeight(int min, int max) {
		for (int i = min; i < max; i++) {
			this.height.add(i);
		}
	}
	
	/**
	 * 条件に含める位置情報を追加します。
	 * 位置情報は相対指定で指定してください。。
	 * @param loc 条件に含める位置情報
	 */
	public void addLocation(DungeonLocation loc) {
		this.locs.add(loc);
	}
	
	/**
	 * 条件に含めるブロック情報を追加します。
	 * @param registory_id 条件に含めるブロック情報
	 */
	public void addBlock(String registory_id) {
		this.blocks.add(registory_id);
	}
	
	/**
	 * 条件に含めるバイオーム情報を追加します。
	 * @param biome_id 条件に含めるバイオーム情報
	 */
	public void addBiome(String biome_id) {
		this.biomes.add(biome_id);
	}
	
	/**
	 * 条件から外すブロック情報を指定します。
	 * @param registory_id 条件から除外するブロック情報
	 */
	public void removeBlock(String registory_id) {
		this.blocks.remove(registory_id);
	}
	
	/**
	 * 条件から外すバイオーム情報を指定します。
	 * @param biome_id 条件から除外するバイオーム情報
	 */
	public void removeBiome(String biome_id) {
		this.biomes.remove(biome_id);
	}
	
	/**
	 * 位置情報からクラスが持つ条件が一致するかを確認します。
	 * @param loc 位置情報
	 * @return 条件が一致した場合trueを返します。
	 */
	public boolean isMatchOptions(DungeonLocation loc) {
		
		DungeonLocation ll;
		
		for (DungeonLocation l : this.locs) {
			
			ll = loc;
			ll.add(l.getX(), l.getY(), l.getZ());
			
			if (!isMatchOption(ll))
				return false;
			
		}
		
		return true;
	}
	
	private boolean isMatchOption(DungeonLocation loc) {
		
		// 高さ情報の比較
		if (!this.height.isEmpty()) {
			if (!this.height.contains(loc.getBlockY()))
				return false;
		}
		
		// バイオーム条件の比較
		if (!this.biomes.contains(
				loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockZ())))
			return false;
		
		// ブロック条件の比較
		if (!this.blocks.contains(
				loc.getWorld().getBlockID(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())))
			return false;
		
		return true;
	}
	
	/**
	 * 関数名を返します。
	 * @return 関数名
	 */
	public String getFunction() {
		return this.function;
	}
	
	/**
	 * 生成確率を返します。
	 * @return 生成確率
	 */
	public double getPercent() {
		return this.percent;
	}
	
	/**
	 * 条件に含まれた位置情報一覧を返します。
	 * 位置情報は全て相対指定です。
	 * @return 位置情報一覧
	 */
	public List<DungeonLocation> getLocations() {
		return new ArrayList<DungeonLocation>(this.locs);
	}
	
	/**
	 * 条件に含まれたブロック情報一覧を返します。
	 * @return ブロック情報一覧
	 */
	public List<String> getBlocks() {
		return new ArrayList<String>(this.blocks);
	}
	
	/**
	 * 条件に含まれたバイオーム情報一覧を返します。
	 * @return バイオーム情報一覧
	 */
	public List<String> getBiomes() {
		return new ArrayList<String>(this.biomes);
	}

	@Override
	public String toString() {
		return "GenerateOption [function=" + function + ", percent=" + percent + ", locs=" + locs + ", blocks=" + blocks
				+ ", biomes=" + biomes + "]";
	}
	
}
