package net.tkarura.resourcedungeons.core.util.nbt;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 複数のタグ情報を取りまとめたクラスです。
 * @author the_karura
 */
public class DNBTTagCompound extends DNBTBase {

	// タグの値
	private Map<String, DNBTBase> value = new HashMap<>();

	/**
	 * 空のタグ情報を生成します。
	 */
	public DNBTTagCompound() {
	}

	/**
	 * タグ情報の複製を生成します。
	 *
	 * @param clone 複製する情報
	 */
	public DNBTTagCompound(DNBTTagCompound clone) {
		this.value = new HashMap<>(clone.value);
	}

	/**
	 * タグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void set(String key, DNBTBase nbt) {
		this.value.put(key, nbt);
	}

	/**
	 * byte型タグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setByte(String key, DNBTTagByte nbt) {
		set(key, nbt);
	}

	/**
	 * short型タグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setShort(String key, DNBTTagShort nbt) {
		set(key, nbt);
	}

	/**
	 * int型タグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setInt(String key, DNBTTagInt nbt) {
		set(key, nbt);
	}

	/**
	 * long型タグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setLong(String key, DNBTTagLong nbt) {
		set(key, nbt);
	}

	/**
	 * float型タグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setFloat(String key, DNBTTagFloat nbt) {
		set(key, nbt);
	}

	/**
	 * double型タグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setDouble(String key, DNBTTagDouble nbt) {
		set(key, nbt);
	}

	/**
	 * Stringタグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setString(String key, DNBTTagString nbt) {
		set(key, nbt);
	}

	/**
	 * byte arrayタグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setByteArray(String key, DNBTTagByteArray nbt) {
		set(key, nbt);
	}

	/**
	 * int arrayタグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setIntArray(String key, DNBTTagIntArray nbt) {
		set(key, nbt);
	}

	/**
	 * リストタグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setList(String key, DNBTTagList nbt) {
		set(key, nbt);
	}

	/**
	 * 構成情報を含めます。
	 *
	 * @param key 鍵
	 * @param nbt 値
	 */
	public void setCompound(String key, DNBTTagCompound nbt) {
		set(key, nbt);
	}

	/**
	 * byte型タグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param value 値
	 */
	public void setByte(String key, byte value) {
		set(key, new DNBTTagByte(value));
	}

	/**
	 * short型タグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param value 値
	 */
	public void setShort(String key, short value) {
		set(key, new DNBTTagShort(value));
	}

	/**
	 * int型タグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param value 値
	 */
	public void setInt(String key, int value) {
		set(key, new DNBTTagInt(value));
	}

	/**
	 * long型タグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param value 値
	 */
	public void setLong(String key, long value) {
		set(key, new DNBTTagLong(value));
	}

	/**
	 * float型タグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param value 値
	 */
	public void setFloat(String key, float value) {
		set(key, new DNBTTagFloat(value));
	}

	/**
	 * double型タグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param value 値
	 */
	public void setDouble(String key, double value) {
		set(key, new DNBTTagDouble(value));
	}

	/**
	 * Stringタグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param value 値
	 */
	public void setString(String key, String value) {
		set(key, new DNBTTagString(value));
	}

	/**
	 * byte arrayタグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param value 値
	 */
	public void setByteArray(String key, byte[] value) {
		set(key, new DNBTTagByteArray(value));
	}

	/**
	 * int arrayタグ情報を含めます。
	 *
	 * @param key 鍵
	 * @param value 値
	 */
	public void setIntArray(String key, int[] value) {
		set(key, new DNBTTagIntArray(value));
	}

	/**
	 * 指定した要素のタグ情報を削除します。
	 *
	 * @param key 削除する要素の鍵
	 */
	public void remove(String key) {
		this.value.remove(key);
	}

	/**
	 * この構成が持つ要素が空であるかを判定します。
	 *
	 * @return 要素が空の場合 true
	 */
	public boolean isEmpty() {
		return this.value.isEmpty();
	}

	/**
	 * 鍵を指定してタグ情報を取得します。
	 *
	 * @param key 鍵
	 * @return 値
	 */
	public Object get(String key) {
	    DNBTBase value = this.value.get(key);
	    if (value != null) {
	        return value.getValue();
        }
		return null;
	}

	/**
	 * 鍵を指定してbyte型の値を取得します。
	 *
	 * @param key 鍵
	 * @return 値
	 */
	public byte getByte(String key) {
	    return getByte(key, (byte) 0);
    }

	/**
	 * 鍵を指定してshort型の値を取得します。
	 *
	 * @param key 鍵
	 * @return 値
	 */
	public short getShort(String key) {
	    return getShort(key, (short) 0);
	}

	/**
	 * 鍵を指定してint型の値を取得します。
	 *
	 * @param key 鍵
	 * @return 値
	 */
	public int getInt(String key) {
	    return getInt(key, 0);
	}

	/**
	 * 鍵を指定してfloat型の値を取得します。
	 *
	 * @param key 鍵
	 * @return 値
	 */
	public float getFloat(String key) {
	    return getFloat(key, 0.0f);
	}

	/**
	 * 鍵を指定してdouble型の値を取得します。
	 *
	 * @param key 鍵
	 * @return 値
	 */
	public double getDouble(String key) {
	    return getDouble(key, 0.0);
	}

	/**
	 * 鍵を指定してStringの値を取得します。
	 *
	 * @param key 鍵
	 * @return 値
	 */
	public String getString(String key) {
	    return getString(key, "");
	}

	/**
	 * 鍵を指定してbyte arrayの値を取得します。
	 *
	 * @param key 鍵
	 * @return 値
	 */
	public byte[] getByteArray(String key) {
	    return getByteArray(key, new byte[0]);
	}

	/**
	 * 鍵を指定してint arrayの値を取得します。
	 *
	 * @param key 鍵
	 * @return 値
	 */
	public int[] getIntArray(String key) {
	    return getIntArray(key, new int[0]);
	}

	/**
	 * 鍵を指定してListの値を取得します。
	 *
	 * @param key 鍵
	 * @return 値
	 */
	public DNBTTagList getList(String key) {
		try {
			return this.value.containsKey(key) ? (DNBTTagList) this.value.get(key) : new DNBTTagList();
		} catch (ClassCastException e) {
			return new DNBTTagList();
		}
	}

	/**
	 * 鍵を指定してNBTTagCompoundの情報を取得します。
	 *
	 * @param key 鍵
	 * @return 情報
	 */
	public DNBTTagCompound getCompound(String key) {
		try {
			return this.value.containsKey(key) ? (DNBTTagCompound) this.value.get(key) : new DNBTTagCompound();
		} catch (ClassCastException e) {
			return new DNBTTagCompound();
		}
	}

    /**
     * 鍵を指定してbyte型の値を取得します。
     *
     * @param key 鍵
     * @param def 存在しない場合の値
     * @return 値
     */
    public byte getByte(String key, byte def) {
        DNBTBase base = this.value.get(key);
        if (base == null) {
            return def;
        }
        if (base instanceof DNBTNumber) {
            return ((DNBTNumber) base).getValueByte();
        }
        if (base instanceof DNBTTagString) {
            return NumberUtils.toByte(((DNBTTagString) base).getValue(), def);
        }
        return def;
    }

    /**
     * 鍵を指定してshort型の値を取得します。
     *
     * @param key 鍵
     * @param def 存在しない場合の値
     * @return 値
     */
    public short getShort(String key, short def) {
        DNBTBase base = this.value.get(key);
        if (base == null) {
            return def;
        }
        if (base instanceof DNBTNumber) {
            return ((DNBTNumber) base).getValueShort();
        }
        if (base instanceof DNBTTagString) {
            return NumberUtils.toShort(((DNBTTagString) base).getValue(), def);
        }
        return def;
    }

    /**
     * 鍵を指定してint型の値を取得します。
     *
     * @param key 鍵
     * @return 値
     */
    public int getInt(String key, int def) {
        DNBTBase base = this.value.get(key);
        if (base == null) {
            return def;
        }
        if (base instanceof DNBTNumber) {
            return ((DNBTNumber) base).getValueInt();
        }
        if (base instanceof DNBTTagString) {
            return NumberUtils.toInt(((DNBTTagString) base).getValue(), def);
        }
        return def;
    }

    /**
     * 鍵を指定してfloat型の値を取得します。
     *
     * @param key 鍵
     * @return 値
     */
    public float getFloat(String key, float def) {
        DNBTBase base = this.value.get(key);
        if (base == null) {
            return def;
        }
        if (base instanceof DNBTNumber) {
            return ((DNBTNumber) base).getValueFloat();
        }
        if (base instanceof DNBTTagString) {
            return NumberUtils.toFloat(((DNBTTagString) base).getValue(), def);
        }
        return def;
    }

    /**
     * 鍵を指定してdouble型の値を取得します。
     *
     * @param key 鍵
     * @return 値
     */
    public double getDouble(String key, double def) {
        DNBTBase base = this.value.get(key);
        if (base == null) {
            return def;
        }
        if (base instanceof DNBTNumber) {
            return ((DNBTNumber) base).getValueDouble();
        }
        if (base instanceof DNBTTagString) {
            return NumberUtils.toDouble(((DNBTTagString) base).getValue(), def);
        }
        return def;
    }

    /**
     * 鍵を指定してStringの値を取得します。
     *
     * @param key 鍵
     * @return 値
     */
    public String getString(String key, String def) {
        DNBTBase base = this.value.get(key);
        if (base == null) {
            return def;
        }
        if (base instanceof DNBTNumber) {
            return String.valueOf(((DNBTNumber) base).getValue());
        }
        if (base instanceof DNBTTagString) {
            return ((DNBTTagString) base).getValue();
        }
        return def;
    }

    /**
     * 鍵を指定してbyte arrayの値を取得します。
     *
     * @param key 鍵
     * @return 値
     */
    public byte[] getByteArray(String key, byte[] def) {
        DNBTBase base = this.value.get(key);
        if (base != null && base instanceof DNBTTagByteArray) {
            return ((DNBTTagByteArray) base).getValue();
        }
        return def;
    }

    /**
     * 鍵を指定してint arrayの値を取得します。
     *
     * @param key 鍵
     * @return 値
     */
    public int[] getIntArray(String key, int[] def) {
        DNBTBase base = this.value.get(key);
        if (base != null && base instanceof DNBTTagIntArray) {
            return ((DNBTTagIntArray) base).getValue();
        }
        return def;
    }

    public Set<String> getKeys() {
    	return this.value.keySet();
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
