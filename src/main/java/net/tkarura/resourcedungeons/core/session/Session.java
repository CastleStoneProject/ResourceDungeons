package net.tkarura.resourcedungeons.core.session;

import net.tkarura.resourcedungeons.core.script.GenerateHandle;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagCompound;

public interface Session {

    public void run(GenerateHandle handler, DNBTTagCompound data);

}
