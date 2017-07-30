package net.tkarura.resourcedungeons.core.server;

import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagCompound;

/**
 * ワールド操作を行う中間クラスです。
 * 
 * @author the_karura
 */
public interface DungeonWorld {

    /**
     * EntityIDを指定した座標にspawnさせます。
     *
     * @param entity_id EntityIDの文字列
     * @param x         x座標
     * @param y         y座標
     * @param z         z座標
     */
    public void spawnEntityFromId(String entity_id, int x, int y, int z);

    /**
     * EntityIDｓを指定した座標のspawnさせます。
     *
     * @param id EntityID
     * @param x  x座標
     * @param y  y座標
     * @param z  z座標
     */
    public void spawnEntityFromId(int id, int x, int y, int z);

    /**
     * Entityを表すNBT構造を解析し指定した座標へspawnさせます。
     *
     * @param nbt Entityを表すNBT構造
     * @param x   x座標
     * @param y   y座標
     * @param z   z座標
     */
    public void spawnEntityFromNBT(DNBTTagCompound nbt, int x, int y, int z);

    /**
     * Registry ID から指定した座標にブロックを配置します。
     * 座標は絶対座標指定になります。
     *
     * @param registry_id
     * @param x           x座標
     * @param y           y座標
     * @param z           z座標
     */
    public void setBlockId(String registry_id, int x, int y, int z);

    /**
     * BlockIDを指定した座標に配置します。
     *
     * @param block_id BlockID
     * @param x        x座標
     * @param y        y座標
     * @param z        z座標
     */
    public void setBlockId(int block_id, int x, int y, int z);

    /**
     * 指定した座標のブロックに対しデータ値を設定します。
     * 座標は絶対座標指定になります。
     *
     * @param data データ値
     * @param x    x座標
     * @param y    y座標
     * @param z    z座標
     */
    public void setBlockData(byte data, int x, int y, int z);

    /**
     * TileEntityを表すNBTを指定した座標に配置します。
     *
     * @param nbt NBT構造
     * @param x   x座標
     * @param y   y座標
     * @param z   z座標
     */
    public void setTileEntityFromNBT(DNBTTagCompound nbt, int x, int y, int z);

    /**
     * 指定した座標にあるブロックのRegistry IDを取得します。
     *
     * @param x x座標
     * @param y y座標
     * @param z z座標
     * @return 指定した座標にあるブロックのRegistry ID
     */
    public String getBlockId(int x, int y, int z);

    /**
     * 指定した座標にあるブロックのデータ値を取得します。
     *
     * @param x x座標
     * @param y y座標
     * @param z z座標
     * @return 指定した座標にあるブロックのデータ値
     */
    public byte getBlockData(int x, int y, int z);

    /**
     * ワールドのSeed値を取得します。
     *
     * @return seed値
     */
    public long getSeed();

}
