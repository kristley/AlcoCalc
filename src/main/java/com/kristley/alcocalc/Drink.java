package com.kristley.alcocalc;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;

import static java.time.format.DateTimeFormatter.ofPattern;

public class Drink {
    private SimpleStringProperty time;
    private SimpleStringProperty beverageName;
    private SimpleStringProperty volume;
    private SimpleStringProperty percentage;
    private SimpleStringProperty color;

    public Drink(SerializableDrink drink){
        time = new SimpleStringProperty(parseDateFromTimestamp(drink));
        beverageName = new SimpleStringProperty(drink.getBeverageName());
        volume = new SimpleStringProperty(drink.getVolume());
        percentage = new SimpleStringProperty(drink.getPercentage());
        color = new SimpleStringProperty(drink.getColor());
    }

    private String parseDateFromTimestamp(SerializableDrink drink) {
        TemporalAccessor time = ofPattern("yyyy-MM-dd HH:mm").parse(drink.getTime());
        LocalDateTime from = LocalDateTime.from(time);
        return from.format(ofPattern("HH:mm"));
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getBeverageName() {
        return beverageName.get();
    }

    public void setBeverageName(String beverageName) {
        this.beverageName.set(beverageName);
    }

    public String getVolume() {
        return volume.get();
    }

    public void setVolume(String volume) {
        this.volume.set(volume);
    }

    public String getPercentage() {
        return percentage.get();
    }

    public void setPercentage(String percentage) {
        this.percentage.set(percentage);
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }
}
