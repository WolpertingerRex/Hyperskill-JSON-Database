package server.commands;

import client.Request;
import server.FileDatabase;
import server.Response;

public abstract class Command {
    protected FileDatabase database;

    public Command(FileDatabase database) {
        this.database = database;
    }

    public abstract Response execute(Request request);


}
