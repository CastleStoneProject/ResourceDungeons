package net.tkarura.resourcedungeons.core.loader.handle;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class LoadLogHandleList {
    
    private List<LoadLogHandle> list = new ArrayList<LoadLogHandle>();
    
    public void add(Level level, String message) {
	this.list.add(new LoadLogHandle(level, message));
    }
    
    public boolean isErrorLog() {
	for (LoadLogHandle handle : this.list) {
	    if (handle.getLevel() == Level.SEVERE) {
		return true;
	    }
	}
	return false;
    }
    
    public List<LoadLogHandle> getLogs() {
	return new ArrayList<LoadLogHandle>(this.list);
    }

}
