package net.tkarura.resourcedungeons.core.dungeon;

import java.io.File;
import java.util.List;

/**
 * ダンジョンに関わる情報を受け渡す為のインタフェースです。
 * 
 * @author the_karura
 */
public interface IDungeon {

    /**
     * ダンジョンの構造を保持しているディレクトリ情報を返します。
     *
     * @return ダンジョン構造を構成するファイルが格納されたディレクトリ
     */
    public File getDirectory();

    /**
     * ダンジョンIDを返します。
     *
     * @return ダンジョンのユニークID
     */
    public String getId();

    /**
     * ダンジョンのサポートバージョンを返します。
     *
     * @return サポートバージョン
     */
    public String getSupport();

    /**
     * ダンジョンの名前を返します。
     *
     * @return ダンジョンの名前
     */
    public String getName();

    /**
     * ダンジョンのバージョンを返します。
     *
     * @return バージョン
     */
    public String getVersion();

    /**
     * ダンジョンの説明を返します。
     *
     * @return 説明
     */
    public String getDescription();

    /**
     * ダンジョンの作成に関わった製作者のユーザ情報一覧を返します。
     *
     * @return ユーザ情報一覧
     */
    public List<DungeonUser> getAuthors();

    /**
     * ダンジョンの作成に関わった貢献者のユーザ情報一覧を返します。
     *
     * @return ユーザ情報一覧
     */
    public List<DungeonUser> getContributors();

    public List<DungeonGenerateOption> getGenerateOptions();

    public List<IDungeonScript> getScripts();

}
