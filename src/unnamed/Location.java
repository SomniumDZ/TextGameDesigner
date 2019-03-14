package unnamed;

import controllers.LocationTabController;
import customs.PaneUp;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
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
    private PaneUp workspace;

    private String UID;

    public Location(String UID, ComboBox<String> locationComboBox) {
        this.UID = UID;
        roadMap = new ArrayList<>();
        locationComboBox.getItems().add(UID);

        //View loading
        VBox tabView = new VBox();
        loader = new FXMLLoader();
        loader.setRoot(tabView);
        try {
            loader.load(new FileInputStream("fxmls/LocationView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            ew.throwError("Location fxml loader error");
        }
        ((LocationTabController)loader.getController()).getLocationViewLabel().setText(UID);
        ((LocationTabController)loader.getController()).setLocation(this);

        Main.getMainController().getLocationsPane().getChildren().add(
                Main.getMainController().getLocationsPane().getChildren().size()-1,
                tabView
        );

        //Workspace loading
        FXMLLoader workspaceLoader = new FXMLLoader();
        try {
            workspace = workspaceLoader.load(new FileInputStream("fxmls/LocationWorkspace.fxml"));
        } catch (IOException e) {
            ew.throwError("workspace loading error");
            throw new RuntimeException(e);
        }
    }

    public Location(){}

    public Location(String name, ComboBox<String> locationChoiceBox, Image image) {
        this(name, locationChoiceBox);
        ((LocationTabController) loader.getController()).getLocationViewImage().setImage(image);
    }

    public Pane getWorkspace() {
        return workspace;
    }

    public ArrayList<Element> getRoadMap() {
        return roadMap;
    }
}
