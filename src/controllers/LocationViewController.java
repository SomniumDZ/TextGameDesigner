package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class LocationViewController {
    @FXML
    public ImageView locationViewImage;
    @FXML
    public Label locationViewLabel;

    public LocationViewController() {

    }

    @FXML
    public void initialization(){

    }

    public ImageView getLocationViewImage() {
        return locationViewImage;
    }

    public Label getLocationViewLabel() {
        return locationViewLabel;
    }
}
