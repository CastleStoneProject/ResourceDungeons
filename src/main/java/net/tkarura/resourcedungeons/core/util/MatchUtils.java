package net.tkarura.resourcedungeons.core.util;

/**
 * 正規表現に関する処理を簡略化させた汎用クラス
 */
public final class MatchUtils {

    private MatchUtils() {}

    public static boolean isBoolean(String str) {
        return str != null && str.matches("true|false");
    }

    public static boolean isInteger(String str) {
        return str != null && str.matches("[+-]?\\d+");
    }

    public static boolean isDecimal(String str) {
        return str != null && !isInteger(str) && str.matches("\\d+\\.?\\d*|\\.\\d+");
    }

}
