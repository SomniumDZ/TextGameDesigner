package сore;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class Location {
    public final static ToggleGroup LOCATIONS_TOGGLE_GROUP = new ToggleGroup();

    private String name;
    private ToggleButton button;
    private ImageView buttonImageView;
    private Image backgroundImage;

    public Location(String name, Image icon) {
        this.name = name;
        buttonImageView = new ImageView(icon);
        try {
            button = FXMLLoader.load(getClass().getResource("/fxmls/locationButton.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        button.setToggleGroup(LOCATIONS_TOGGLE_GROUP);
        button.setGraphic(buttonImageView);
    }

    public static void createLocation(String name) {
        createLocation(name, null);
    }

    public static void createLocation(String name, Image image) {
        Main.locationList.add(new Location(name,image));

    }

    public String getName() {
        return name;
    }

    public ToggleButton getButton() {
        return button;
    }

    @Override
    public String toString() {
        return name;
    }
}
