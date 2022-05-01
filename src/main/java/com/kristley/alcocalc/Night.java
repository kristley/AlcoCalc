package com.kristley.alcocalc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Night {
    private String date;
    private List<SerializableDrink> history;

    public Night(LocalDateTime dateTime){
        date = DateTimeHelper.timeStringFromDate(dateTime);
        history = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public List<SerializableDrink> getSerializableDrinks() {
        return history;
    }

    public void add(SerializableDrink drink){
        history.add(drink);
    }

    public SerializableDrink getLatestDrink() {
        return null;
    }
}
