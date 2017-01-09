package net.tkarura.resourcedungeons.core.script.handle;

import net.tkarura.resourcedungeons.core.script.GenerateInstance;

@HandleParameter(group = "resourcedungeon", name = "generate")
public class GenerateHandle extends Handle {
    
    public GenerateHandle(GenerateInstance instance) {
	super(instance);
    }
    
    /**
     * 生成を行う起点X座標を返します。
     * ワールドに対し絶対座標値で返します。
     * @return 起点X座標
     */
    public int getGenX() {
	return instance.getParameters().getInt("gen_x");
    }

    /**
     * 生成を行う起点Y座標を返します。
     * ワールドに対し絶対座標値で返します。
     * @return 起点Y座標
     */
    public int getGenY() {
	return instance.getParameters().getInt("gen_y");
    }

    /**
     * 生成を行う起点Z座標を返します。
     * ワールドに対し絶対座標値で返します。
     * @return 起点Z座標
     */
    public int getGenZ() {
	return instance.getParameters().getInt("gen_z");
    }
    
    @Override
    public Handle copy(GenerateInstance instance) {
	return new GenerateHandle(instance);
    }
    
}
