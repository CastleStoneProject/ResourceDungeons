package net.tkarura.resourcedungeons.core.util.nbt;

/**
 * byte型配列のタグ情報を表すクラスです。
 * @author the_karura
 */
public class DNBTTagByteArray extends DNBTBase {

	// タグの値
	private byte[] value;

	/**
	 * 中身の情報を0個の配列にして生成します。
	 */
	public DNBTTagByteArray() {
		this(new byte[0]);
	}

	/**
	 * 中身の情報を指定して生成します。
	 *
	 * @param value 値
	 */
	public DNBTTagByteArray(byte[] value) {
		this.value = value;
	}

	@Override
	public byte[] getValue() {
		return this.value;
	}

	@Override
	public byte getTypeId() {
		return 7;
	}

	@Override
	public DNBTBase clone() {
		return new DNBTTagByteArray(this.value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (int i = 0; i < value.length; i++) {
			builder.append(value[i]).append(",");
		}
		return builder.append("]").toString();
	}

}
