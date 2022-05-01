package com.kristley.alcocalc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileHandler {
    private final String filename;

    public FileHandler(String filename) {
        this.filename = filename;
    }

    public void saveNights(List<Night> nights) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer = new FileWriter(filename);

        gson.toJson(nights, new TypeToken<List<Night>>() {
        }.getType(), writer);
        writer.flush();
        writer.close();
    }

    public List<Night> getNightsFromSaveFile() {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<Night> nights = gson.fromJson(reader, new TypeToken<List<Night>>() {
        }.getType());
        return nights;
    }
}
