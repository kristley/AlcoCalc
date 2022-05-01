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

        saveNights();
    }

    public static void saveNights() throws IOException {
        instance.fileHandler.saveNights(instance.nights);
    }


    public static Night getNight() {
        if(instance.index < 0){
            return null;
        }
        return instance.nights.get(instance.index);
    }


    public static void updateCurrentNightToTonight() {
        if (getLatestNight() == null || !latestNightIsCurrentNight()) {
            instance.nights.add(new Night(LocalDateTime.now()));
        }

        instance.setNight(instance.nights.size() - 1);
        instance.tonight = getNight();
    }
    private static boolean latestNightIsCurrentNight(){
        Night latestNight = getLatestNight();
        if (latestNight == null){
            return true;
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfNight = DateTimeHelper.timeFromString(latestNight.getDate());
        SerializableDrink latestDrink = latestNight.getLatestDrink();
        LocalDateTime lastDrinkTime = null;
        if (latestDrink != null){
            lastDrinkTime = DateTimeHelper.timeFromString(latestDrink.getTime());
        }
        return (latestDrink == null || isSameNight(now, startOfNight, lastDrinkTime)) && isSameNight(now, startOfNight);
    }

    private static boolean isSameNight(LocalDateTime now, LocalDateTime startOfNight, LocalDateTime lastDrink) {
        Duration duration = Duration.between(lastDrink, now);
        return isSameNight(now, startOfNight) && duration.toHours() < 6;
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


    private static boolean isSameNight(LocalDateTime now, LocalDateTime startOfNight) {
        Duration duration = Duration.between(startOfNight, now);
        return duration.toHours() <= 24 && (startOfNight.getDayOfMonth() == now.getDayOfMonth() ||
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
    public static boolean currentNightIsTonight(){
        return instance.tonight == getNight();
    }

    public static void deleteEmptyNight(){
        if (instance.tonight == null){
            Night latestNight = getLatestNight();
            if (latestNight == null){
                return;
            }
            LocalDateTime startOfNight = DateTimeHelper.timeFromString(latestNight.getDate());
            if (isSameNight(LocalDateTime.now(), startOfNight)) {
                instance.tonight = latestNight;
            }
            else {
                return;
            }
        }
        if (instance.tonight.getSerializableDrinks().size() == 0){
            instance.nights.remove(instance.nights.size() - 1);
            try {
                saveNights();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
