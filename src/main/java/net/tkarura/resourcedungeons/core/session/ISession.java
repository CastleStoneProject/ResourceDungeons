package net.tkarura.resourcedungeons.core.session;

import net.tkarura.resourcedungeons.core.exception.DungeonSessionException;
import net.tkarura.resourcedungeons.core.script.GenerateHandle;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagCompound;

public interface ISession {

    public void run(GenerateHandle handler, DNBTTagCompound data) throws DungeonSessionException;

}
