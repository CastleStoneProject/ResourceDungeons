package net.tkarura.resourcedungeons.core.script;

import java.util.Deque;
import java.util.LinkedList;
import java.util.UUID;

import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.server.DungeonWorld;
import net.tkarura.resourcedungeons.core.session.Session;
import net.tkarura.resourcedungeons.core.session.SessionManager;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagCompound;

public final class GenerateHandle {

    private final UUID uuid = UUID.randomUUID();
    private IDungeon dungeon;
    private DungeonWorld world;
    private SessionManager sessions;
    private Deque<DNBTTagCompound> queue = new LinkedList<DNBTTagCompound>();
    private DNBTTagCompound register = new DNBTTagCompound();
    private int base_x = 0;
    private int base_y = 0;
    private int base_z = 0;

    public GenerateHandle(IDungeon dungeon, DungeonWorld world, SessionManager sessions) {
        this.dungeon = dungeon;
        this.world = world;
        this.sessions = sessions;
    }

    public void push() {
        this.queue.push(register);
        this.register = new DNBTTagCompound();
    }

    public void runSessions() {

        SessionManager sessions = this.sessions;

        DNBTTagCompound nbt;

        while ((nbt = this.queue.pollLast()) != null) {

            Session session = sessions.getSession(nbt.getString("session"));

            session.run(this, nbt);

        }

    }

    public void setBaseLoc(int x, int y, int z) {
        this.base_x = x;
        this.base_y = y;
        this.base_z = z;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public IDungeon getDungeon() {
        return this.dungeon;
    }

    public DungeonWorld getWorld() {
        return this.world;
    }

    public DNBTTagCompound getRegister() {
        return this.register;
    }

    public int getBaseX() {
        return this.base_x;
    }

    public int getBaseY() {
        return this.base_y;
    }

    public int getBaseZ() {
        return this.base_z;
    }

}
