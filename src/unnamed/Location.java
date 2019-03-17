package unnamed;

import controllers.LocationTabController;
import customs.PaneUp;
import customs.base.LocationWorkspaceController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.Main;

import java.io.IOException;

import static main.Main.ew;

public class Location {
    private FXMLLoader loader;

    private PaneUp workspace;

    private String name;

    public Location(String name, ComboBox<String> locationComboBox) {
        this.name = name;
        locationComboBox.getItems().add(name);

        //View loading
        VBox tabView = new VBox();
        loader = new FXMLLoader(getClass().getResource("/fxmls/LocationView.fxml"));
        loader.setRoot(tabView);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            ew.throwError("Location fxml loader error");
        }
        ((LocationTabController)loader.getController()).getLocationViewLabel().setText(name);
        ((LocationTabController)loader.getController()).setLocation(this);

        Main.getMainController().getLocationsPane().getChildren().add(
                Main.getMainController().getLocationsPane().getChildren().size()-1,
                tabView
        );

        //Workspace loading
        FXMLLoader workspaceLoader = new FXMLLoader(getClass().getResource("/fxmls/LocationWorkspace.fxml"));
        try {
            workspace = workspaceLoader.load();
            ((LocationWorkspaceController) workspaceLoader.getController()).setLocation(this);
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

    public void setName(String name){
        ((LocationTabController)loader.getController()).getLocationViewLabel().setText(name);
        Main.getMainController().getLocationChoiceBox().getItems().removeAll(this.name);
        Main.getMainController().getLocationChoiceBox().getItems().add(name);
        this.name = name;
    }

    public Pane getWorkspace() {
        return workspace;
    }

}
