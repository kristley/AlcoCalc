package com.kristley.alcocalc;

import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ColoredCell extends TableCell<Drink, String> {

    private final Circle circle;

    public ColoredCell() {
        circle = new Circle(10);
    }

    @Override
    protected void updateItem(String color, boolean empty) {
        super.updateItem(color, empty);

        if (empty || color == null) {
            setText(null);
            circle.setFill(Color.TRANSPARENT);
            setGraphic(null);
        } else {
            circle.setFill(Color.web(color));
            setGraphic(circle);
        }
    }}
