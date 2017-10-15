package net.tkarura.resourcedungeons.core.command;

import java.util.UUID;

import net.tkarura.resourcedungeons.core.server.IDungeonWorld;

public interface DungeonCommandSender {

    public void sendMessage(String message);

    public UUID getUUID();

    public String getName();

    public String[] getArgs();

    public boolean hasPermission(String permission);

    public IDungeonWorld getWorld();

    public int getX();

    public int getY();

    public int getZ();

}
