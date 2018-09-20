package net.tkarura.resourcedungeons.core.util.nbt;

/**
 * double型のタグ情報を表すクラスです。
 * @author the_karura
 */
public class DNBTTagDouble extends DNBTNumber {

	// タグの値
	private double value;

	/**
	 * 中身の情報を0にして生成します。
	 */
	public DNBTTagDouble() {
		this(0D);
	}

	/**
	 * 中身の情報を指定して生成します。
	 *
	 * @param value 値
	 */
	public DNBTTagDouble(double value) {
		this.value = value;
	}

	@Override
	public byte getValueByte() {
		return (byte) Math.floor(value);
	}

	@Override
	public short getValueShort() {
		return (short) Math.round(value);
	}

	@Override
	public int getValueInt() {
		return (int) Math.round(value);
	}

	@Override
	public long getValueLong() {
		return Math.round(value);
	}

	@Override
	public float getValueFloat() {
		return (float) value;
	}

	@Override
	public double getValueDouble() {
		return value;
	}

	@Override
	public Double getValue() {
		return this.value;
	}

	@Override
	public byte getTypeId() {
		return DNBTBase.TAG_DOUBLE;
	}

	@Override
	public DNBTBase clone() {
		return new DNBTTagDouble(this.value);
	}

	@Override
	public String toString() {
		return this.value + "D";
	}

}
