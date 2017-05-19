package net.tkarura.resourcedungeons.core.util.nbt;

/**
 * 文字列を表す
 * @author the_karura
 */
public class DNBTTagString extends DNBTBase {
	
	// タグの値
	private String value;
	
	/**
	 * 空の文字列を指定して生成します。
	 */
	public DNBTTagString() {
		this("");
	}
	
	/**
	 * 文字列を指定して生成します。
	 * @param value 指定する文字列
	 */
	public DNBTTagString(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return this.value;
	}
	
	@Override
	public byte getTypeId() {
		return 8;
	}
	
	@Override
	public DNBTBase clone() {
		return new DNBTTagString(this.value);
	}
	
	@Override
	public String toString() {
		return new StringBuilder().append("\"").append(value).append("\"").toString();
	}
	
}
