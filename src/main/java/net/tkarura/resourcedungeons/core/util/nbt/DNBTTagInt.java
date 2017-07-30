package net.tkarura.resourcedungeons.core.util.nbt;

/**
 * int型のタグ情報を表すクラスです。
 * @author the_karura
 */
public class DNBTTagInt extends DNBTNumber {

	// タグの値
	private int value;

	/**
	 * 中身の情報を0にして生成します。
	 */
	public DNBTTagInt() {
		this(0);
	}

	/**
	 * 中身の情報を指定して生成します。
	 *
	 * @param value 値
	 */
	public DNBTTagInt(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}

	@Override
	public byte getTypeId() {
		return (byte) 3;
	}

	@Override
	public DNBTBase clone() {
		return new DNBTTagInt(this.value);
	}

	@Override
	public String toString() {
		return this.value + "";
	}

}
