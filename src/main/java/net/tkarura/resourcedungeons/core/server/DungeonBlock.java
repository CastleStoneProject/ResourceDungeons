package net.tkarura.resourcedungeons.core.server;

import net.tkarura.resourcedungeons.core.nbt.DNBTTagCompound;

/**
 * ブロックに関す情報を保管するクラスです。
 * TileEntityに関する情報も取り扱います。
 * @author the_karura
 */
public class DungeonBlock {
	
	protected DungeonLocation loc;
	protected String registory_id;
	protected byte damage;
	protected DNBTTagCompound nbt;
	
	/**
	 * ブロック情報として生成します。
	 * @param loc ブロックの位置
	 * @param registory_id ブロックID
	 * @param damage ダメージ値
	 */
	public DungeonBlock(DungeonLocation loc, String registory_id, byte damage) {
		this(loc, registory_id, damage, new DNBTTagCompound());
	}
	
	/**
	 * TileEntityとして生成します。
	 * @param loc ブロックの位置
	 * @param registory_id ブロックID
	 * @param damage ダメージ値
	 * @param nbt NBTタグ
	 */
	public DungeonBlock(DungeonLocation loc, String registory_id, byte damage, DNBTTagCompound nbt) {
		this.registory_id = registory_id;
		this.damage = damage;
		this.nbt = new DNBTTagCompound(nbt);
	}
	
	/**
	 * ダメージ値を設定します。
	 * @param damage ダメージ値
	 * @return ダメージ値が変更されたブロック情報
	 */
	public DungeonBlock setDamage(byte damage) {
		loc.getWorld().setBlock(loc, registory_id, damage, false);
		return loc.getWorld().getBlock(loc);
	}
	
	/**
	 * ブロックIDを返します。
	 * @return ブロックID
	 */
	public String getID() {
		return this.registory_id;
	}
	
	/**
	 * ダメージ値を返します。
	 * @return ダメージ値
	 */
	public byte getDamage() {
		return this.damage;
	}
	
	/**
	 * 位置情報を返します。
	 * @return 位置情報
	 */
	public DungeonLocation getLocation() {
		return this.loc;
	}
	
	/**
	 * NBTタグ情報を返します。
	 * TileEntityで無い場合はnullを返します。
	 * @return NBTタグ情報
	 */
	public DNBTTagCompound getNBTTagCompound() {
		return this.nbt;
	}
	
}
