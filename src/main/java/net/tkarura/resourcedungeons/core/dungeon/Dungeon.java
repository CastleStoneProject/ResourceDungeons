package net.tkarura.resourcedungeons.core.dungeon;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import net.tkarura.resourcedungeons.core.script.IDungeonScript;

public class Dungeon implements IDungeon {
    
    protected final String id;
    protected String support = "1.0.0";
    protected String name = "N/A";
    protected String version = "N/A";
    protected String discription = "N/A";
    protected List<DungeonUser> authors = new ArrayList<DungeonUser>();
    protected List<DungeonUser> contributors = new ArrayList<DungeonUser>();
    protected List<IDungeonScript> scripts = new ArrayList<IDungeonScript>();
    
    public Dungeon(String id) {
	Validate.notNull(id, "id is null.");
	this.id = id;
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
    
    public void addScript(IDungeonScript script) {
	this.scripts.add(script);
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
    
    @Override
    public List<IDungeonScript> getScripts() {
	return new ArrayList<IDungeonScript>(this.scripts);
    }

    /* (非 Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Dungeon [" + (id != null ? "id=" + id + ", " : "") + "support=" + support + ", "
		+ (name != null ? "name=" + name + ", " : "") + (version != null ? "version=" + version + ", " : "")
		+ (discription != null ? "discription=" + discription + ", " : "")
		+ (authors != null ? "authors=" + authors + ", " : "")
		+ (contributors != null ? "contributors=" + contributors + ", " : "")
		+ (scripts != null ? "scripts=" + scripts : "") + "]";
    }
    
}
