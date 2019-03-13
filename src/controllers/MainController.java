package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import static main.Main.getCurrentProject;

public class MainController {
    @FXML
    public BorderPane eventsAndActionsPane;
    @FXML
    public FlowPane locationsPane;
    @FXML
    ComboBox<String> locationsComboBox;

    private Stage addLocationMenuStage;

    public MainController() {
        addLocationMenuStage = new Stage();
        addLocationMenuStage.initModality(Modality.APPLICATION_MODAL);
        VBox root = new VBox();
        FileInputStream fis;
        try {
            fis = new FileInputStream("fxmls/AddLocationMenu.fxml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ew.throwError("File not fonded");
            return;
        }
        FXMLLoader addLocationMenuLoader = new FXMLLoader();
        addLocationMenuLoader.setRoot(root);
        try {
            addLocationMenuLoader.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            ew.throwError("FXML loading error");
        }
        Scene scene = new Scene(root);
        addLocationMenuStage.setScene(scene);
        addLocationMenuStage.setTitle("Add location");
    }

    @FXML
    void initialize(){

    }

    @FXML
    void changeLocation(ActionEvent event){
        Location chosenLocation = getCurrentProject().getLocations().get(locationsComboBox.getSelectionModel().getSelectedItem()+"");
        eventsAndActionsPane.setCenter(chosenLocation.getCanvas());
    }

    @FXML
    void addLocation(){
        addLocationMenuStage.showAndWait();
    }

    @FXML
    void save(){
        getCurrentProject().save(false);
    }

    @FXML
    void saveAs(){
        getCurrentProject().save(true);
    }

    @FXML
    void open(){

    }

    public ComboBox<String> getLocationChoiceBox() {
        return locationsComboBox;
    }

    public FlowPane getLocationsPane() {
        return locationsPane;
    }

    public Stage getAddLocationMenuStage() {
        return addLocationMenuStage;
    }
}
