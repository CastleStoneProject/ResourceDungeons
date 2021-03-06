package net.tkarura.resourcedungeons.core.util.nbt;

/**
 * short型のタグ情報を表すクラス
 * @author the_karura
 */
public class DNBTTagShort extends DNBTNumber {

	// タグの値
	private short value;

	/**
	 * 中身の情報を0にして生成します。
	 */
	public DNBTTagShort() {
		this((short) 0);
	}

	/**
	 * 中身の情報を指定して生成します。
	 *
	 * @param value 値
	 */
	public DNBTTagShort(short value) {
		this.value = value;
	}

	@Override
	public byte getValueByte() {
		return (byte) value;
	}

	@Override
	public short getValueShort() {
		return value;
	}

	@Override
	public int getValueInt() {
		return value;
	}

	@Override
	public long getValueLong() {
		return value;
	}

	@Override
	public float getValueFloat() {
		return value;
	}

	@Override
	public double getValueDouble() {
		return value;
	}

	@Override
	public Short getValue() {
		return this.value;
	}

	@Override
	public byte getTypeId() {
		return (byte) 2;
	}

	@Override
	public DNBTBase clone() {
		return new DNBTTagShort(this.value);
	}

	@Override
	public String toString() {
		return this.value + "S";
	}

}
