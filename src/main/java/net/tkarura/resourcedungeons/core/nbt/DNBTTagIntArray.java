package net.tkarura.resourcedungeons.core.nbt;

/**
 * int arrayタグ情報を表すクラスです。
 * @author the_karura
 */
public class DNBTTagIntArray extends DNBTBase {
	
	// タグの値
	private int[] value;
	
	/**
	 * 中身の情報を0にして生成します。
	 */
	public DNBTTagIntArray() {
		this(new int[0]);
	}
	
	/**
	 * 中身の情報を指定して生成します。
	 * @param value 値
	 */
	public DNBTTagIntArray(int value[]) {
		this.value = value;
	}
	
	@Override
	public int[] getValue() {
		return this.value;
	}
	
	@Override
	public byte getTypeId() {
		return 11;
	}
	
	@Override
	public DNBTBase clone() {
		return new DNBTTagIntArray(this.value);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for (int i = 0; i < value.length; i++) {
			builder.append(value[i]).append(",");
		}
		builder.append("]");
		return builder.toString();
	}
	
}
