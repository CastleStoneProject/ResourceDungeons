package net.tkarura.resourcedungeons.core.dungeon.builder;

import net.tkarura.resourcedungeons.core.dungeon.Dungeon;
import net.tkarura.resourcedungeons.core.exception.DungeonLoadException;

/**
 * {@link Dungeon}を生成する際に使用する手続きクラスを表します。
 * 例えばファイルに格納された情報を通して{@link Dungeon}を生成したい場合など
 * <p>{@link Dungeon}はidの情報が生成時に問い合わされる為
 * {@link DungeonBuilder}を実装したクラスがidを参照する前にクラスを生成したい場合は
 * 仮置きで<code>system.build.識別子</code>をコンストラクタに渡してください。
 * <p>例: htmlの情報から生成する実装クラスの場合 new Dungeon("system.build.html");
 * @author the_karura
 */
public interface DungeonBuilder {
	
	/**
	 * 生成されたダンジョン情報を返します。
	 * @return
	 * @throws DungeonLoadException
	 */
	public Dungeon getResult() throws DungeonLoadException;
	
}
