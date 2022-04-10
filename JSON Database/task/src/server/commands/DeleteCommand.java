package server.commands;

import client.Request;
import com.google.gson.JsonElement;
import server.FileDatabase;
import server.Response;
import server.Status;


import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class DeleteCommand extends Command{
    private boolean result = false;

    public DeleteCommand(FileDatabase database) {
        super(database);
    }

    @Override
    public Response execute(Request request) {
        result = false;
        recurseAndDelete(database.getCells(), request.getKeyValues());
        if (!result) return Response.newBuilder().setStatus(Status.ERROR).setReason("No such key").build();

        database.save();
        return Response.newBuilder().setStatus(Status.OK).build();
    }

    private void recurseAndDelete(JsonElement root, Queue<String> keys) {
        Set<Map.Entry<String, JsonElement>> entries = root.getAsJsonObject().entrySet();

        for (Map.Entry<String, JsonElement> entry : entries) {
            System.out.println("Entry key " + entry.getKey() + " Entry value " + entry.getValue());

            String key = entry.getKey();
            if (key.equals(keys.peek())) {
                keys.poll();
                if (keys.isEmpty()) {
                    entries.remove(entry);
                    result = true;
                    break;

                } else {
                    if (entry.getValue().isJsonObject()) {
                        recurseAndDelete(entry.getValue(), keys);
                    }
                }
            }
        }
    }
}
