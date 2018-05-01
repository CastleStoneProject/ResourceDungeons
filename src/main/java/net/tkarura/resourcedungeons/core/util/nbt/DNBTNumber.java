package net.tkarura.resourcedungeons.core.util.nbt;

/**
 * 数値に関するNBTTagを表すクラスです。
 * 数値関連のみを取り扱う場合などに使用します。
 * @author the_karura
 */
public abstract class DNBTNumber extends DNBTBase {

	public abstract byte getValueByte();

	public abstract short getValueShort();

	public abstract int getValueInt();

	public abstract long getValueLong();

	public abstract float getValueFloat();

	public abstract double getValueDouble();

	@Override
	public abstract Number getValue();

}
