package net.tkarura.resourcedungeons.core.dungeon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

public class Dungeon implements IDungeon {
    
    protected final String id;
    protected File dir;
    protected String support = "1.0.0";
    protected String name = "N/A";
    protected String version = "N/A";
    protected String discription = "N/A";
    protected List<DungeonUser> authors = new ArrayList<DungeonUser>();
    protected List<DungeonUser> contributors = new ArrayList<DungeonUser>();
    
    public Dungeon(String id) {
	Validate.notNull(id, "id is null.");
	this.id = id;
    }
    
    public void setDirectory(File dir) {
	this.dir = dir;
    }
    
    public void setSupport(String support) {
	this.support = support;
    }
    
    public void setName(String name) {
	this.name = name != null ? name : "N/A";
    }
    
    public void setVersion(String version) {
	this.version = version != null ? version : "N/A";
    }
    
    public void setDiscription(String discription) {
	this.discription = discription;
    }
    
    public void addAuthor(DungeonUser user) {
	this.authors.add(user);
    }
    
    public void addContributor(DungeonUser user) {
	this.contributors.add(user);
    }
    
    @Override
    public File getDirectory() {
	return this.dir;
    }
    
    @Override
    public final String getId() {
	return this.id;
    }

    @Override
    public String getSupport() {
	return this.support;
    }
    
    @Override
    public String getName() {
	return this.name;
    }
    
    @Override
    public String getVersion() {
	return this.version;
    }

    @Override
    public String getDiscription() {
	return this.discription;
    }

    @Override
    public List<DungeonUser> getAuthors() {
	return new ArrayList<DungeonUser>(this.authors);
    }

    @Override
    public List<DungeonUser> getContributors() {
	return new ArrayList<DungeonUser>(this.contributors);
    }
    
}
