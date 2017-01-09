package net.tkarura.resourcedungeons.core.script.handle;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import net.tkarura.resourcedungeons.core.script.GenerateInstance;

public class HandleManager {
    
    private Map<String, Handle> group;
    
    public HandleManager() {
	this.group = new HashMap<String, Handle>();
    }
    
    public void init() {
	this.group.clear();
    }
    
    public void registerHandle(Handle handle) {
	HandleParameter param = getParameter(handle.getClass());
	if (param == null) {
	    throw new IllegalArgumentException("can not use anottation. " + HandleParameter.class.getName());
	}
	this.group.put(param.name(), handle);
    }
    
    private HandleParameter getParameter(Class<? extends Handle> handle_class) {
	for (Annotation ano : handle_class.getDeclaredAnnotations()) {
	    if (ano.annotationType().equals(HandleParameter.class)) {
		return (HandleParameter) ano;
	    }
	}
	return null;
    }
    
    public Handle getHandle(GenerateInstance instance, String handle_name) {
	return this.group.get(handle_name).copy(instance);
    }
    
}
