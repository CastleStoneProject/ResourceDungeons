package net.tkarura.resourcedungeons.core.dungeon;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

/**
 * ユーザ単体の情報を保管するクラスです。
 * ユーザ情報の識別の為のUUIDと属性の情報を保管します。
 * @author the_karura
 */
public class UserData {
	
	protected String uuid;
	protected Map<String, String> attributes = new HashMap<String, String>();
	
	/**
	 * UUIDを指定して生成します。
	 * @param uuid ユーザのUUI
	 */
	public UserData(String uuid) {
		Validate.notNull(uuid, "uuid is null.");
		this.uuid = uuid;
	}
	
	/**
	 * 鍵を指定して属性を設定します。
	 * @param key 鍵
	 * @param value 属性情報
	 */
	public void setAttribute(String key, String value) {
		this.attributes.put(key, value);
	}
	
	/**
	 * ユーザUUIDを指定します。
	 * @return ユーザUUID
	 */
	public String getUUID() {
		return this.uuid;
	}
	
	/**
	 * 鍵を指定して属性情報を取得します。
	 * @param key 鍵
	 * @return 属性情報
	 */
	public String getAttribute(String key) {
		return this.attributes.get(key);
	}
	
	@Override
	public String toString() {
		return "UserData [" + (uuid != null ? "uuid=" + uuid + ", " : "")
				+ (attributes != null ? "attributes=" + attributes : "") + "]";
	}
	
}
