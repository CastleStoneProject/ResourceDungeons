package net.tkarura.resourcedungeons.core.command;

import net.tkarura.resourcedungeons.core.DungeonManager;
import net.tkarura.resourcedungeons.core.ResourceDungeons;
import net.tkarura.resourcedungeons.core.dungeon.Dungeon;
import net.tkarura.resourcedungeons.core.exception.CommandException;
import net.tkarura.resourcedungeons.core.exception.DungeonGenerateException;
import net.tkarura.resourcedungeons.core.generator.DungeonGenerate;
import net.tkarura.resourcedungeons.core.server.entity.DungeonPlayer;
import net.tkarura.resourcedungeons.core.server.sender.DungeonSender;

/**
 * ダンジョンを生成する処理を行うコマンドクラスです。
 * @author the_karura
 */
public final class GenerateCommand implements Command {
	
	private final String command = "generate";
	private final String permission = "ResourceDungeons.Command.Generate";
	private final boolean cliant = true;
	
	@Override
	public void execute(DungeonSender sender, String[] args) throws CommandException {
		
		if (args.length < 3)
			throw new CommandException("Command Args is Not Enough.");
		
		DungeonManager manager = ResourceDungeons.getInstance().getDungeonManager();
		Dungeon dungeon = manager.getDungeon(args[1]);
		
		if (dungeon == null)
			throw new CommandException("Dungeon ID is NotFound.");
		
		try {
			
			DungeonPlayer player = (DungeonPlayer) sender;
			DungeonGenerate generate = new DungeonGenerate(dungeon);
			
			generate.execute(player.getLocation(), args[2]);
			
			sender.sendMessage("Dungeon Generate Complate.");
			
		} catch (DungeonGenerateException e) {
			sender.sendMessage(e.getLocalizedMessage());
			e.printStackTrace();
		}
		
	}
	
	@Override
	public String getCommand() {
		return this.command;
	}
	
	@Override
	public String getPermission() {
		return this.permission;
	}
	
	@Override
	public boolean isPlayer() {
		return this.cliant;
	}
	
}
