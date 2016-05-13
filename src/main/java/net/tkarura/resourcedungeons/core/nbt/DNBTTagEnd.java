package net.tkarura.resourcedungeons.core.nbt;

/**
 * NBTTagの終端を表すタグです。
 * @deprecated 現在の所これと同様の機能をMinecraft側が行うので形だけの実装として置いているのが現状です。
 * そのためこれを使用した機能の実装などは行わないでください。動作の不安定化を招きます。
 * @author the_karura
 */
@Deprecated
public class DNBTTagEnd extends DNBTBase {

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public byte getTypeId() {
		return (byte) 0;
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
