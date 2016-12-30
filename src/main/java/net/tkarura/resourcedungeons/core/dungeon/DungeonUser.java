package net.tkarura.resourcedungeons.core.dungeon;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DungeonUser {
    
    private UUID uuid;
    private Map<String, String> param = new HashMap<String, String>();
    
    public DungeonUser(UUID uuid) {
	this.uuid = uuid;
    }
    
    public UUID getUUID() {
	return this.uuid;
    }
    
    public void setParameter(String key, String value) {
	this.param.put(key, value);
    }
    
    public String getName() {
	String result = this.param.get("name");
	return result != null ? result : "N/A";
    }
    
    public String getContribution() {
	String result = this.param.get("contribution");
	return result != null ? result : "N/A";
    }

    /* (非 Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "DungeonUser [" + (uuid != null ? "uuid=" + uuid + ", " : "") + ("name=" + getName()) + (" contribution=" + getContribution())
		+ "]";
    }
    
}
