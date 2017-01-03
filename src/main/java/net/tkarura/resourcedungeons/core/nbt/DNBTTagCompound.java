package net.tkarura.resourcedungeons.core.nbt;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 複数のタグ情報を取りまとめたクラスです。
 * @author the_karura
 */
public class DNBTTagCompound extends DNBTBase {
	
	// タグの値
	private Map<String, DNBTBase> value = new HashMap<String, DNBTBase>();
	
	/**
	 * 空のタグ情報を生成します。
	 */
	public DNBTTagCompound() {}
	
	/**
	 * タグ情報の複製を生成します。
	 * @param clone 複製する情報
	 */
	public DNBTTagCompound(DNBTTagCompound clone) {
		this.value = new HashMap<String, DNBTBase>(clone.value);
	}
	
	/**
	 * タグ情報を含めます。
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void set(String key, DNBTBase nbt) {
		this.value.put(key, nbt);
	}
	
	/**
	 * byte型タグ情報を含めます。
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setByte(String key, DNBTTagByte nbt) {
		set(key, nbt);
	}
	
	/**
	 * short型タグ情報を含めます。
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setShort(String key, DNBTTagShort nbt) {
		set(key, nbt);
	}
	
	/**
	 * int型タグ情報を含めます。
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setInt(String key, DNBTTagInt nbt) {
		set(key, nbt);
	}
	
	/**
	 * long型タグ情報を含めます。
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setLong(String key, DNBTTagLong nbt) {
		set(key, nbt);
	}
	
	/**
	 * float型タグ情報を含めます。
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setFloat(String key, DNBTTagFloat nbt) {
		set(key, nbt);
	}
	
	/**
	 * double型タグ情報を含めます。
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setDouble(String key, DNBTTagDouble nbt) {
		set(key, nbt);
	}
	
	/**
	 * Stringタグ情報を含めます。
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setString(String key, DNBTTagString nbt) {
		set(key, nbt);
	}
	
	/**
	 * byte arrayタグ情報を含めます。
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setByteArray(String key, DNBTTagByteArray nbt) {
		set(key, nbt);
	}
	
	/**
	 * int arrayタグ情報を含めます。
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setIntArray(String key, DNBTTagIntArray nbt) {
		set(key, nbt);
	}
	
	/**
	 * リストタグ情報を含めます。
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setList(String key, DNBTTagList nbt) {
		set(key, nbt);
	}
	
	/**
	 * 構成情報を含めます。
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setCompound(String key, DNBTTagCompound nbt) {
	    set(key, nbt);
	}
	
	/**
	 * 鍵を指定してタグ情報を取得します。
	 * @param key 鍵
	 * @return 値
	 */
	public DNBTBase get(String key) {
		return this.value.get(key);
	}
	
	/**
	 * 鍵を指定してbyte型の値を取得します。
	 * @param key 鍵
	 * @return 値
	 */
	public byte getByte(String key) {
		try {
			return this.value.containsKey(key) ? (Byte) this.value.get(key).getValue() : (byte) 0;
		} catch (ClassCastException e) {
			return (byte) 0;
		}
	}
	
	/**
	 * 鍵を指定してshort型の値を取得します。
	 * @param key 鍵
	 * @return 値
	 */
	public short getShort(String key) {
		try {
			return this.value.containsKey(key) ? (Short) this.value.get(key).getValue() : (short) 0;
		} catch (ClassCastException e) {
			return (short) 0;
		}
	}
	
	/**
	 * 鍵を指定してint型の値を取得します。
	 * @param key 鍵
	 * @return 値
	 */
	public int getInt(String key) {
		try {
			return this.value.containsKey(key) ? (Integer) this.value.get(key).getValue() : 0;
		} catch (ClassCastException e) {
			return 0;
		}
	}
	
	/**
	 * 鍵を指定してfloat型の値を取得します。
	 * @param key 鍵
	 * @return 値
	 */
	public float getFloat(String key) {
		try {
			return this.value.containsKey(key) ? (Float) this.value.get(key).getValue() : (float) 0;
		} catch (ClassCastException e) {
			return (float) 0;
		}
	}
	
	/**
	 * 鍵を指定してdouble型の値を取得します。
	 * @param key 鍵
	 * @return 値
	 */
	public double getDouble(String key) {
		try {
			return this.value.containsKey(key) ? (Double) this.value.get(key).getValue() : 0;
		} catch (ClassCastException e) {
			return 0;
		}
	}
	
	/**
	 * 鍵を指定してStringの値を取得します。
	 * @param key 鍵
	 * @return 値
	 */
	public String getString(String key) {
		try {
			return this.value.containsKey(key) ? (String) this.value.get(key).getValue() : "";
		} catch (ClassCastException e) {
			return "";
		}
	}
	
	/**
	 * 鍵を指定してbyte arrayの値を取得します。
	 * @param key 鍵
	 * @return 値
	 */
	public byte[] getByteArray(String key) {
		try {
			return this.value.containsKey(key) ? (byte[]) this.value.get(key).getValue() : new byte[0];
		} catch (ClassCastException e) {
			return new byte[0];
		}
	}
	
	/**
	 * 鍵を指定してint arrayの値を取得します。
	 * @param key 鍵
	 * @return 値
	 */
	public int[] getIntArray(String key) {
		try {
			return this.value.containsKey(key) ? (int[]) this.value.get(key).getValue() : new int[0];
		} catch (ClassCastException e) {
			return new int[0];
		}
	}
	
	/**
	 * 鍵を指定してListの値を取得します。
	 * @param key 鍵
	 * @return 値
	 */
	public DNBTTagList getList(String key) {
		try {
			return this.value.containsKey(key) ? (DNBTTagList) this.value.get(key).getValue() : new DNBTTagList();
		} catch (ClassCastException e) {
			return new DNBTTagList();
		}
	}
	
	/**
	 * 鍵を指定してNBTTagCompoundの情報を取得します。
	 * @param key 鍵
	 * @return 情報
	 */
	public DNBTTagCompound getCompound(String key) {
		try {
			return this.value.containsKey(key) ? (DNBTTagCompound) this.value.get(key).getValue() : new DNBTTagCompound();
		} catch (ClassCastException e) {
			return new DNBTTagCompound();
		}
	}
	
	@Override
	public Map<String, DNBTBase> getValue() {
		return this.value;
	}
	
	@Override
	public byte getTypeId() {
		return (byte) 10;
	}
	
	@Override
	public DNBTBase clone() {
		DNBTTagCompound compound = new DNBTTagCompound();
		for (Entry<String, DNBTBase> e : this.value.entrySet()) {
			compound.set(e.getKey(), e.getValue().clone());
		}
		return compound;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("{");
		for (Entry<String, DNBTBase> entry : value.entrySet()) {
			builder
			.append(entry.getKey()).append(":")
			.append(entry.getValue()).append(",");
		}
		return builder.append("}").toString();
	}
	
}
