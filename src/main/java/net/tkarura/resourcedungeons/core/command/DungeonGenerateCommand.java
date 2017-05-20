package net.tkarura.resourcedungeons.core.command;

import net.tkarura.resourcedungeons.core.dungeon.DungeonManager;
import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.exception.DungeonScriptException;
import net.tkarura.resourcedungeons.core.script.DungeonScriptManager;
import net.tkarura.resourcedungeons.core.script.GenerateHandle;
import net.tkarura.resourcedungeons.core.server.DungeonWorld;
import net.tkarura.resourcedungeons.core.session.SessionManager;

public class DungeonGenerateCommand extends DungeonCommand {
    
    private DungeonManager dungeon_manager;
    private SessionManager session_manager;
    
    public DungeonGenerateCommand() {
	super("generate");
	this.permission = "ResourceDungeons.Command.Generate";
	this.player_only = true;
    }
    
    public void setDungeonManager(DungeonManager dungeon_manager) {
	this.dungeon_manager = dungeon_manager;
    }
    
    public void setSessionManager(SessionManager session_manager) {
	this.session_manager = session_manager;
    }
    
    @Override
    public void execute(DungeonCommandSender sender) {
	
	// 引数チェック
	if (sender.getArgs().length <= 1) {
	    sender.sendMessage("引数が足りません。dungeon idを指定してください。");
	    return;
	}
	
	// 引数からダンジョン情報を取得
	IDungeon dungeon = dungeon_manager.getDungeon(sender.getArgs()[1]);
	
	// ダンジョン情報があるかを確認
	if (dungeon == null) {
	    sender.sendMessage("該当のdungeon idはありません。");
	    return;
	}
	
	// スクリプトに渡す構造体の引数を作成
	DungeonWorld world = sender.getWorld();
	int x = sender.getX();
	int y = sender.getY();
	int z = sender.getZ();
	
	// 生成ハンドルを作成します。
	GenerateHandle handle = new GenerateHandle(dungeon, world, session_manager);
	handle.setBaseLoc(x, y, z);
	
	// ダンジョン生成器の生成
	DungeonScriptManager generate = new DungeonScriptManager(handle);
	generate.setScriptClassLoader(null);
	
	try {
	    
	    // スクリプトの実行
	    generate.runScript();
	    generate.callMainFunction();
	    
	    // 生成完了の通知
	    sender.sendMessage("Dungeon Generate Complate.");
	
	} catch (DungeonScriptException e) {
	    
	    // 生成に失敗した時の通知
	    e.printStackTrace();
	    sender.sendMessage("Dungeon Generate Faild. reason: " + e.getLocalizedMessage());
	}
	
	
    }
    
}
