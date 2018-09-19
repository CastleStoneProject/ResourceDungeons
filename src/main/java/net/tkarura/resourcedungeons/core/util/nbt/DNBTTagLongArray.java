package net.tkarura.resourcedungeons.core.util.nbt;

public class DNBTTagLongArray extends DNBTBase {

    private long[] value;

    public DNBTTagLongArray() {
        this.value = new long[0];
    }

    public DNBTTagLongArray(long[] value) {
        this.value = value;
    }

    @Override
    public long[] getValue() {
        return value;
    }

    @Override
    public byte getTypeId() {
        return 12;
    }

    @Override
    public DNBTTagLongArray clone() {
        return new DNBTTagLongArray(this.value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < this.value.length; i++) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append(this.value[i]);
        }
        builder.append("]");
        return builder.toString();
    }

}
