package com.kristley.alcocalc;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.kristley.alcocalc.NightsManager.*;

public class OverviewModel {
    public double calculateAbsoluteVolume() {
        double vol = 0;
        for (SerializableDrink sd :
                getNight().getSerializableDrinks()) {
            vol += calculateAbsoluteVolumeOfBeverage(sd.getBeverage());
        }
        return vol;
    }

    private double calculateAbsoluteVolumeOfBeverage(Beverage beverage) {
        double multiplier;
        switch (beverage.getVolumeSuffix()){
            case "l":
                multiplier = 1000;
                break;
            case "dl":
                multiplier = 100;
                break;
            case "cl":
                multiplier = 10;
                break;
            case "ml":
                multiplier = 1;
                break;
            default:
                throw new IllegalStateException();
        }
        return multiplier * beverage.getVolume() * beverage.getPercentage() / 100;
    }

    public String calculateDrinkingTime() {

        List<SerializableDrink> drinks = getNight().getSerializableDrinks();
        if (drinks.size() == 0){
            return "0:0";
        }
        String end = drinks.get(drinks.size()-1).getTime();
        String start = getNight().getDate();

        LocalDateTime endTime = DateTimeHelper.timeFromString(end);
        LocalDateTime startTime = DateTimeHelper.timeFromString(start);

        Duration duration = Duration.between(startTime, endTime);

        return duration.toHoursPart() + ":" + duration.toMinutesPart();
    }

    public boolean canGoForward() {
        return NightsManager.hasFutureNight();
    }

    public boolean canGoBack() {
        return NightsManager.hasPastNight();
    }

    public void goBack() {
        NightsManager.goToPastNight();
    }

    public void goForward() {
        NightsManager.goToFutureNight();
    }
}