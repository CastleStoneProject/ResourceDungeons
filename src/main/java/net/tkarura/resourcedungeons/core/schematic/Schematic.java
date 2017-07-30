package net.tkarura.resourcedungeons.core.schematic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.Validate;

import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagCompound;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagList;
import net.tkarura.resourcedungeons.core.util.nbt.stream.DNBTDataInputStream;

/**
 * Schematicフォーマットの構造を解析するクラスです。
 * schematicフォーマットについては
 * <a href="http://minecraft.gamepedia.com/Schematic_file_format">http://minecraft.gamepedia.com/Schematic_file_format</a>
 * を参照してください。
 * 
 * @author the_karura
 */
public class Schematic {
    
    protected short width;
    protected short height;
    protected short length;
    protected String materials;
    protected int[][][] blocks;
    protected byte[][][] data;
    protected List<DNBTTagCompound> entities;
    protected List<DNBTTagCompound> tile_entities;
    
    /**
     * NBTTagCompoundを指定して解析をします。
     * @param format
     * @throws IllegalArgumentException 解析中に例外が起きた場合
     */
    public Schematic(DNBTTagCompound format) {
	
	// タグの要素解析
	validate(format);
	
	// タグの要素取得
	this.width = format.getShort("Width");
	this.height = format.getShort("Height");
	this.length = format.getShort("Length");
	this.materials = format.getString("Materials");
	this.blocks = convertByteArrayToInt(format.getByteArray("Blocks"));
	this.data = convertByteArray(format.getByteArray("Data"));
	this.entities = convertList(format.getList("Entities"));
	this.tile_entities = convertList(format.getList("TileEntities"));
    }
    
    private void validate(DNBTTagCompound format) {
	Validate.notNull(format.getShort("Width"),       "format tag \"Width\" is null.");
	Validate.notNull(format.getShort("Height"),      "format tag \"Height\" is null.");
	Validate.notNull(format.getShort("Length"),      "format tag \"Length\" is null.");
	Validate.notNull(format.getString("Materials"),  "format tag \"Materials\" is null.");
	Validate.notNull(format.getByteArray("Blocks"),  "format tag \"Blocks\" is null.");
	Validate.notNull(format.getByteArray("Data"),    "format tag \"Data\" is null.");
	Validate.notNull(format.getList("Entities"),     "format tag \"Entities\" is null.");
	Validate.notNull(format.getList("TileEntities"), "format tag \"TileEntities\" is null.");
    }
    
    private int[][][] convertByteArrayToInt(byte[] array) {
	int[][][] result = new int[width][height][length];
	for (int x = 0; x < this.width; x++) {
	    for (int y = 0; y < this.height; y++) {
		for (int z = 0; z < this.length; z++) {
		    result[x][y][z] = array[(y * length + z) * width + x] & 0xFF;
		}
	    }
	}
	return result;
    }
    
    private byte[][][] convertByteArray(byte[] array) {
	byte[][][] result = new byte[width][height][length];
	for (int x = 0; x < this.width; x++) {
	    for (int y = 0; y < this.height; y++) {
		for (int z = 0; z < this.length; z++) {
		    result[x][y][z] = array[(y * length + z) * width + x];
		}
	    }
	}
	return result;
    }
    
    private List<DNBTTagCompound> convertList(DNBTTagList list) {
	List<DNBTTagCompound> result = new ArrayList<DNBTTagCompound>();
	for (int i = 0; i < list.size(); i++) {
	    result.add(list.getNBTTagCompound(i));
	}
	return result;
    }
    
    /**
     * x軸の長さを返します。
     * @return x軸の長さ
     */
    public int getWidth() {
	return this.width;
    }
    
    /**
     * y軸の長さを返します。
     * @return y軸の長さ
     */
    public int getHeight() {
	return this.height;
    }
    
    /**
     * z軸の長さを返します。
     * @return z軸の長さ
     */
    public int getLength() {
	return this.length;
    }
    
    /**
     * 指定した座標に格納されたブロック情報を取得します。
     * {@link #getWidth()}、{@link #getHeight()}、{@link #getLength()}
     * が持つ大きさを超えた座標を指定した場合<code>-1</code>を返します。
     * @param x x座標
     * @param y y座標
     * @param z z座標
     * @return 座標に格納されたブロックID または <code>-1</code>
     */
    public int getBlock(int x, int y, int z) {
	try {
	    return this.blocks[x][y][z];
	} catch (IndexOutOfBoundsException e) {
	    return -1;
	}
    }
    
    /**
     * 指定した座標に格納されたブロックデータ情報を取得します。
     * {@link #getWidth()}、{@link #getHeight()}、{@link #getLength()}
     * が持つ大きさを超えた座標を指定した場合<code>-1</code>を返します。
     * @param x x座標
     * @param y y座標
     * @param z z座標
     * @return 座標に格納されたブロックデータID または <code>-1</code>
     */
    public byte getData(int x, int y, int z) {
	try {
	    return this.data[x][y][z];
	} catch (IndexOutOfBoundsException e) {
	    return -1;
	}
    }
    
    /**
     * 格納されたEntity情報のサイズを返します。
     * @return 格納されたEntity情報のサイズ
     */
    public int getEntitySize() {
	return this.entities.size();
    }
    
    /**
     * 指定されたインデックスから格納されたEntity情報を取得します。
     * {@link #getEntitySize()}を超える値を指定した場合<code>null</code>を返します。
     * @param index 要素へアクセスする為のインデックス
     * @return Entityを表すNBT構造
     */
    public DNBTTagCompound getEntity(int index) {
	try {
	    return this.entities.get(index);
	} catch (IndexOutOfBoundsException e) {
	    return null;
	}
    }
    
    /**
     * 格納されたTileEntity情報のサイズを返します。
     * @return 格納されたTileEntity情報のサイズ
     */
    public int getTileEntitySize() {
	return this.tile_entities.size();
    }
    
    /**
     * 指定されたインデックスから格納されたTileEntity情報を取得します。
     * {@link #getTileEntitySize()}を超える値を指定した場合<code>null</code>を返します。
     * @param index 要素へアクセスする為のインデックス
     * @return TileEntityを表すNBT構造
     */
    public DNBTTagCompound getTileEntity(int index) {
	try {
	    return this.tile_entities.get(index);
	} catch (IndexOutOfBoundsException e) {
	    return null;
	}
    }

	/**
	 * ファイル情報からSchematic情報を生成します。
	 * @param file schematic形式のバイナリファイル
	 * @return Schematic情報
	 * @throws IOException 不正なファイルの場合
	 */
	public static Schematic loadSchematic(File file) throws IOException {
	
	Schematic schematic;
	DNBTTagCompound nbt;
	
	DNBTDataInputStream datainput = new DNBTDataInputStream(new FileInputStream(file));
	nbt = datainput.getCompound();
	datainput.close();
	
	schematic = new Schematic(nbt);
	
	return schematic;
    }

    /* (非 Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Schematic [width=" + width + ", height=" + height + ", length=" + length + ", "
		+ (materials != null ? "materials=" + materials + ", " : "")
		+ (blocks != null ? "blocks=" + Arrays.toString(blocks) + ", " : "")
		+ (data != null ? "data=" + Arrays.toString(data) + ", " : "")
		+ (entities != null ? "entities=" + entities + ", " : "")
		+ (tile_entities != null ? "tile_entities=" + tile_entities : "") + "]";
    }
    
}
