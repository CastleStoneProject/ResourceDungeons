package net.tkarura.resourcedungeons.core.util.nbt;

/**
 * long型のタグ情報を表すクラス
 * @author the_karura
 */
public class DNBTTagLong extends DNBTNumber {

	// タグの値
	private long value;

	/**
	 * 中身の情報を0にして生成します。
	 */
	public DNBTTagLong() {
		this(0L);
	}

	/**
	 * 中身の情報を指定して生成します。
	 *
	 * @param value 値
	 */
	public DNBTTagLong(long value) {
		this.value = value;
	}

	@Override
	public Long getValue() {
		return this.value;
	}

	@Override
	public byte getTypeId() {
		return 4;
	}

	@Override
	public DNBTBase clone() {
		return new DNBTTagLong(this.value);
	}

	@Override
	public String toString() {
		return this.value + "L";
	}

}
