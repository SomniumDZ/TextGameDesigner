package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import unnamed.Location;

public class LocationController {
    @FXML
    private ImageView locationViewImage;
    @FXML
    private Label locationViewLabel;

    private Location location;

    //ContextMenu
    @FXML
    private MenuItem addEventMenuItem;

    public LocationController() {

    }

    @FXML
    public void initialization(){
        addEventMenuItem.setOnAction(this::addEvent);
    }

    private void addEvent(ActionEvent event) {
        System.out.println("Checking");
    }

    public ImageView getLocationViewImage() {
        return locationViewImage;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Label getLocationViewLabel() {
        return locationViewLabel;
    }
}
