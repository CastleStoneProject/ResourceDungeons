package net.tkarura.resourcedungeons.core.nbt.stream;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import net.tkarura.resourcedungeons.core.nbt.DNBTBase;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagByte;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagByteArray;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagCompound;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagDouble;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagEnd;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagFloat;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagInt;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagIntArray;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagList;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagLong;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagShort;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagString;

/**
 * バイナリデータからNBTタグへ変換を行うクラスです。
 * 
 * @author the_karura
 */
public class DNBTInputStream implements Closeable {
    
    // バイナリ読み取り用のストリーム
    protected DataInputStream stream;
    protected DNBTTagCompound compound;

    public DNBTInputStream(InputStream stream) throws IOException {
	this.stream = new DataInputStream(new DataInputStream(new BufferedInputStream(new GZIPInputStream(stream))));
	this.compound = read();
    }
    
    private DNBTTagCompound read() throws IOException {
	
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
	    name = id != DNBTBase.TAG_END ? this.stream.readUTF() : "";

	    // タグIDから要素を取得
	    compound.set(name, readTag(id));

	};
	
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
    
    public DNBTTagCompound getCompound() {
	return this.compound;
    }
    
    @Override
    public void close() throws IOException {
	this.stream.close();
    }
    
}
