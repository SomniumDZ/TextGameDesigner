package сore;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Location {
    public final static ToggleGroup LOCATIONS_TOGGLE_GROUP = new ToggleGroup();

    private String name;
    private ToggleButton button;
    private ImageView buttonImageView;
    private Image backgroundImage;

    public Location(String name, Image icon) {
        this.name = name;
        this.button = button;
    }

    static void createLocation(String name) {
        createLocation(name, null);
    }

    static void createLocation(String name, Image image) {
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
