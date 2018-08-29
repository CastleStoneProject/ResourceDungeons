
// APIのメインクラスを宣言
var DungeonScriptAPI = {};

// ***** メンバ変数 **********************************************************************

DungeonScriptAPI.name = "DungeonScriptAPI"; // 名前
DungeonScriptAPI.version = "1.0.0";         // バージョン情報

DungeonScriptAPI.handle = 0; // スクリプト実行結果を格納するハンドル

DungeonScriptAPI.baseX = 0;
DungeonScriptAPI.baseY = 0;
DungeonScriptAPI.baseZ = 0;

DungeonScriptAPI.dungeonId = "";
DungeonScriptAPI.dungeonName = "";
DungeonScriptAPI.dungeonVersion = "";

DungeonScriptAPI.log = 0;

DungeonScriptAPI.seedW = 88675123;
DungeonScriptAPI.seedX = 123456789;
DungeonScriptAPI.seedY = 362436069;
DungeonScriptAPI.seedZ = 521288629;

// ***** メンバ関数 **********************************************************************

// スクリプトの実行に必要となる関数
// この関数は最初に呼び出される関数よりも前に呼び出され生成ハンドルが格納されます。
// その為この関数が無いと全てのスクリプトは動作しなくなります。
DungeonScriptAPI.SetHandle = function(_handle)
{

    // 生成用のハンドル情報を設定
    DungeonScriptAPI.handle = _handle;

    // ログ情報の取得
    DungeonScriptAPI.log = _handle.getLogger();

    var dungeon = _handle.getDungeon();

    DungeonScriptAPI.dungeonId = dungeon.getId();
    DungeonScriptAPI.dungeonName = dungeon.getName();
    DungeonScriptAPI.dungeonVersion = dungeon.getVersion();

    // 生成開始位置情報を設定
    DungeonScriptAPI.baseX = _handle.getBaseX();
    DungeonScriptAPI.baseY = _handle.getBaseY();
    DungeonScriptAPI.baseZ = _handle.getBaseZ();

    // シード値を設定
    DungeonScriptAPI.seedW = _handle.getWorld().getSeed();

    DungeonScriptAPI.CleanRegister();

}

// ***** レジスタ操作関連 **********************************************************************

// レジスタの情報をハンドルに登録する関数
DungeonScriptAPI.PushRegister = function()
{
    DungeonScriptAPI.handle.push();
}

// レジスタの情報を削除する関数
// これまで登録した情報を初期化します。
DungeonScriptAPI.CleanRegister = function()
{
    DungeonScriptAPI.handle.clean();
}

// レジスタの情報を取得する関数
DungeonScriptAPI.GetRegister = function()
{
    return DungeonScriptAPI.handle.getRegister();
}
