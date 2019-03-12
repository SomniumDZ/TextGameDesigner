package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unnamed.Location;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static main.Main.ew;

public class MainController {
    @FXML
    public BorderPane eventsAndActionsPane;
    @FXML
    public FlowPane locationsPane;
    @FXML
    ComboBox<String> locationsComboBox;

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
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox root = new VBox();

        FileInputStream fis;
        try {
            fis = new FileInputStream("fxmls/AddLocationMenu.fxml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ew.throwError("File not fonded");
            return;
        }

        Main.getModalWindowsLoader().setRoot(root);
        try {
            Main.getModalWindowsLoader().load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            ew.throwError("FXML loading error");
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add location");
        stage.showAndWait();
    }

    public ComboBox<String> getLocationChoiceBox() {
        return locationsComboBox;
    }

    public FlowPane getLocationsPane() {
        return locationsPane;
    }
}
