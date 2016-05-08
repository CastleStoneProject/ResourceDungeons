package net.tkarura.resourcedungeons.core.server;

/**
 * ワールド情報とワールド座標を決めるX,Y,Zを保管するクラスです。
 * @author the_karura
 */
public class DungeonLocation {
	
	private DungeonWorld world;
	private double x;
	private double y;
	private double z;
	
	/**
	 * ワールド情報を指定せずに生成
	 * @param x x座標
	 * @param y y座標
	 * @param z z座標
	 */
	public DungeonLocation(double x, double y, double z) {
		this(null, x, y, z);
	}
	
	/**
	 * ワールド情報と位置情報を指定して生成
	 * @param world ワールド情報
	 * @param x x座標
	 * @param y y座標
	 * @param z z座標
	 */
	public DungeonLocation(DungeonWorld world, double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * 現在の座標から指定した座標分加算します。
	 * @param x 加算するx座標
	 * @param y 加算するy座標
	 * @param z 加算するz座標
	 */
	public void add(double x, double y, double z) {
		addX(x);
		addY(y);
		addZ(z);
	}
	
	/**
	 * 現在のX座標から指定した数値分加算します。
	 * @param x 加算するx座標
	 */
	public void addX(double x) {
		this.x += x;
	}
	
	/**
	 * 現在のY座標から指定した数値分加算します。
	 * @param y 加算するx座標
	 */
	public void addY(double y) {
		this.y += y;
	}
	
	/**
	 * 現在のZ座標から指定した数値分加算します。
	 * @param z 加算するz座標
	 */
	public void addZ(double z) {
		this.z += z;
	}
	
	/**
	 * 座標を設定します。
	 * @param x 設定するx座標
	 * @param y 設定するy座標
	 * @param z 設定するz座標
	 */
	public void set(double x, double y, double z) {
		setX(x);
		setY(y);
		setZ(z);
	}
	
	/**
	 * X座標を設定します。
	 * @param x 設定するx座標
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Y座標を設定します。
	 * @param y 設定するy座標
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Z座標を設定します。
	 * @param z 設定するz座標
	 */
	public void setZ(double z) {
		this.z = z;
	}
	
	/**
	 * ワールド情報を返します。
	 * @return ワールド情報
	 */
	public DungeonWorld getWorld() {
		return this.world;
	}
	
	/**
	 * X座標を返します。
	 * @return X座標
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Y座標を返します。
	 * @return Y座標
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Z座標を返します。
	 * @return Z座標
	 */
	public double getZ() {
		return this.z;
	}
	
	/**
	 * X座標の整数部を返します。
	 * @return X座標整数部
	 */
	public int getBlockX() {
		return (int) this.x;
	}
	
	/**
	 * Y座標の整数部を返します。
	 * @return Y座標整数部
	 */
	public int getBlockY() {
		return (int) this.y;
	}
	
	/**
	 * Z座標の整数部を返します。
	 * @return Z座標整数部
	 */
	public int getBlockZ() {
		return (int) this.z;
	}
	
	/** Eclipseからの自動生成 */
	@Override
	public String toString() {
		return "" + x + ", " + y + ", " + z;
	}
	
}
