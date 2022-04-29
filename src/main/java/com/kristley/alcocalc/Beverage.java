package com.kristley.alcocalc;

public class Beverage {
    private final String name;
    private final double volume;
    private final double percentage;
    private final String color;
    private final String volumeSuffix;

    public Beverage(String beverageName, double volume, String volumeSuffix, double percentage, String color) {
        this.name = beverageName;
        this.volume = volume;
        this.volumeSuffix = volumeSuffix;
        this.percentage = percentage;
        this.color = color;
    }

    public String getBeverageName() {
        return name;
    }

    public double getVolume() {
        return volume;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getColor() {
        return color;
    }

    public String getVolumeSuffix() {
        return volumeSuffix;
    }
}
