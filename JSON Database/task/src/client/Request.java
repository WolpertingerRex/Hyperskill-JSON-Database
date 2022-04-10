package client;

import com.google.gson.JsonElement;

import java.util.LinkedList;
import java.util.Queue;

public class Request {
    private String type;
    private JsonElement key;
    private JsonElement value;

    public Request(String type, JsonElement key, JsonElement value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public JsonElement getKey() {
        return key;
    }

    public JsonElement getValue() {
        return value;
    }

    public Queue<String> getKeyValues() {
        Queue<String> result = new LinkedList<>();
        if (key.isJsonArray()) {
            for (JsonElement e : key.getAsJsonArray()) {
                if (e.isJsonPrimitive()) result.add(e.getAsString());
            }
        } else if (key.isJsonPrimitive()) {
            result.add(key.getAsString());
        }
        return result;
    }
}
