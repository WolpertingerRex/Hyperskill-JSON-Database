package utils;

import client.Request;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileUtils {
    static ReadWriteLock lock = new ReentrantReadWriteLock();
    static Lock readLock = lock.readLock();
    static Lock writeLock = lock.writeLock();

 public static JsonElement read(String filename) {
        readLock.lock();
        Gson gson = new Gson();
      JsonElement result = null;
        try (Reader reader = Files.newBufferedReader(Paths.get(filename))) {
            result = gson.fromJson(reader, new TypeToken<JsonElement>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
        }
        return result;
    }


    public static Request readRequest(String filename) {
        Gson gson = new Gson();
        try (Reader reader = Files.newBufferedReader(Paths.get(filename))) {
            return gson.fromJson(reader, new TypeToken<Request>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void write(String filename, JsonElement input) {
        writeLock.lock();
        Gson gson = new Gson();
        try (Writer writer = Files.newBufferedWriter(Paths.get(filename))) {
            gson.toJson(input, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }
}
