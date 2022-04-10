package server;

import client.Request;
import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server {
    private final String address = "127.0.0.1";
    private final int port = 23456;
    private final String dbName = System.getProperty("user.dir") + File.separator +
                    "src" + File.separator +
                    "server" + File.separator +
                    "data" + File.separator + "db.json";

    private final DatabaseManager manager = new DatabaseManager(new FileDatabase(dbName));


    public void start() {

        try {
            ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
            System.out.println("Server started!");
            ExecutorService executor = Executors.newFixedThreadPool(10);

            while (true) {
                Socket socket = server.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                String jsonInput = input.readUTF();
                Request request = new Gson().fromJson(jsonInput, Request.class);

                manager.setRequest(request);
                Future<Response> future = executor.submit(manager);
                Response response = future.get();

                String jsonOutput = new Gson().toJson(response);
                output.writeUTF(jsonOutput);


                if(request.getType().equals("exit")) {
                    server.close();
                    System.exit(0);
                }
            }
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
