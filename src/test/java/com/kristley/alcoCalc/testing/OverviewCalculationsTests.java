package com.kristley.alcoCalc.testing;

import com.kristley.alcocalc.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OverviewCalculationsTests {
    @Test
    void correctAbsoluteVolume() {
        List<SerializableDrink> drinks = new ArrayList<>();
        drinks.add(new SerializableDrink(new Beverage("beer", 0.5, "l", 4.7, "#447ea9"), DateTimeHelper.now()));
        drinks.add(new SerializableDrink(new Beverage("beer", 5, "dl", 4.7, "#447ea9"), DateTimeHelper.now()));
        drinks.add(new SerializableDrink(new Beverage("beer", 500, "ml", 4.7, "#447ea9"), DateTimeHelper.now()));
        //1.5l * 1000 = 1500ml --- 1500ml * 4.7% = 70.5ml
        OverviewModel model = new OverviewModel();
        String vol = model.calculateAbsoluteVolumeString(drinks);
        Assertions.assertEquals("70.5ml", vol);
    }

    @Test
    void correctDrinkingTime() {
        LocalDateTime now = LocalDateTime.now();
        Night night = new Night(now.minusHours(1).minusMinutes(45));
        List<SerializableDrink> drinks = new ArrayList<>();
        drinks.add(new SerializableDrink(new Beverage("beer", 0.5, "l", 4.7, "#447ea9"), DateTimeHelper.now()));
        drinks.add(new SerializableDrink(new Beverage("beer", 5, "dl", 4.7, "#447ea9"), DateTimeHelper.now()));
        drinks.add(new SerializableDrink(new Beverage("beer", 500, "ml", 4.7, "#447ea9"), DateTimeHelper.now()));
        OverviewModel model = new OverviewModel();
        Assertions.assertEquals("1:45", model.calculateDrinkingTime(drinks, night));
    }
}
