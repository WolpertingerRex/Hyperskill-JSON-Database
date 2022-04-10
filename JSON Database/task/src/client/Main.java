package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import utils.FileUtils;

import java.io.File;

public class Main {
    @Parameter(names = "-t")
    private String type;
    @Parameter(names = "-k")
    private String key;
    @Parameter(names = "-v")
    private String value;
    @Parameter(names = "-in")
    private String filename;

    private final static String path = System.getProperty("user.dir") + File.separator +
            "src" + File.separator +
            "client" + File.separator +
            "data" + File.separator;

    public static void main(String[] args) {
        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);
        Client client = new Client();

        Request request = null;
        
        if (main.filename != null) {
            try {
                request = FileUtils.readRequest(path + main.filename);
                System.out.println(request.getKeyValues().toString());
            } catch (Exception e) {
            }
        } else {
            Gson gson = new Gson();
            request = new Request(main.type, gson.toJsonTree(main.key), gson.toJsonTree(main.value));

        }
        client.send(request);
    }
}
