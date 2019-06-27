package service;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonsFactory {
    public static ToggleButton LocationButton(){
        ToggleButton locationButton = new ToggleButton();
        locationButton.setPrefHeight(60);
        locationButton.setPrefWidth(120);
        return locationButton;
    }

    public static Button addButton(int size) {
        return addButton(null, size);
    }

    public static Button addButton(String text, int size){
        Button addButton = new Button(text);
        ImageView graphic = new ImageView(new Image("add-location-icon.png"));
        graphic.setFitHeight(size-10);
        graphic.setFitWidth(size-10);
        addButton.setGraphic(graphic);
        addButton.setContentDisplay(ContentDisplay.TOP);
        addButton.setPrefHeight(size);
        addButton.setPrefWidth(size);
        return addButton;
    }
}
