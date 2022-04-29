package com.kristley.alcoCalc.testing;

import com.kristley.alcocalc.Beverage;
import com.kristley.alcocalc.DateTimeHelper;
import com.kristley.alcocalc.SerializableDrink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SerializableDrinkTests {
    @Test
    void returnsCorrectVolume() {
        Beverage bev = new Beverage("Morgan", 70, "cl", 40, "#447ea9");
        SerializableDrink drink = new SerializableDrink(bev, DateTimeHelper.now());
        Assertions.assertEquals("70.0cl", drink.getVolume());
    }
    @Test
    void returnsCorrectPercentage() {
        Beverage bev = new Beverage("Morgan", 70, "cl", 40, "#447ea9");
        SerializableDrink drink = new SerializableDrink(bev, DateTimeHelper.now());
        Assertions.assertEquals("40.0%", drink.getPercentage());
    }
}
