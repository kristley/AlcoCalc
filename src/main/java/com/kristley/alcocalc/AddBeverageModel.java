package com.kristley.alcocalc;

import java.io.IOException;

public class AddBeverageModel {
    public void add(Beverage beverage, String time) {
        try {
            NightsManager.addDrinkToNight(new SerializableDrink(beverage, time));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
