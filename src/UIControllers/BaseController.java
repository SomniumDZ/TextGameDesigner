package UIControllers;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import сore.Location;
import сore.Main;

import java.io.IOException;

public class BaseController {
    public static final Button ADD_LOCATION_BUTTON = new Button(
            "Add location",
            new ImageView(new Image("res/add-location-icon.png"))
    );
    public static final Stage ADD_LOCATION_STAGE = new Stage();

    public BorderPane sequenceEditor;
    public ComboBox<Location> locationSwitchBox;
    public FlowPane locationButtonsPane;

    public BaseController() {
    }

    public void initialize(){
        //Locations tab init
        locationSwitchBox.setItems(Main.locationList);
        VBox addLocationStageRoot;
        try {
            addLocationStageRoot = FXMLLoader.load(getClass().getResource("/fxmls/addLocationParameters.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            addLocationStageRoot = new VBox();
        }
        Scene scene = new Scene(addLocationStageRoot);
        ADD_LOCATION_STAGE.setScene(scene);
        ADD_LOCATION_STAGE.initModality(Modality.APPLICATION_MODAL);
        ((ImageView) ADD_LOCATION_BUTTON.getGraphic()).setFitWidth(60);
        ((ImageView) ADD_LOCATION_BUTTON.getGraphic()).setFitHeight(60);
        ADD_LOCATION_BUTTON.setContentDisplay(ContentDisplay.TOP);
        ADD_LOCATION_BUTTON.setOnAction(event -> ADD_LOCATION_STAGE.showAndWait());
        locationButtonsPane.getChildren().add(ADD_LOCATION_BUTTON);
        Main.locationList.addListener((ListChangeListener<Location>) c -> {
            locationButtonsPane.getChildren().remove(ADD_LOCATION_BUTTON);
            while (c.next()) {
                locationButtonsPane.getChildren().add(c.getAddedSubList().get(0).getButton());
            }
            locationButtonsPane.getChildren().add(ADD_LOCATION_BUTTON);
        });
    }
}
