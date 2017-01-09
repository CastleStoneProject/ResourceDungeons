package net.tkarura.resourcedungeons.core.script.handle;

import net.tkarura.resourcedungeons.core.script.GenerateInstance;

public abstract class Handle {
    
    protected GenerateInstance instance;
    
    public Handle(GenerateInstance instance) {
	this.instance = instance;
    }
    
    public abstract Handle copy(GenerateInstance instance);
    
}
