package net.tkarura.resourcedungeons.core.util.nbt.io;

import net.tkarura.resourcedungeons.core.util.nbt.*;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public class DNBTDataOutputStream implements Closeable {

    private DataOutputStream stream;

    public DNBTDataOutputStream(DataOutputStream stream) {
        this.stream = stream;
    }

    public void write(DNBTTagCompound compound) throws IOException {
        this.stream.writeByte(compound.getTypeId());
        this.stream.writeUTF("");
        writeBase(compound);
    }

    public void writeBase(DNBTBase base) throws IOException {

        switch (base.getTypeId()) {

            case DNBTBase.TAG_END: {

            }
            break;

            case DNBTBase.TAG_BYTE: {
                stream.writeByte(((DNBTTagByte) base).getValue());
            }
            break;

            case DNBTBase.TAG_SHORT: {
                stream.writeShort(((DNBTTagShort) base).getValue());
            }

            case DNBTBase.TAG_INT: {
                stream.writeInt(((DNBTTagInt) base).getValue());
            }
            break;

            case DNBTBase.TAG_LONG: {
                stream.writeLong(((DNBTTagLong) base).getValue());
            }
            break;

            case DNBTBase.TAG_FLOAT: {
                stream.writeFloat(((DNBTTagFloat) base).getValue());
            }
            break;

            case DNBTBase.TAG_DOUBLE: {
                stream.writeDouble(((DNBTTagDouble) base).getValue());
            }
            break;

            case DNBTBase.TAG_BYTE_ARRAY: {
                writeArray((DNBTTagIntArray) base);
            }
            break;

            case DNBTBase.TAG_STRING: {
                stream.writeUTF(((DNBTTagString) base).getValue());
            }
            break;

            case DNBTBase.TAG_LIST: {
                writeList((DNBTTagList) base);
            }
            break;

            case DNBTBase.TAG_COMPOUND: {
                writeCompound((DNBTTagCompound) base);
            }
            break;

            case DNBTBase.TAG_INT_ARRAY: {
                writeArray((DNBTTagByteArray) base);
            }
            break;

            case DNBTBase.TAG_LONG_ARRAY: {
                writeArray((DNBTTagLongArray) base);
            }
            break;

        }

    }

    public void writeArray(DNBTTagByteArray array) throws IOException {
        byte[] dates = array.getValue();
        stream.writeInt(dates.length);
        stream.write(dates);
    }

    public void writeArray(DNBTTagIntArray array) throws IOException {
        int[] dates = array.getValue();
        stream.writeInt(dates.length);
        for (int i = 0; i < dates.length; i++) {
            stream.writeInt(dates[i]);
        }
    }

    public void writeArray(DNBTTagLongArray array) throws IOException {
        long[] dates = array.getValue();
        stream.writeInt(dates.length);
        for (int i = 0; i < dates.length; i++) {
            stream.writeLong(dates[i]);
        }
    }

    public void writeCompound(DNBTTagCompound compound) throws IOException {
        for (Map.Entry<String, DNBTBase> entry : compound.getValue().entrySet()) {
            String name = entry.getKey();
            DNBTBase base = entry.getValue();
            write(name, base);
        }
        stream.writeByte(0);
    }

    public void write(String name, DNBTBase base) throws IOException {
        stream.writeByte(base.getTypeId());
        if (base.getTypeId() != DNBTBase.TAG_END) {
            stream.writeUTF(name);
            writeBase(base);
        }
    }

    public void writeList(DNBTTagList list) throws IOException {
        int type = 0;
        if (!list.getValue().isEmpty()) {
            type = list.getValue().get(0).getTypeId();
        }
        stream.writeByte(type);
        stream.writeInt(list.size());
        for (int i = 0; i < list.size(); i++) {
            writeBase(list.getValue().get(i));
        }
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }

    public static DNBTDataOutputStream createStreamGZIP(GZIPOutputStream stream) {
        return new DNBTDataOutputStream(new DataOutputStream(stream));
    }

}
