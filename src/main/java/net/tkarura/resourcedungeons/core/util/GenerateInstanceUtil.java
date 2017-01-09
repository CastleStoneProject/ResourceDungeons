package net.tkarura.resourcedungeons.core.util;

import net.tkarura.resourcedungeons.core.nbt.DNBTTagCompound;
import net.tkarura.resourcedungeons.core.nbt.DNBTTagInt;

public final class GenerateInstanceUtil {
    
    private GenerateInstanceUtil() {
    }
    
    public static void setGeneratePos(DNBTTagCompound nbt, int x, int y, int z) {
	nbt.set("gen_x", new DNBTTagInt(x));
	nbt.set("gen_y", new DNBTTagInt(y));
	nbt.set("gen_z", new DNBTTagInt(z));
    }
    
}
