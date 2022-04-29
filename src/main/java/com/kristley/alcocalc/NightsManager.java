package com.kristley.alcocalc;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NightsManager {
    private static NightsManager instance;
    private List<Night> nights = new ArrayList<>();
    private int index = -1;
    private Night tonight;
    private FileHandler fileHandler;

    public NightsManager(String filename) {
        instance = this;

        fileHandler = new FileHandler(filename);
        initializeNightsFromFile();
    }

    private static void initializeNightsFromFile() {
        List<Night> nights = instance.fileHandler.getNightsFromSaveFile();
        instance.nights = nights == null ? new ArrayList<>() : nights;
    }

    public static void addDrinkToCurrentNight(SerializableDrink drink) throws IOException {
        getNight().add(drink);

        instance.fileHandler.SaveNights(instance.nights);
    }


    public static Night getNight() {
        if(instance.index < 0){
            return null;
        }
        return instance.nights.get(instance.index);
    }


    public static void updateCurrentNightToTonight() {
        LocalDateTime now = LocalDateTime.now();
        if (getLatestNight()== null) {
            instance.nights.add(new Night(LocalDateTime.now()));
        }
        LocalDateTime startOfNight = DateTimeHelper.timeFromString(getLatestNight().getDate());
        Duration duration = Duration.between(startOfNight, now);

        //Probably should only add new Night if user is writing to file, but it's fine for demo
        if (!isSameNight(now, startOfNight, duration)) {
            instance.nights.add(new Night(LocalDateTime.now()));
        }
        instance.setNight(instance.nights.size() - 1);
        instance.tonight = getNight();
    }

    public static int getAmountOfNights() {
        return instance.nights.size();
    }


    private void setNight(int i) {
        instance.index = i;
    }

    private static Night getLatestNight() {
        if (instance.nights.size() == 0){
            return null;
        }
        return instance.nights.get(instance.nights.size() - 1);
    }

    private static boolean isSameNight(LocalDateTime now, LocalDateTime startOfNight, Duration duration) {
        return duration.toHours() < 25 && (startOfNight.getDayOfMonth() == now.getDayOfMonth() ||
                now.getDayOfMonth() - startOfNight.getDayOfMonth() == 1);
    }

    public static boolean hasFutureNight() {
        return instance.nights.size() - 1 > instance.index;
    }

    public static boolean hasPastNight() {
        return instance.index > 0;
    }

    public static void goToPastNight() {
        instance.setNight(--instance.index);
    }
    public static void goToFutureNight() {
        instance.setNight(++instance.index);
    }

    public static boolean currentNightIsToday(){
        return instance.tonight == getNight();
    }
}
