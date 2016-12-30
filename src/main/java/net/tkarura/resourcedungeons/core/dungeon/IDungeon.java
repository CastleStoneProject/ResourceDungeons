package net.tkarura.resourcedungeons.core.dungeon;

import java.util.List;

import net.tkarura.resourcedungeons.core.script.IDungeonScript;

public interface IDungeon {
    
    public String getId();
    
    public String getSupport();
    
    public String getName();
    
    public String getVersion();
    
    public String getDiscription();
    
    public List<DungeonUser> getAuthors();
    
    public List<DungeonUser> getContributors();
    
    public List<IDungeonScript> getScripts();
    
}
