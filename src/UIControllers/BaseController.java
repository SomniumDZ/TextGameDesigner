package UIControllers;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import сore.Location;
import сore.Main;

public class BaseController {
    public BorderPane sequenceEditor;
    public ComboBox<Location> locationSwitchBox;

    public BaseController() {

    }

    public void initialize(){
        locationSwitchBox.setItems(Main.locationList);
    }
}
