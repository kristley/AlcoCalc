package com.kristley.alcocalc;

public class SerializableDrink {
    private Beverage beverage;
    private String time;

    public SerializableDrink(Beverage beverage, String time) {
        this.beverage = beverage;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public Beverage getBeverage(){
        return beverage;
    }

    public String getBeverageName() {
        return beverage.getBeverageName();
    }

    public String getVolume() {
        return beverage.getVolume() + beverage.getVolumeSuffix();
    }

    public String getPercentage() {
        return beverage.getPercentage() + "%";
    }

    public String getColor() {
        return beverage.getColor();
    }

}
