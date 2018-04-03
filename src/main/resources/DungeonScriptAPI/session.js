
function PushRegister()
{
    DungeonScriptAPI.PushRegister();
}

function CleanRegister()
{
    DungeonScriptAPI.CleanRegister();
}

function SetRegisterByte(key, value)
{
    DungeonScriptAPI.GetRegister().setByte(key, value);
}

function SetRegisterShort(key, value)
{
    DungeonScriptAPI.GetRegister().setShort(key, value);
}

function SetRegisterInt(key, value)
{
    DungeonScriptAPI.GetRegister().setInt(key, value);
}

function SetRegisterLong(key, value)
{
    DungeonScriptAPI.GetRegister().setLong(key, value);
}

function SetRegisterFloat(key, value)
{
    DungeonScriptAPI.GetRegister().setFloat(key, value);
}

function SetRegisterDouble(key, value)
{
    DungeonScriptAPI.GetRegister().setFloat(key, value);
}

function SetRegisterString(key, value)
{
    DungeonScriptAPI.GetRegister().setString(key, value);
}
