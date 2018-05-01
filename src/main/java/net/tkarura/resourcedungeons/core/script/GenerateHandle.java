package net.tkarura.resourcedungeons.core.script;

import java.util.Deque;
import java.util.LinkedList;
import java.util.UUID;

import net.tkarura.resourcedungeons.core.dungeon.IDungeon;
import net.tkarura.resourcedungeons.core.exception.DungeonSessionException;
import net.tkarura.resourcedungeons.core.server.IDungeonWorld;
import net.tkarura.resourcedungeons.core.session.ISession;
import net.tkarura.resourcedungeons.core.session.SessionManager;
import net.tkarura.resourcedungeons.core.util.nbt.DNBTTagCompound;

/**
 * スクリプト実行結果を格納する為のクラスです。
 */
public final class GenerateHandle {

    protected IDungeon dungeon;                                   // ダンジョン情報
    protected IDungeonWorld world;                                // ワールド情報
    protected int base_x = 0;                                     // 生成基点x
    protected int base_y = 0;                                     // 生成基点y
    protected int base_z = 0;                                     // 生成基点z

    protected SessionManager sessions;                            // 命令一覧情報

    private Deque<DNBTTagCompound> queue = new LinkedList<>();  // 実際に実行させる為の情報
    private DNBTTagCompound register = new DNBTTagCompound();   // スクリプトで取り扱う情報

    /**
     * レジスタの情報をキューに追加します。
     */
    public void push() {
        this.queue.push(register);
    }

    public void clean() {
        this.register = new DNBTTagCompound();
    }

    /**
     * スクリプトから得た情報を元に実際に実行を行う情報です。
     * Scriptからの実行がされないようにprotectedで防止してます。
     */
    protected void runSessions() {

        SessionManager sessions = this.sessions;
        DNBTTagCompound nbt;

        if (sessions == null) {
            throw new NullPointerException("sessions is null.");
        }

        // 格納された情報を実行します。
        while ((nbt = this.queue.pollLast()) != null) {

            // セッション情報を取得
            ISession session = sessions.getSession(nbt.getString("session"));

            // 有効なセッション情報な場合は処理
            if (session != null) {

                try {

                    session.run(this, nbt);

                } catch (DungeonSessionException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    /**
     * 生成基点を変更します。
     * @param x 変更する基点x
     * @param y 変更する基点y
     * @param z 変更する基点z
     */
    public void setBaseLoc(int x, int y, int z) {
        this.base_x = x;
        this.base_y = y;
        this.base_z = z;
    }

    /**
     * ダンジョン情報を取得します。
     * @return ダンジョン情報
     */
    public IDungeon getDungeon() {
        return this.dungeon;
    }

    /**
     * ワールド情報を取得します。
     * @return ワールド情報
     */
    public IDungeonWorld getWorld() {
        return this.world;
    }

    /**
     * レジスタ情報を取得します。
     * @return レジスタ情報
     */
    public DNBTTagCompound getRegister() {
        return this.register;
    }

    /**
     * 生成基点xを取得します。
     * @return 生成基点x
     */
    public int getBaseX() {
        return this.base_x;
    }

    /**
     * 生成基点yを取得します。
     * @return 生成基点y
     */
    public int getBaseY() {
        return this.base_y;
    }

    /**
     * 生成基点zを取得します。
     * @return 生成基点z
     */
    public int getBaseZ() {
        return this.base_z;
    }

}
