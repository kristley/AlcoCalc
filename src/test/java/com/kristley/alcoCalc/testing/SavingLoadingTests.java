package com.kristley.alcoCalc.testing;

import com.kristley.alcocalc.Beverage;
import com.kristley.alcocalc.DateTimeHelper;
import com.kristley.alcocalc.NightsManager;
import com.kristley.alcocalc.SerializableDrink;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class SavingLoadingTests {
    private static Beverage beverage;

    @BeforeAll
    static void beforeAll() {
        beverage = new Beverage("Morgan", 70,"cl", 40, "#447ea9");
    }

    @Test
    void savingNightsToFile() throws IOException {
        File file = getFile("testNights.json");
        new NightsManager(file.getAbsolutePath());
        NightsManager.updateCurrentNightToTonight();

        NightsManager.addDrinkToNight(new SerializableDrink(beverage, DateTimeHelper.timeStringFromDate(LocalDateTime.now())));
    }

    File getFile(String path) throws IOException {
        File file = new File(path);
        if (!file.createNewFile()){
            throw new IOException();
        }
        return file;
    }
}
