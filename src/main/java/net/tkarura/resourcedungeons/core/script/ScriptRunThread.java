package net.tkarura.resourcedungeons.core.script;

import net.tkarura.resourcedungeons.core.exception.DungeonScriptException;

public class ScriptRunThread extends Thread {

	private DungeonScriptManager dsm;

	public ScriptRunThread(String thread_name, DungeonScriptManager dsm) {
		super(thread_name);
		this.dsm = dsm;
	}

	@Override
	public void run() {

		try {

			dsm.runScript();

			dsm.callMainFunction();

		} catch (DungeonScriptException e) {
			e.printStackTrace();
		}

	}

}
