
// 指定した絶対座標に構造体を配置する関数
function SetSchematicToAbsolute(schematic_file, x, y, z)
{
    CleanRegister();
    SetRegisterString("session", "schematic");
    SetRegisterString("schematic", schematic_file);
    SetRegisterInt("x", x);
    SetRegisterInt("y", y);
    SetRegisterInt("z", z);
    PushRegister();
}

// 指定した相対座標に構造体を配置する関数
function SetSchematic(schematic_file, x, y, z)
{
    var x_ = x + DungeonScriptAPI.baseX;
    var y_ = y + DungeonScriptAPI.baseY;
    var z_ = z + DungeonScriptAPI.baseZ;
    SetSchematicToAbsolute(schematic_file, x_, y_, z_);
}
