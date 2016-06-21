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
	private final List<DungeonLocation> locs = new ArrayList<DungeonLocation>();
	private final List<String> blocks = new ArrayList<String>();
	private final List<String> biomes = new ArrayList<String>();
	
	/**
	 * 条件が一致した時に呼び出される関数名を指定して生成
	 * @param function 関数名
	 */
	public GenerateOption(String function) {
		this(function, 100.0f);
	}
	
	public GenerateOption(String function, double percent) {
		this.function = function;
		this.percent = percent;
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
		
		if (!this.biomes.contains(
				loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockZ())))
			return false;
		
		if (!this.biomes.contains(
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
		return "GenerateOption [" + (locs != null ? "locs=" + locs + ", " : "")
				+ (blocks != null ? "blocks=" + blocks + ", " : "") + (biomes != null ? "biomes=" + biomes : "") + "]";
	}
	
}
