package unnamed;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import static main.Main.ew;

public class Location {
    private FXMLLoader loader;

    private HashMap<String, Node> nodes;
    private Pane canvas;

    private String UID;

    public Location(String UID, ComboBox<String> locationComboBox) {
        this.UID = UID;
        nodes = new HashMap<>();
        locationComboBox.getItems().add(UID);
        canvas = new Pane();

        //View loading
        VBox view = new VBox();
        loader = new FXMLLoader();
        loader.setRoot(view);
        try {
            loader.load(new FileInputStream("fxmls/LocationView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            ew.throwError("Location fxml loader error");
        }
        ((LocationViewController)loader.getController()).getLocationViewLabel().setText(UID);

        Main.getMainController().getLocationsPane().getChildren().add(
                Main.getMainController().getLocationsPane().getChildren().size()-1,
                view
        );
    }

    public Location(){}

    public Location(String name, ComboBox<String> locationChoiceBox, Image image) {
        this(name, locationChoiceBox);
        ((LocationViewController) loader.getController()).getLocationViewImage().setImage(image);
    }

    @FXML
    public void initialize(){
    }

    public Pane getCanvas() {
        return canvas;
    }
}
