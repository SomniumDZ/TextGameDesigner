package unnamed;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import static main.Main.ew;

public class Location {
    private static FXMLLoader loader;
    private static FileInputStream viewFXMLFile;

    //View
    @FXML
    public ImageView locationViewImage;
    @FXML
    public Label locationViewLabel;

    HashMap<String, Node> nodes;
    Pane canvas;

    public Location(String UID, ComboBox locationComboBox) {
        nodes = new HashMap<>();
        locationComboBox.getItems().add(UID);
        canvas = new Pane();

        //View loading
        VBox view = new VBox();
        loader.setRoot(view);
        try {
            loader.load(viewFXMLFile);
        } catch (IOException e) {
            e.printStackTrace();
            ew.throwError("Location fxml loader error");
        }
    }

    public Location(){}

    @FXML
    public void initialize(){

    }

    public static void setLoader(FXMLLoader loader) {
        Location.loader = loader;
    }

    public static void setViewFXMLFile(FileInputStream viewFXMLFile) {
        Location.viewFXMLFile = viewFXMLFile;
    }

    public Pane getCanvas() {
        return canvas;
    }
}
