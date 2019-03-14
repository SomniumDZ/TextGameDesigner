package unnamed;

import controllers.LocationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static main.Main.ew;

public class Location {
    private FXMLLoader loader;

    private ArrayList<Element> roadMap;
    private Pane canvas;

    private String UID;

    private ContextMenu contextMenu;

    public Location(String UID, ComboBox<String> locationComboBox) {
        this.UID = UID;
        roadMap = new ArrayList<>();
        locationComboBox.getItems().add(UID);
        canvas = new Pane();

        //View loading
        VBox view = new VBox();
        loader = new FXMLLoader();
        loader.setRoot(view);
        FXMLLoader contextMenuLoader = new FXMLLoader();
        contextMenu = new ContextMenu();
        contextMenuLoader.setRoot(contextMenu);
        try {
            loader.load(new FileInputStream("fxmls/LocationView.fxml"));
            contextMenuLoader.setController(loader.getController());
            contextMenuLoader.load(new FileInputStream("fxmls/LocationContextMenu.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            ew.throwError("Location fxml loader error");
        }
        ((LocationController)loader.getController()).initialization();
        ((LocationController)loader.getController()).getLocationViewLabel().setText(UID);
        ((LocationController)loader.getController()).setLocation(this);

        Main.getMainController().getLocationsPane().getChildren().add(
                Main.getMainController().getLocationsPane().getChildren().size()-1,
                view
        );

        canvas.setOnContextMenuRequested(event -> contextMenu.show(canvas, event.getScreenX(), event.getScreenY()));
    }

    public Location(){}

    public Location(String name, ComboBox<String> locationChoiceBox, Image image) {
        this(name, locationChoiceBox);
        ((LocationController) loader.getController()).getLocationViewImage().setImage(image);
    }

    public Pane getCanvas() {
        return canvas;
    }

    public ArrayList<Element> getRoadMap() {
        return roadMap;
    }
}
