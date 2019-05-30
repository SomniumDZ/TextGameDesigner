package UIControllers;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

    public BorderPane sequenceEditor;
    public ComboBox<Location> locationSwitchBox;
    public FlowPane locationButtonsPane;

    public BaseController() {
    }

    public void initialize(){
        //Locations tab init
        locationSwitchBox.setItems(Main.locationList);
        locationButtonsPane.getChildren().add(ADD_LOCATION_BUTTON);
        Main.locationList.addListener((ListChangeListener<Location>) c -> {
            locationButtonsPane.getChildren().remove(ADD_LOCATION_BUTTON);
            locationButtonsPane.getChildren().add(c.getAddedSubList().get(0).getButton());
            locationButtonsPane.getChildren().add(ADD_LOCATION_BUTTON);
        });
        ADD_LOCATION_BUTTON.setOnAction(event -> {
            Stage stage = new Stage();
            VBox root;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxmls/addLocationParameters.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
                root = new VBox();
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        });
    }
}
