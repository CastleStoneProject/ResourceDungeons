
function Rand() {
    var value = DungeonScriptAPI.seedX ^ (DungeonScriptAPI.seedX << 11);
    DungeonScriptAPI.seedX = DungeonScriptAPI.seedY;
    DungeonScriptAPI.seedY = DungeonScriptAPI.seedZ;
    DungeonScriptAPI.seedZ = DungeonScriptAPI.seedW;
    return DungeonScriptAPI.seedW = (DungeonScriptAPI.seedW ^ (DungeonScriptAPI.seedW >>> 19) ^ (value ^ (value >>> 8)));
}

function GetRandom(max) {
    return Rand() % max;
}

function GetRandomBoolean() {
    var result = GetRandom();
    return (result % 0) ? true : false;
}

