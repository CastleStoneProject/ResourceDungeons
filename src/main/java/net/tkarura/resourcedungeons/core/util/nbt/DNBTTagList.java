package net.tkarura.resourcedungeons.core.util.nbt;

import java.util.ArrayList;
import java.util.List;

/**
 * 複数のタグ情報をリスト構造に管理するタグを返します。
 * @author the_karura
 */
public class DNBTTagList extends DNBTBase {
	
	private List<DNBTBase> value = new ArrayList<DNBTBase>();
	
	/**
	 * 空のリストを生成します。
	 */
	public DNBTTagList() {
	}
	
	/**
	 * リストを複製します。
	 * @param clone
	 */
	public DNBTTagList(DNBTTagList clone) {
		this.value.addAll(clone.value);
	}
	
	/**
	 * リストに追加します。
	 * @param value
	 */
	public void add(DNBTBase value) {
		this.value.add(value);
	}
	
	/**
	 * 指定した位置にタグ情報を設定します。
	 * @param index 設定する位置
	 * @param value 設定するタグ
	 */
	public void set(int index, DNBTBase value) {
		this.value.set(index, value);
	}
	
	/**
	 * 指定した位置情報のタグを習得します。
	 * @param index 習得する位置
	 * @return タグ情報
	 */
	public Object get(int index) {
		return this.value.get(index);
	}
	
	/**
	 * 指定した位置情報のbyteタグを習得します。
	 * @param index 位置情報
	 * @return タグ情報
	 */
	public byte getByte(int index) {
		try {
			return (Byte) this.value.get(index).getValue();
		} catch (ClassCastException e) {
			return (byte) 0;
		}
	}
	
	/**
	 * 指定した位置情報のshortタグを習得します。
	 * @param index 位置情報
	 * @return タグ情報
	 */
	public short getShort(int index) {
		try {
			return (Short) this.value.get(index).getValue();
		} catch (ClassCastException e) {
			return (byte) 0;
		}
	}
	
	/**
	 * 指定した位置情報のintタグを習得します。
	 * @param index 位置情報
	 * @return タグ情報
	 */
	public int getInt(int index) {
		try {
			return (Integer) this.value.get(index).getValue();
		} catch (ClassCastException e) {
			return (byte) 0;
		}
	}
	
	/**
	 * 指定した位置情報のfloatタグを習得します。
	 * @param index 位置情報
	 * @return タグ情報
	 */
	public float getFloat(int index) {
		try {
			return (Float) this.value.get(index).getValue();
		} catch (ClassCastException e) {
			return (byte) 0;
		}
	}
	
	/**
	 * 指定した位置情報のdoubleタグを習得します。
	 * @param index 位置情報
	 * @return タグ情報
	 */
	public double getDouble(int index) {
		try {
			return (Double) this.value.get(index).getValue();
		} catch (ClassCastException e) {
			return (byte) 0;
		}
	}
	
	/**
	 * 指定した位置情報のbyte arrayタグを習得します。
	 * @param index 位置情報
	 * @return タグ情報
	 */
	public byte[] getByteArray(int index) {
		try {
			return (byte[]) this.value.get(index).getValue();
		} catch (ClassCastException e) {
			return new byte[0];
		}
	}
	
	/**
	 * 指定した位置情報のStringタグを習得します。
	 * @param index 位置情報
	 * @return タグ情報
	 */
	public String getString(int index) {
		try {
			return (String) this.value.get(index).getValue();
		} catch (ClassCastException e) {
			return "";
		}
	}
	
	/**
	 * 指定した位置情報のNBTTagCompoundタグを習得します。
	 * @param index 位置情報
	 * @return タグ情報
	 */
	public DNBTTagCompound getNBTTagCompound(int index) {
		try {
			return (DNBTTagCompound) this.value.get(index);
		} catch (ClassCastException e) {
			return new DNBTTagCompound();
		}
	}
	
	/**
	 * リストのサイズを習得します。
	 * @return リストが持つタグの数
	 */
	public int size() {
		return this.value.size();
	}
	
	@Override
	public List<DNBTBase> getValue() {
		return this.value;
	}
	
	@Override
	public byte getTypeId() {
		return (byte) 9;
	}
	
	@Override
	public DNBTBase clone() {
		DNBTTagList list = new DNBTTagList();
		for (int i = 0; i < this.value.size(); i++) {
			list.add(this.value.get(i).clone());
		}
		return list;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		for (int i = 0; i < this.value.size(); i++) {
			builder.append(i).append(":")
			.append(this.value.get(i).toString()).append(",");
		}
		return builder.append("]").toString();
	}
	
}
