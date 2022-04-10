package server;


import client.Request;
import client.Type;
import server.commands.DeleteCommand;
import server.commands.GetCommand;
import server.commands.SetCommand;

import java.util.concurrent.Callable;


public class DatabaseManager implements Callable<Response> {
    private final FileDatabase database;
    private Request request;

    public DatabaseManager(FileDatabase database) {
        this.database = database;
    }

    public Response process() {

        Type type = Type.valueOf(request.getType().toUpperCase());
        switch (type) {
            case EXIT:
                return Response.newBuilder().setStatus(Status.OK).build();
            case GET:
              return new GetCommand(database).execute(request);
            case SET:
                return new SetCommand(database).execute(request);
            case DELETE:
                return new DeleteCommand(database).execute(request);
            default:
                return Response.newBuilder().setStatus(Status.ERROR).build();
        }
    }


    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public Response call() throws Exception {
        return process();
    }
}
