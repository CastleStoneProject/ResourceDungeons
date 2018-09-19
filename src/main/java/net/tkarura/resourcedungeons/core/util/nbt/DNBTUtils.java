package net.tkarura.resourcedungeons.core.util.nbt;

import net.tkarura.resourcedungeons.core.util.MatchUtils;
import org.apache.commons.lang3.Validate;

public final class DNBTUtils {

    private DNBTUtils() {}

    public static DNBTBase convert(Object value) {

        Validate.notNull(value, "value is null.");

        if (value instanceof Boolean) {
            return new DNBTTagByte((byte) (((Boolean) value) ? 1 : 0));
        }

        if (value instanceof String) {
            return new DNBTTagString((String) value);
        }

        if (value instanceof Byte) {
            return new DNBTTagByte((Byte) value);
        }

        if (value instanceof Short) {
            return new DNBTTagShort((Short) value);
        }

        if (value instanceof Integer) {
            return new DNBTTagInt((Integer) value);
        }

        if (value instanceof Long) {
            return new DNBTTagLong((Long) value);
        }

        if (value instanceof Float) {
            return new DNBTTagFloat((Float) value);
        }

        if (value instanceof Double) {
            return new DNBTTagDouble((Double) value);
        }

        return null;

    }

    public static DNBTBase convert(String value) {

        Validate.notNull(value, "value is null.");

        if (MatchUtils.isBoolean(value)) {
            return new DNBTTagByte((byte) (Boolean.valueOf(value) ? 1 : 0));
        }

        if (MatchUtils.isInteger(value)) {
            return new DNBTTagInt(Integer.valueOf(value));
        }

        if (MatchUtils.isDecimal(value)) {
            return new DNBTTagDouble(Double.valueOf(value));
        }

        return new DNBTTagString(value);
    }

}
