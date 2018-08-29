package net.tkarura.resourcedungeons.core.util.nbt.stream;

import java.io.*;
import java.util.zip.GZIPInputStream;

import net.tkarura.resourcedungeons.core.util.nbt.DNBTBase;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagByte;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagByteArray;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagCompound;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagDouble;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagEnd;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagFloat;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagInt;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagIntArray;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagList;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagLong;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagShort;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagString;

/**
 * バイナリデータからNBTタグへ変換を行うクラスです。
 * 
 * @author the_karura
 */
public class DNBTDataInputStream implements AutoCloseable {
    
    // バイナリ読み取り用のストリーム
    protected DataInputStream stream;

    /**
     * データストリームを指定してクラスを生成します。
     * @param stream ストリームクラス
     */
	public DNBTDataInputStream(DataInputStream stream) {
        this.stream = stream;
    }

    /**
     * ストリームを通して {@link DNBTTagCompound} に変換して返します。
     * @return 変換された {@link DNBTTagCompound} 情報
     * @throws IOException 入力中に例外が発生した場合
     * ルートタグがDNBTTagCompoundの情報でない場合
     */
    public DNBTTagCompound read() throws IOException {

		// タグ情報を読み取ります。
		DNBTBase tag = readTag();

		// Compound以外の要素が渡された場合は例外を起こします。
		if (tag.getTypeId() != DNBTBase.TAG_COMPOUND) {
			throw new IOException("this root tag not compound. " + DNBTBase.TAG_TYPE_NAMES[tag.getTypeId()]);
		}

		// Compoundタグの要素結果を返します。
		return (DNBTTagCompound) tag;
	}
    
    private DNBTBase readTag() throws IOException {

		// タグIDを取得
		byte id = this.stream.readByte();

		if (id == DNBTBase.TAG_END) {

			// END要素であればそのまま返します。
			return new DNBTTagEnd();

		} else {

			// ルートタグの名前は省略する。
			this.stream.readUTF();

			// タグを読み取り
			return readTag(id);
		}

	}
    
    private DNBTBase readTag(int id) throws IOException {

		// タグタイプIDから型を取得します。
		switch (id) {

			case DNBTBase.TAG_END: {
				return new DNBTTagEnd();
			}

			case DNBTBase.TAG_BYTE: {
				return new DNBTTagByte(this.stream.readByte());
			}

			case DNBTBase.TAG_SHORT: {
				return new DNBTTagShort(this.stream.readShort());
			}

			case DNBTBase.TAG_INT: {
				return new DNBTTagInt(this.stream.readInt());
			}

			case DNBTBase.TAG_LONG: {
				return new DNBTTagLong(this.stream.readLong());
			}

			case DNBTBase.TAG_FLOAT: {
				return new DNBTTagFloat(this.stream.readFloat());
			}

			case DNBTBase.TAG_DOUBLE: {
				return new DNBTTagDouble(this.stream.readDouble());
			}

			case DNBTBase.TAG_BYTE_ARRAY: {
				return new DNBTTagByteArray(readByteArray());
			}

			case DNBTBase.TAG_STRING: {
				return new DNBTTagString(this.stream.readUTF());
			}

			case DNBTBase.TAG_LIST: {
				return readList();
			}

			case DNBTBase.TAG_COMPOUND: {
				return readCompound();
			}

			case DNBTBase.TAG_INT_ARRAY: {
				return new DNBTTagIntArray(readIntArray());
			}

			// 無効なタグタイプが指定された場合例外を発生させます。
			default: {
				throw new IOException("Invaled Tag Type. " + id);
			}

		}
	}
    
    private byte[] readByteArray() throws IOException {

		// 取得した長さから配列を作成
		byte[] array = new byte[this.stream.readInt()];

		// 作成した配列に要素を代入
		this.stream.readFully(array);

		return array;
	}
    
    private DNBTTagList readList() throws IOException {

		// 返し値用の要素を作成
		DNBTTagList list = new DNBTTagList();

		// リストの要素型を取得
		byte list_id = this.stream.readByte();
		int length = this.stream.readInt();

		// リストの要素を取得
		for (int i = 0; i < length; i++) {
			list.add(readTag(list_id));
		}

		return list;
	}
    
    private DNBTTagCompound readCompound() throws IOException {

		// 返し値用の要素を作成
		DNBTTagCompound compound = new DNBTTagCompound();

		// タグのIDとタグの識別子を取得します。
		int id;
		String name;

		// タグの型IDを取得を行いTAG_ENDまで要素をループします。
		while ((id = this.stream.readByte()) != DNBTBase.TAG_END) {

			// タグの型がTAG_ENDであれば処理を停止します。
			name = this.stream.readUTF();

			// タグIDから要素を取得
			compound.set(name, readTag(id));

		}

		return compound;
	}
    
    private int[] readIntArray() throws IOException {

		// 取得した長さから配列を作成
		int[] array = new int[this.stream.readInt()];

		// 作成した配列を代入
		for (int i = 0; i < array.length; i++) {
			array[i] = this.stream.readInt();
		}

		return array;
	}

    @Override
    public void close() throws IOException {
		this.stream.close();
	}

    /**
     * {@link GZIPInputStream} ストリームを通して {@link DNBTDataInputStream} を生成します。
     * @param stream GZIP形式に対応したストリーム情報
     * @return 引数を渡して生成された {@link DNBTDataInputStream}
     */
	public static DNBTDataInputStream createStreamGZIP(GZIPInputStream stream) {
	    return new DNBTDataInputStream(new DataInputStream(new BufferedInputStream(stream)));
    }

}
