package net.tkarura.resourcedungeons.core.util.nbt;

/**
 * 数値に関するNBTTagを表すクラスです。
 * 数値関連のみを取り扱う場合などに使用します。
 * @author the_karura
 */
public abstract class DNBTNumber extends DNBTBase {

	@Override
	public abstract Number getValue();

}
