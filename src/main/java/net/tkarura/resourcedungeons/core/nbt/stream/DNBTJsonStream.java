package net.tkarura.resourcedungeons.core.nbt.stream;

import java.io.IOException;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;

import net.tkarura.resourcedungeons.core.nbt.DNBTBase;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagByte;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagByteArray;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagCompound;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagDouble;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagInt;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagIntArray;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagList;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagLong;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagShort;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagString;

/**
 * json形式の構造情報とDNBTTag構造の情報を相互変換を行う機能です。
 * @author the_karura
 */
public final class DNBTJsonStream {
	
	/**
	 * コンストラクタ生成防止
	 */
	private DNBTJsonStream() {}
	
	/**
	 * DNBTタグの情報をjson形式への書き込みします。
	 * @param writer jsonへの書き込みストリーム
	 * @param nbt タグ情報
	 * @throws IOException 書き込み中に発生した例外
	 */
	public static void writer(JsonWriter writer, DNBTTagCompound nbt) throws IOException {
		writer(writer, null, nbt);
	}
	
	/**
	 * DNBTタグの情報をjson形式への書き込みします。
	 * @param writer jsonへの書き込みストリーム
	 * @param name タグ鍵名
	 * @param nbt タグ情報
	 * @throws IOException 書き込み中に発生した例外
	 */
	public static void writer(JsonWriter writer, String name, DNBTTagCompound nbt) throws IOException {
		
		if (name != null) {
			writer.name(name);
		}
		
		writer.beginObject();
		
		for (Entry<String, DNBTBase> entry : nbt.getValue().entrySet()) {
			
			String key = entry.getKey();
			DNBTBase tag = entry.getValue();
			
			switch (tag.getTypeId()) {
			
			case DNBTBase.TAG_BYTE:
				
				writer.name(key).value((byte) tag.getValue());
				
				break;
			case DNBTBase.TAG_SHORT:
				
				writer.name(key).value((short) tag.getValue());
				
				break;
			case DNBTBase.TAG_INT:
				
				writer.name(key).value((int) tag.getValue());
				
				break;
			case DNBTBase.TAG_LONG:
				
				writer.name(key).value((long) tag.getValue());
				
				break;
			case DNBTBase.TAG_FLOAT:
				
				writer.name(key).value((float) tag.getValue());
				
				break;
			case DNBTBase.TAG_DOUBLE:
				
				writer.name(key).value((double) tag.getValue());
				
				break;
			case DNBTBase.TAG_BYTE_ARRAY:
				
				writer.name(key);
				writer.beginArray();
				
				byte[] byte_array = (byte[]) tag.getValue();
				for (int i = 0; i < byte_array.length; i++) {
					
					writer.value((byte) byte_array[i]);
					
				}
				
				writer.endArray();
				
				break;
			case DNBTBase.TAG_STRING:
				
				writer.name(key).value((String) tag.getValue());
				
				break;
			case DNBTBase.TAG_COMPOUND:
				
				writer(writer, key, (DNBTTagCompound) tag);
				
				break;
			case DNBTBase.TAG_LIST:
				
				writer(writer, key, (DNBTTagList) tag);
				
				break;
			case DNBTBase.TAG_INT_ARRAY:
				
				writer.name(key);
				writer.beginArray();
				
				int[] int_array = (int[]) tag.getValue();
				for (int i = 0; i < int_array.length; i++) {
					
					writer.value((int) int_array[i]);
					
				}
				
				writer.endArray();
				
				break;
			
			}
			
		}
		
		writer.endObject();
		
	}
	
	/**
	 * DNBTタグのリスト構造情報をjson形式への書き込みします。
	 * @param writer jsonへの書き込みストリーム
	 * @param nbt タグ情報
	 * @throws IOException 書き込み中に発生した例外
	 */
	public static void writer(JsonWriter writer, DNBTTagList nbt) throws IOException {
		writer(writer, null, nbt);
	}
	
	/**
	 * DNBTタグのリスト構造情報をjson形式への書き込みします。
	 * @param writer jsonへの書き込みストリーム
	 * @param name タグ鍵名
	 * @param nbt タグ情報
	 * @throws IOException 書き込み中に発生した例外
	 */
	public static void writer(JsonWriter writer, String name, DNBTTagList nbt) throws IOException {
		
		if (name != null) {
			writer.name(name);
		}
		
		writer.beginArray();
		
		for (DNBTBase tag : nbt.getValue()) {
			
			switch (tag.getTypeId()) {
			
			case DNBTBase.TAG_BYTE:
				
				writer.value((byte) tag.getValue());
				
				break;
			case DNBTBase.TAG_SHORT:
				
				writer.value((short) tag.getValue());
				
				break;
			case DNBTBase.TAG_INT:
				
				writer.value((int) tag.getValue());
				
				break;
			case DNBTBase.TAG_LONG:
				
				writer.value((long) tag.getValue());
				
				break;
			case DNBTBase.TAG_FLOAT:
				
				writer.value((float) tag.getValue());
				
				break;
			case DNBTBase.TAG_DOUBLE:
				
				writer.value((double) tag.getValue());
				
				break;
			case DNBTBase.TAG_BYTE_ARRAY:
				
				writer.beginArray();
				
				byte[] byte_array = (byte[]) tag.getValue();
				for (int i = 0; i < byte_array.length; i++) {
					
					writer.value((byte) byte_array[i]);
					
				}
				
				writer.endArray();
				
				break;
			case DNBTBase.TAG_STRING:
				
				writer.value((String) tag.getValue());
				
				break;
			case DNBTBase.TAG_COMPOUND:
				
				writer(writer, null, (DNBTTagCompound) tag);
				
				break;
			case DNBTBase.TAG_LIST:
				
				writer(writer, null, (DNBTTagList) tag);
				
				break;
			case DNBTBase.TAG_INT_ARRAY:
				
				writer.beginArray();
				
				int[] int_array = (int[]) tag.getValue();
				for (int i = 0; i < int_array.length; i++) {
					
					writer.value((int) int_array[i]);
					
				}
				
				writer.endArray();
				
				break;
			
			}
			
		}
		
		writer.endArray();
		
	}
	
	/**
	 * json構造の情報からDNBTタグ情報へ変換します。
	 * @param object 変換するjson情報
	 * @return 変換したDNBTタグ情報
	 */
	public static DNBTTagCompound load(JsonObject object) {
		
		DNBTTagCompound nbt = new DNBTTagCompound();
		
		for (Entry<String, JsonElement> entry : object.entrySet()) {
			
			String key = entry.getKey();
			JsonElement json = entry.getValue();
			
			if (json.isJsonPrimitive()) {
				nbt.set(key, load(json.getAsJsonPrimitive()));
			}
			
			if (json.isJsonObject()) {
				nbt.set(key, load(json.getAsJsonObject()));
			}
			
			if (json.isJsonArray()) {
				nbt.set(key, load(json.getAsJsonArray()));
			}
			
		}
		
		return nbt;
	}
	
	/**
	 * json構造の情報からDNBTタグの情報へ変換します。{@link #load(JsonObject)}とは異なりリスト構造用です。
	 * @param array リスト構造のjson情報
	 * @return 変換したDNBTタグ情報
	 */
	public static DNBTBase load(JsonArray array) {
		
		DNBTTagList list = new DNBTTagList();
		
		for (JsonElement json : array) {
			
			if (json.isJsonObject()) {
				list.add(load(json.getAsJsonObject()));
			}
			
			if (json.isJsonArray()) {
				list.add(load(json.getAsJsonArray()));
			}
			
			if (json.isJsonPrimitive()) {
				list.add(load(json.getAsJsonPrimitive()));
			}
			
		}
		
		// NBTTagByteArray と NBTTagIntArrayの判定
		boolean array_tag = true;
		
		for (DNBTBase nbt : list.getValue()) {
			
			if (nbt.getTypeId() != DNBTBase.TAG_BYTE) {
				array_tag = false;
			}
			
		}
		
		if (array_tag) {
			byte array_[] = new byte[list.size()];
			for (int i = 0; i < array_.length; i++) {
				array_[i] = ((DNBTTagByte) list.getValue().get(i)).getValue();
			}
			return new DNBTTagByteArray(array_);
		}
		
		array_tag = true;
		
		for (DNBTBase nbt : list.getValue()) {
			
			if (nbt.getTypeId() != DNBTBase.TAG_INT) {
				array_tag = false;
			}
		}
		
		if (array_tag) {
			int array_[] = new int[list.size()];
			for (int i = 0; i < array_.length; i++) {
				array_[i] = ((DNBTTagInt) list.getValue().get(i)).getValue();
			}
			return new DNBTTagIntArray(array_);
		}
		
		return list;
	}
	
	/**
	 * json構造の情報からDNBTタグの情報へ変換します。{@link #load(JsonPrimitive)}とは異なり一つの要素用です。
	 * @param primitive jsonの要素情報
	 * @return 変換したDNBTタグ情報
	 */
	public static DNBTBase load(JsonPrimitive primitive) {
		
		// 文字列から正規表現を使い型を識別
		String str = primitive.getAsString();
		
		try {
			
			// 数値型であれば
			if (primitive.isNumber()) {
				
				// 整数判定
				Double value = Double.valueOf(str);
				
				// 実数判定 (正規表現で少数を判定)
				if (str.matches("[-|+]?[0-9]*\\.+[0-9]*")) {
					
					// FIXME 文字列からfloatとdoubleどちらの型が
					// 適切であるかを比較する式が思いつかないのでコメントアウト
					
					// float
					// if (Float.MAX_VALUE >= value && Float.MIN_VALUE <= value) {
					//	return new DNBTTagFloat(Float.valueOf(str));
					// }
					// double
					// if (Double.MAX_VALUE >= value && Double.MIN_VALUE <= value) {
						return new DNBTTagDouble(Double.valueOf(str));
					// }
				}
				
				// byte
				if (Byte.MAX_VALUE >= value && Byte.MIN_VALUE <= value) {
					return new DNBTTagByte(value.byteValue());
				}
				
				// short
				if (Short.MAX_VALUE >= value && Short.MIN_VALUE <= value) {
					return new DNBTTagShort(value.shortValue());
				}
				
				// int
				if (Integer.MAX_VALUE >= value && Integer.MIN_VALUE <= value) {
					return new DNBTTagInt(value.intValue());
				}
				
				// long
				if (Long.MAX_VALUE >= value && Long.MIN_VALUE <= value) {
					return new DNBTTagLong(value.longValue());
				}
				
			}
			
			// Boolean
			if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")) {
				return new DNBTTagByte((byte) (primitive.getAsBoolean() ? 1 : 0));
			}
			
		} catch (NumberFormatException e) {
		}
		
		// String
		return new DNBTTagString(str);
		
	}
	
}
