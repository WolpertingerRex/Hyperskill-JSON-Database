package server.commands;

import client.Request;
import com.google.gson.JsonElement;
import server.FileDatabase;
import server.Response;
import server.Status;

import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class SetCommand extends Command{
    private boolean result = false;

    public SetCommand(FileDatabase database) {
        super(database);
    }

    @Override
    public Response execute(Request request) {
            Queue<String> keys = request.getKeyValues();
            result = false;
            recurseAndSet(database.getCells(), keys, request.getValue());

            if(!result) database.getCells().getAsJsonObject().add(keys.poll(), request.getValue());

            database.save();

            return Response.newBuilder().setStatus(Status.OK).build();

    }

    private void recurseAndSet(JsonElement root, Queue<String> keys, JsonElement value) {
        Set<Map.Entry<String, JsonElement>> entries = root.getAsJsonObject().entrySet();

        for (Map.Entry<String, JsonElement> entry : entries) {
            System.out.println("Entry key " + entry.getKey() + " Entry value " + entry.getValue());

            String key = entry.getKey();
            if (key.equals(keys.peek())) {
                keys.poll();
                if (keys.isEmpty()) {

                    entry.setValue(value);
                    result = true;
                    break;

                } else {
                    if (entry.getValue().isJsonObject()) {
                        recurseAndSet(entry.getValue(), keys, value);
                    }
                }
            }
        }
    }
}
