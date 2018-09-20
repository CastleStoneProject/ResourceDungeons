package net.tkarura.resourcedungeons.core.util.nbt;

/**
 * NBTTagの終端を表すタグです。
 * @author the_karura
 */
public class DNBTTagEnd extends DNBTBase {

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public byte getTypeId() {
		return DNBTBase.TAG_END;
	}

	@Override
	public String toString() {
		return "END";
	}

	@Override
	public DNBTBase clone() {
		return new DNBTTagEnd();
	}

}
