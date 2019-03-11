package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import unnamed.Location;

public class MainController {
    @FXML
    public BorderPane eventsAndActionsPane;
    @FXML
    ComboBox locationsComboBox;

    @FXML
    void initialize(){

    }

    @FXML
    void changeLocation(ActionEvent event){
        Location chosenLocation = Main.getLocations().get(locationsComboBox.getSelectionModel().getSelectedItem()+"");
        eventsAndActionsPane.setCenter(chosenLocation.getCanvas());
    }

    public ComboBox getLocationChoiceBox() {
        return locationsComboBox;
    }


}
