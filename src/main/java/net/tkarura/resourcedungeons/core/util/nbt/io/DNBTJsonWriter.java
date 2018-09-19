package net.tkarura.resourcedungeons.core.util.nbt.io;

import com.google.gson.stream.JsonWriter;
import net.tkarura.resourcedungeons.core.util.nbt.*;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class DNBTJsonWriter implements Closeable {

    private JsonWriter writer;

    public DNBTJsonWriter(Writer writer) {
        this.writer = new JsonWriter(writer);
        this.writer.setIndent(" ");
        this.writer.setSerializeNulls(false);
        this.writer.setHtmlSafe(true);
    }

    public void writer(DNBTTagCompound compound) throws IOException {
        this.writer.beginObject();
        for (Map.Entry<String, DNBTBase> entry : compound.getValue().entrySet()) {
            String tag = entry.getKey();
            DNBTBase value = entry.getValue();
            this.writer.name(tag);
            writer(value);
        }
        this.writer.endObject();
    }

    public void writer(DNBTTagList list) throws IOException {
        this.writer.beginArray();
        for (DNBTBase value : list.getValue()) {
            writer(value);
        }
        this.writer.endArray();
    }

    public void writer(DNBTTagByteArray array) throws IOException {
        this.writer.beginArray();
        for (int value : array.getValue()) {
            this.writer.value(value);
        }
        this.writer.endArray();
    }

    public void writer(DNBTTagIntArray array) throws IOException {
        this.writer.beginArray();
        for (int value : array.getValue()) {
            this.writer.value(value);
        }
        this.writer.endArray();
    }

    public void writer(DNBTTagLongArray array) throws IOException {
        this.writer.beginArray();
        for (long value : array.getValue()) {
            this.writer.value(value);
        }
        this.writer.endArray();
    }

    public void writer(DNBTBase base) throws IOException {

        switch (base.getTypeId()) {

            case DNBTBase.TAG_BYTE:
            case DNBTBase.TAG_SHORT:
            case DNBTBase.TAG_INT:
            case DNBTBase.TAG_LONG: {
                this.writer.value(((DNBTNumber) base).getValue());
            }
            break;

            case DNBTBase.TAG_FLOAT:
            case DNBTBase.TAG_DOUBLE: {
                this.writer.value(((DNBTNumber) base).getValueDouble());
            }
            break;

            case DNBTBase.TAG_BYTE_ARRAY: {
                writer((DNBTTagByteArray) base);
            }
            break;

            case DNBTBase.TAG_STRING: {
                this.writer.value(((DNBTTagString) base).getValue());
            }
            break;

            case DNBTBase.TAG_LIST: {
                writer((DNBTTagList) base);
            }
            break;

            case DNBTBase.TAG_COMPOUND: {
                writer((DNBTTagCompound) base);
            }
            break;

            case DNBTBase.TAG_INT_ARRAY: {
                writer((DNBTTagIntArray) base);
            }
            break;

            case DNBTBase.TAG_LONG_ARRAY: {
                writer((DNBTTagLongArray) base);
            }
            break;

        }

    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }

}
