package net.tkarura.resourcedungeons.core.dungeon;

import java.io.File;
import java.util.List;

public interface IDungeon {
    
    public File getDirectory();
    
    public String getId();
    
    public String getSupport();
    
    public String getName();
    
    public String getVersion();
    
    public String getDiscription();
    
    public List<DungeonUser> getAuthors();
    
    public List<DungeonUser> getContributors();
    
}
