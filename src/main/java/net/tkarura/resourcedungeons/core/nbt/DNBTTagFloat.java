package net.tkarura.resourcedungeons.core.nbt;

/**
 * float型のタグ情報を表すクラスです。
 * @author the_karura
 */
public class DNBTTagFloat extends DNBTNumber {
	
	// タグの値
	private float value;
	
	/**
	 * 中身の情報を0にして生成します。
	 */
	public DNBTTagFloat() {
		this(0F);
	}
	
	/**
	 * 中身の情報を指定して生成します。
	 * @param value 値
	 */
	public DNBTTagFloat(float value) {
		this.value = value;
	}
	
	@Override
	public Number getValue() {
		return this.value;
	}
	
	@Override
	public byte getTypeId() {
		return 5;
	}
	
	@Override
	public DNBTBase clone() {
		return new DNBTTagFloat(this.value);
	}
	
	@Override
	public String toString() {
		return this.value + "F";
	}
	
}
