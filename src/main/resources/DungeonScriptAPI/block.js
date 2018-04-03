
// 指定した絶対座標でブロックを配置する関数
function SetBlockToAbsolute(block_id, x, y, z)
{
    CleanRegister();
    SetRegisterString("session", "setblock");
    SetRegisterString("block_id", block_id);
    SetRegisterInt("x", x);
    SetRegisterInt("y", y);
    SetRegisterInt("z", z);
    PushRegister();
}

// 指定した相対座標にブロックを配置する関数
function SetBlock(block_id, x, y, z)
{
    var x_ = x + DungeonScriptAPI.baseX;
    var y_ = y + DungeonScriptAPI.baseY;
    var z_ = z + DungeonScriptAPI.baseZ;
    SetBlockToAbsolute(block_id, x_, y_, z_);
}

// 指定した絶対座標のブロックメタデータを変更する関数
function SetBlockDataToAbsolute(block_data, x, y, z)
{
    CleanRegister();
    SetRegisterString("session", "setblock");
    SetRegisterString("block_data", block_data);
    SetRegisterInt("x", x);
    SetRegisterInt("y", y);
    SetRegisterInt("z", z);
    PushRegister();
}

// 指定した相対座標のブロックメタデータを変更する関数
function SetBlockData(block_data, x, y, z)
{
    var x_ = x + DungeonScriptAPI.baseX;
    var y_ = y + DungeonScriptAPI.baseY;
    var z_ = z + DungeonScriptAPI.baseZ;
    SetBlockDataToAbsolute(block_data, x_, y_, z_);
}

// 指定した絶対座標のブロックとメタデータを変更する関数
function SetBlockAndDataToAbsolute(block_id, block_data, x, y, z)
{
    CleanRegister();
    SetRegisterString("session", "setblock");
    SetRegisterString("block_id", block_id);
    SetRegisterString("block_data", block_data);
    SetRegisterInt("x", x);
    SetRegisterInt("y", y);
    SetRegisterInt("z", z);
    PushRegister();
}

// 指定した相対座標のブロックとメタデータを変更する関数
function SetBlockAndData(block_id, block_data, x, y, z)
{
    var x_ = x + DungeonScriptAPI.baseX;
    var y_ = y + DungeonScriptAPI.baseY;
    var z_ = z + DungeonScriptAPI.baseZ;
    SetBlockAndDataToAbsolute(block_id, block_data, x_, y_, z_);
}
