package com.kristley.alcocalc;

import java.io.IOException;

public class AddBeverageModel {
    public void add(Beverage beverage, String time) {
        try {
            NightsManager.addDrinkToCurrentNight(new SerializableDrink(beverage, time));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SceneManager.loadSceneFromResource("alcoCalc-view.fxml");
    }

    public boolean hasValidInput(String beverageText, String volumeText, String percentageText) {
        try {
            double d = Double.parseDouble(volumeText);
            double d2 = Double.parseDouble(percentageText);

        } catch (NumberFormatException e) {
            return false;
        }

        if (beverageText.length() < 1)
            return false;
        return true;
    }
    public Beverage GetBeverageFromFields(String beverageName, String volumeValue, String suffix, String percentageValue, int colorValue) {
        String hex8 = Integer.toHexString(colorValue);
        String color = "#" + hex8.substring(0, 6);

        Double volume = Double.parseDouble(volumeValue);
        Double percentage = Double.parseDouble(percentageValue);
        return new Beverage(beverageName, volume, suffix, percentage, color);
    }
}
