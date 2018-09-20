package net.tkarura.resourcedungeons.core.util.nbt.io;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import net.tkarura.resourcedungeons.core.util.nbt.*;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

public class DNBTJsonReader implements Closeable {

    private JsonReader reader;

    public DNBTJsonReader(Reader reader) {
        this.reader = new JsonReader(reader);
    }

    public DNBTTagCompound read() {
        Gson gson = new Gson();
        return read((JsonObject) gson.fromJson(reader, JsonObject.class));
    }

    public DNBTBase read(JsonElement element) {

        if (element.isJsonObject()) {
            return read(element.getAsJsonObject());
        }

        if (element.isJsonArray()) {
            return read(element.getAsJsonArray());
        }

        if (element.isJsonPrimitive()) {
            return read(element.getAsJsonPrimitive());
        }

        throw new IllegalArgumentException("can not be converted. " + element.toString());

    }

    public DNBTTagCompound read(JsonObject object) {
        DNBTTagCompound compound = new DNBTTagCompound();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            compound.set(entry.getKey(), read(entry.getValue()));
        }
        return compound;
    }

    public DNBTTagList read(JsonArray array) {
        byte first = 0;
        DNBTTagList list = new DNBTTagList();
        for (int i = 0; i < array.size(); i++) {
            DNBTBase base = read(array.get(i));
            if (i == 0) {
                first = base.getTypeId();
            }
            if (i != 0 && base.getTypeId() != first) {
                throw new IllegalArgumentException("list value type is " + DNBTBase.TAG_TYPE_NAMES[first] +
                        ". invalid value type:" + DNBTBase.TAG_TYPE_NAMES[base.getTypeId()]);
            }
            list.add(base);
        }
        return list;
    }

    public DNBTBase read(JsonPrimitive primitive) {

        if (primitive.isString()) {
            return new DNBTTagString(primitive.getAsString());
        }

        if (primitive.isNumber()) {
            String value = primitive.getAsString();
            return DNBTUtils.convert(value);
        }

        throw new IllegalArgumentException("can not be converted. " + primitive.toString());
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

}
