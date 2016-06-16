package net.tkarura.resourcedungeons.core.util;

import java.util.Random;

import org.apache.commons.lang.Validate;

import net.tkarura.resourcedungeons.core.ResourceDungeons;
import net.tkarura.resourcedungeons.core.dungeon.Dungeon;
import net.tkarura.resourcedungeons.core.dungeon.GenerateOption;
import net.tkarura.resourcedungeons.core.exception.DungeonGenerateException;
import net.tkarura.resourcedungeons.core.script.JavaScriptExecutor;
import net.tkarura.resourcedungeons.core.script.ScriptExecutor;
import net.tkarura.resourcedungeons.core.server.DungeonLocation;

/**
 * ダンジョン生成に関わる機能を簡易的に利用出来るようにしたクラスです。
 * @author the_karura
 */
public final class GenerateUtils {
	
	private GenerateUtils() {}
	
	/**
	 * 現在の位置情報とダンジョン
	 * @param loc 位置情報
	 * @param random ランダム情報
	 */
	public static void generate(DungeonLocation loc, Random random) {
		
		try {
			
			int x = loc.getBlockX();
			int z = loc.getBlockZ();
			
			Dungeon[] dungeons = ResourceDungeons.getInstance().getDungeonManager().getDungeons();
			GenerateOption option = null;
			DungeonLocation base_loc;
			
			for (Dungeon dungeon : dungeons) {
				
				for (int y = 0; y < loc.getWorld().getMaxHeight(); y++) {
					
					base_loc = new DungeonLocation(loc.getWorld(), x, y, z);
					option = getGenerate(dungeon, base_loc);
					
					if (option != null) {
						generate(dungeon, base_loc, option);
					}
					
				}
				
			}
			
		} catch (DungeonGenerateException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 位置情報から生成可能なオプション情報を習得します。
	 * @param dungeon ダンジョン情報
	 * @param base_loc 位置情報
	 * @return 生成オプション いずれも条件に一致しない場合はnullを返します。
	 */
	public static GenerateOption getGenerate(Dungeon dungeon, DungeonLocation base_loc) {
		
		Validate.notNull(dungeon, "dungeon is null.");
		Validate.notNull(base_loc, "base_loc is null.");
		Validate.notNull(base_loc.getWorld(), "base_loc member world can not be null.");
		
		if (dungeon.getGenerates().isEmpty())
			return null;
		
		for (GenerateOption option : dungeon.getGenerates()) {
			
			if (isGenerate(option, base_loc)) {
				return option;
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * 指定した位置情報が生成オプションの条件と一致するかを確認します。
	 * @param option 検索情報
	 * @param base_loc 位置情報
	 * @return 条件に一致する場合はtrue
	 */
	public static boolean isGenerate(GenerateOption option, DungeonLocation base_loc) {
		
		Validate.notNull(option, "option is null.");
		Validate.notNull(base_loc, "base_loc is null.");
		Validate.notNull(base_loc.getWorld(), "base_loc in member world can not be null.");
		
		for (DungeonLocation loc : option.getLocations()) {
			
			loc.add(base_loc.getX(), base_loc.getY(), base_loc.getZ());
			
			if (!option.getBlocks().contains(base_loc.getWorld()
					.getBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())))
				return false;
			
			if (!option.getBlocks().contains(base_loc.getWorld()
					.getBiome(loc.getBlockX(), loc.getBlockZ())))
				return false;
			
		}
		
		return true;
		
	}
	
	/**
	 * ダンジョン情報と位置情報と生成に使用する生成オプションからダンジョンを生成します。
	 * @param dungeon ダンジョン情報
	 * @param loc 位置情報
	 * @param option オプション情報
	 * @throws DungeonGenerateException 生成中に起こる例外です。
	 */
	public static void generate(Dungeon dungeon, DungeonLocation loc, GenerateOption option)
			throws DungeonGenerateException {
		
		ScriptExecutor generate = new JavaScriptExecutor(dungeon);
		
		generate.execute(loc, option.getFunction());
		
	}
	
}
