package com.kristley.alcocalc;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


public class OverviewModel {
    public String calculateAbsoluteVolumeString(List<SerializableDrink> drinks) {
        double vol = 0;
        for (SerializableDrink sd :
                drinks) {
            vol += calculateAbsoluteVolumeOfBeverage(sd.getBeverage());
        }
        return String.format("%.1f", vol) + "ml";
    }

    private double calculateAbsoluteVolumeOfBeverage(Beverage beverage) {
        double multiplier = switch (beverage.getVolumeSuffix()) {
            case "l" -> 1000;
            case "dl" -> 100;
            case "cl" -> 10;
            case "ml" -> 1;
            default -> throw new IllegalStateException();
        };
        return multiplier * beverage.getVolume() * beverage.getPercentage() / 100;
    }

    public String calculateDrinkingTime(List<SerializableDrink> drinks, Night night) {

        if (drinks.size() == 0){
            return "0:0";
        }
        String end = drinks.get(drinks.size()-1).getTime();
        String start = night.getDate();

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