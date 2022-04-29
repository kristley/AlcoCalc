package com.kristley.alcocalc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NightsManager {
    private static NightsManager instance;
    private static String filename;
    private Night currentNight;
    private List<Night> nights = new ArrayList<>();
    private int index;
    private Night tonight;

    public NightsManager(String filename) {
        instance = this;
        instance.filename = filename;
        initializeNightsFromFile();
    }

    private static void initializeNightsFromFile() {
        Gson gson = new Gson();
        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<Night> nights = gson.fromJson(reader, new TypeToken<List<Night>>() {
        }.getType());
        instance.nights = nights == null ? new ArrayList<>() : nights;
    }

    public static void addDrinkToNight(SerializableDrink drink) throws IOException {
        instance.currentNight.add(drink);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(instance.nights.size());
        for (SerializableDrink sd :
                getLatestNight().getSerializableDrinks()) {
            System.out.println(sd.getBeverageName());
        }
        FileWriter writer = new FileWriter(instance.filename);

        gson.toJson(instance.nights, new TypeToken<List<Night>>() {
        }.getType(), writer);
        writer.flush();
        writer.close();
    }

    public static Night getNight() {
        return instance.currentNight;
    }


    public static void updateCurrentNightToTonight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfNight = DateTimeHelper.timeFromString(getLatestNight().getDate());
        Duration duration = Duration.between(startOfNight, now);

        //Probably should only add new Night if user is writing to file, but it's fine for demo
        if (!isSameNight(now, startOfNight, duration)) {
            instance.nights.add(new Night(LocalDateTime.now()));
        }
        instance.setNight(instance.nights.size() - 1);
    }


    private void setNight(int i) {
        instance.index = i;
        instance.currentNight = instance.nights.get(i);
    }

    private static Night getLatestNight() {
        return instance.nights.get(instance.nights.size() - 1);
    }

    private static boolean isSameNight(LocalDateTime now, LocalDateTime startOfNight, Duration duration) {
        return duration.toHours() < 25 && (startOfNight.getDayOfMonth() == now.getDayOfMonth() ||
                now.getDayOfMonth() - startOfNight.getDayOfMonth() == 1);
    }

    public static boolean hasFutureNight() {
        try{
            instance.nights.get(instance.index + 1);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean hasPastNight() {
        try{
            instance.nights.get(instance.index - 1);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public static void goToPastNight() {
        instance.setNight(--instance.index);
    }
    public static void goToFutureNight() {
        instance.setNight(++instance.index);
    }

    public static boolean currentNightIsToday(){
        return instance.tonight == instance.currentNight;
    }
}
