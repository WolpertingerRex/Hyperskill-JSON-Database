package server;

import com.google.gson.JsonElement;
import utils.FileUtils;


public class FileDatabase {
    private String filename;
    private JsonElement cells;

    public FileDatabase(String filename) {
        this.filename = filename;
        cells = FileUtils.read(filename);
    }

    public void save(){
        FileUtils.write(filename, cells);
    }

    public JsonElement getCells() {
        return cells;
    }
}
