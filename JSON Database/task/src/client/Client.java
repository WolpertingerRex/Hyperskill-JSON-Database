package client;

import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class Client {
    private final String address = "127.0.0.1";
    private final int port = 23456;

    public void send(Request request) {

        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
             DataInputStream input = new DataInputStream(socket.getInputStream())

        ) {
            System.out.println("Client started!");

            String jsonOutput = new Gson().toJson(request);
            output.writeUTF(jsonOutput);
            System.out.println("Sent: "+ jsonOutput);

            String jsonInput = input.readUTF();
            System.out.println("Received: " + jsonInput);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
