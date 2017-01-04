package net.tkarura.resourcedungeons.core.nbt;

/**
 * NBTTagに関するクラスの根幹部分を表すクラスです。
 * NBTTagに共通する処理およびNBTTagの種類を識別する為の情報を提供します。
 * @author the_karura
 */
public abstract class DNBTBase {
	
	public final static byte TAG_END = 0;
	public final static byte TAG_BYTE = 1;
	public final static byte TAG_SHORT = 2;
	public final static byte TAG_INT = 3;
	public final static byte TAG_LONG = 4;
	public final static byte TAG_FLOAT = 5;
	public final static byte TAG_DOUBLE = 6;
	public final static byte TAG_BYTE_ARRAY = 7;
	public final static byte TAG_STRING = 8;
	public final static byte TAG_LIST = 9;
	public final static byte TAG_COMPOUND = 10;
	public final static byte TAG_INT_ARRAY = 11;
	
	/**
	 * 生成します。
	 */
	protected DNBTBase() {}
	
	/**
	 * タグが持つ情報を返します。
	 * @return タグが所有する情報
	 */
	public abstract Object getValue();
	
	/**
	 * タグの種類を識別するIDを返します。
	 * @return タグのID
	 */
	public abstract byte getTypeId();
	
	/**
	 * タグを指定して該当のタグを生成します。
	 * @param id タグID
	 * @return 該当の空のタグ情報 もし該当しないIDを指定した場合nullが返されます。
	 */
	protected static DNBTBase createTag(byte id) {
		switch (id) {
		case TAG_END:
			return new DNBTTagEnd();
		case TAG_BYTE:
			return new DNBTTagByte();
		case TAG_SHORT:
			return new DNBTTagShort();
		case TAG_INT:
			return new DNBTTagInt();
		case TAG_LONG:
			return new DNBTTagLong();
		case TAG_FLOAT:
			return new DNBTTagFloat();
		case TAG_DOUBLE:
			return new DNBTTagDouble();
		case TAG_BYTE_ARRAY:
			return new DNBTTagByteArray();
		case TAG_STRING:
			return new DNBTTagString();
		case TAG_LIST:
			return new DNBTTagList();
		case TAG_COMPOUND:
			return new DNBTTagCompound();
		case TAG_INT_ARRAY:
			return new DNBTTagIntArray();
		default:
			return null;
		}
	}
	
	/**
	 * byte引数のタグを返します。
	 * @param value byte
	 * @return byte引数が格納されたタグが返されます。
	 */
	public static DNBTTagByte valueOf(byte value) {
		return new DNBTTagByte(value);
	}
	
	/**
	 * short引数のタグを返します。
	 * @param value short
	 * @return short引数が格納されたタグが返されます。
	 */
	public static DNBTTagShort valueOf(short value) {
		return new DNBTTagShort(value);
	}
	
	/**
	 * int引数のタグを返します。
	 * @param value int
	 * @return int引数が格納されたタグが返されます。
	 */
	public static DNBTTagInt valueOf(int value) {
		return new DNBTTagInt(value);
	}
	
	/**
	 * long引数のタグを返します。
	 * @param value long
	 * @return long引数が格納されたタグが返されます。
	 */
	public static DNBTTagLong valueOf(long value) {
		return new DNBTTagLong(value);
	}
	
	/**
	 * float引数のタグを返します。
	 * @param value float
	 * @return float引数が格納されたタグが返されます。
	 */
	public static DNBTTagFloat valueOf(float value) {
		return new DNBTTagFloat(value);
	}
	
	/**
	 * double引数のタグを返します。
	 * @param value double
	 * @return double引数が格納されたタグが返されます。
	 */
	public static DNBTTagDouble valueOf(double value) {
		return new DNBTTagDouble(value);
	}
	
	/**
	 * byte配列引数のタグを返します。
	 * @param value byte配列
	 * @return byte配列引数が格納されたタグが返されます。
	 */
	public static DNBTTagByteArray valueOf(byte[] value) {
		return new DNBTTagByteArray(value);
	}
	
	/**
	 * String引数のタグを返します。
	 * @param value String
	 * @return String引数が格納されたタグが返されます。
	 */
	public static DNBTTagString valueOf(String value) {
		return new DNBTTagString(value);
	}
	
	/**
	 * int配列引数のタグを返します。
	 * @param value int配列
	 * @return int配列引数が格納されたタグが返されます。
	 */
	public static DNBTTagIntArray valueOf(int[] value) {
		return new DNBTTagIntArray(value);
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof DNBTBase)) {
			return false;
		}
		return this.getTypeId() == ((DNBTBase) object).getTypeId();
	}
	
	@Override
	public abstract DNBTBase clone();
	
	@Override
	public int hashCode() {
		return this.getTypeId();
	}
	
	@Override
	public abstract String toString();
	
}
