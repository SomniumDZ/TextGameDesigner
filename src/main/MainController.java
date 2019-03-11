package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import unnamed.Location;

public class MainController {
    @FXML
    public BorderPane eventsAndActionsPane;
    @FXML
    public FlowPane locationsPane;
    @FXML
    ComboBox locationsComboBox;

    public MainController() {
    }

    @FXML
    void initialize(){

    }

    @FXML
    void changeLocation(ActionEvent event){
        Location chosenLocation = Main.getLocations().get(locationsComboBox.getSelectionModel().getSelectedItem()+"");
        eventsAndActionsPane.setCenter(chosenLocation.getCanvas());
    }

    @FXML
    void addLocation(){

    }

    public ComboBox getLocationChoiceBox() {
        return locationsComboBox;
    }

    public FlowPane getLocationsPane() {
        return locationsPane;
    }
}
