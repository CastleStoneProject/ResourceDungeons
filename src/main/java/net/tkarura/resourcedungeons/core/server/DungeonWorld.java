package net.tkarura.resourcedungeons.core.server;

/**
 * ワールド操作を行う中間クラスです。
 * 
 * @author the_karura
 */
public interface DungeonWorld {
    
    /**
     * Registry ID から指定した座標にブロックを配置します。
     * 座標は絶対座標指定になります。
     * @param registry_id 
     * @param x x座標
     * @param y y座標
     * @param z z座標
     */
    public void setBlockId(String registry_id, int x, int y, int z);
    
    /**
     * 指定した座標のブロックに対しデータ値を設定します。
     * 座標は絶対座標指定になります。
     * @param data データ値
     * @param x x座標
     * @param y y座標
     * @param z z座標
     */
    public void setBlockData(byte data, int x, int y, int z);
    
    /**
     * 指定した座標にあるブロックのRegistry IDを取得します。
     * @param x x座標
     * @param y y座標
     * @param z z座標
     * @return 指定した座標にあるブロックのRegistry ID
     */
    public String getBlockId(int x, int y, int z);
    
    /**
     * 指定した座標にあるブロックのデータ値を取得します。
     * @param x x座標
     * @param y y座標
     * @param z z座標
     * @return 指定した座標にあるブロックのデータ値
     */
    public byte getData(int x, int y, int z);
    
    /**
     * ワールドのSeed値を取得します。
     * @return seed値
     */
    public long getSeed();
    
}
