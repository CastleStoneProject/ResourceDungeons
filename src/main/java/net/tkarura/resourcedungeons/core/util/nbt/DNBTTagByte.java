package net.tkarura.resourcedungeons.core.util.nbt;

/**
 * byte型のタグ情報を表すクラスです。
 * @author the_karura
 */
public class DNBTTagByte extends DNBTNumber {

	// タグの値
	private byte value;

	/**
	 * 中身の情報を0にして生成します。
	 */
	public DNBTTagByte() {
		this.value = 0;
	}

	/**
	 * 中身の情報を指定して生成します。
	 *
	 * @param value 値
	 */
	public DNBTTagByte(byte value) {
		this.value = value;
	}

	@Override
	public Byte getValue() {
		return value;
	}

	@Override
	public byte getTypeId() {
		return (byte) 1;
	}

	@Override
	public DNBTBase clone() {
		return new DNBTTagByte(this.value);
	}

	@Override
	public String toString() {
		return value + "B";
	}

}
