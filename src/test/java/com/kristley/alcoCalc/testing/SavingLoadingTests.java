package com.kristley.alcoCalc.testing;

import com.kristley.alcocalc.Beverage;
import com.kristley.alcocalc.DateTimeHelper;
import com.kristley.alcocalc.NightsManager;
import com.kristley.alcocalc.SerializableDrink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class SavingLoadingTests {
    private static Beverage beverage;

    @BeforeAll
    static void beforeAll() {
        beverage = new Beverage("Morgan", 70,"cl", 40, "#447ea9");
        getNewSaveFile().delete();
    }

    @Test
    void emptyFileHasEmptyNights() {
        File file = getNewSaveFile();
        new NightsManager(file.getAbsolutePath());
        Assertions.assertNull(NightsManager.getNight());
        Assertions.assertTrue(NightsManager.getAmountOfNights() == 0);
        Assertions.assertFalse(NightsManager.hasFutureNight());
        Assertions.assertFalse(NightsManager.hasPastNight());
        deleteFile(file);
    }

    @Test
    void savingAndLoadingNightsToFile() throws IOException {
        File file = getNewSaveFile();
        new NightsManager(file.getAbsolutePath());
        NightsManager.updateCurrentNightToTonight();
        NightsManager.addDrinkToCurrentNight(new SerializableDrink(beverage, DateTimeHelper.now()));
        new NightsManager(file.getAbsolutePath());//should initialize new manager with same file
        NightsManager.updateCurrentNightToTonight();
        Assertions.assertTrue(NightsManager.getNight().getSerializableDrinks().size() > 0, "Loaded Nights from file");
        deleteFile(file);
    }


    @Test
    void deletesEmptyNight() throws IOException {
        File file = getNewSaveFile();
        new NightsManager(file.getAbsolutePath());
        NightsManager.updateCurrentNightToTonight();
        NightsManager.saveNights();
        Assertions.assertEquals(1, NightsManager.getAmountOfNights());
        new NightsManager(file.getAbsolutePath());
        Assertions.assertEquals(1, NightsManager.getAmountOfNights());
        NightsManager.deleteEmptyNight();
        Assertions.assertEquals(0, NightsManager.getAmountOfNights());
    }

    @Test
    void existingFileHasMultipleNights() {
        File file = getExistingSaveFile();
        new NightsManager(file.getAbsolutePath());
        Assertions.assertTrue(NightsManager.getAmountOfNights() > 0);
    }
    @Test
    void canGoBackwardsAndNotForwards() {
        File file = getExistingSaveFile();
        new NightsManager(file.getAbsolutePath());
        NightsManager.updateCurrentNightToTonight();
        Assertions.assertFalse(NightsManager.hasFutureNight());
        Assertions.assertTrue(NightsManager.hasPastNight());
    }

    @Test
    void canGoForwards() {
        File file = getExistingSaveFile();
        new NightsManager(file.getAbsolutePath());
        NightsManager.updateCurrentNightToTonight();
        Assertions.assertTrue(NightsManager.hasPastNight());
        NightsManager.goToPastNight();
        Assertions.assertTrue(NightsManager.hasFutureNight());
    }

    @Test
    void canGoForwardsAndNotBackwards() {
        File file = getExistingSaveFile();
        new NightsManager(file.getAbsolutePath());
        NightsManager.updateCurrentNightToTonight();
        while (NightsManager.hasPastNight())
            NightsManager.goToPastNight();
        Assertions.assertTrue(NightsManager.hasFutureNight());
        Assertions.assertFalse(NightsManager.hasPastNight());
    }

    private void deleteFile(File file) {
        if (file.delete()) {

        }
    }

    static File getNewSaveFile(){
        File file = new File("src/test/resources/testNights.json");
        file.deleteOnExit();
        try {
            if (!file.createNewFile()){
                throw new IOException();
            }
        } catch (IOException e) {
        }
        return file;
    }

    File getExistingSaveFile(){
        return new File("src/test/resources/testMultipleNights.json");
    }
}
