package server.commands;

import client.Request;
import com.google.gson.JsonElement;
import server.FileDatabase;
import server.Response;
import server.Status;

import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GetCommand extends Command{
    private JsonElement result;

    public GetCommand(FileDatabase database) {
        super(database);
    }

    @Override
    public Response execute(Request request) {
        result = null;
        recurse(database.getCells(), request.getKeyValues());
        if (result == null) return Response.newBuilder().setStatus(Status.ERROR).setReason("No such key").build();

        return Response.newBuilder().setStatus(Status.OK)
                .setValue(result)
                .build();
    }

    private void recurse(JsonElement root, Queue<String> keys) {
        Set<Map.Entry<String, JsonElement>> entries = root.getAsJsonObject().entrySet();

        for (Map.Entry<String, JsonElement> entry : entries) {

            String key = entry.getKey();
            if (key.equals(keys.peek())) {
                keys.poll();
                if (keys.isEmpty()) {
                    result = entry.getValue();
                    break;

                } else {
                    if (entry.getValue().isJsonObject()) {
                        recurse(entry.getValue(), keys);
                    }
                }
            }
        }
    }
}
