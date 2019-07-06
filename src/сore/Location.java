package сore;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.ButtonsFactory;
import сore.nodes.SequenceNode;

public class Location {
    public final static ToggleGroup LOCATIONS_TOGGLE_GROUP = new ToggleGroup();

    private String name;
    private ToggleButton button;
    private ImageView buttonImageView;
    private Image backgroundImage;
    private SequencePane sequencePane;

    public Location(String name, Image icon) {
        this.name = name;
        buttonImageView = new ImageView(icon);
        button = ButtonsFactory.LocationButton();
        button.setToggleGroup(LOCATIONS_TOGGLE_GROUP);
        button.setGraphic(buttonImageView);
        button.setText(name);
        sequencePane = new SequencePane(sequenceContextMenu());
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

    public SequencePane getSequencePane() {
        return sequencePane;
    }

    public ContextMenu sequenceContextMenu(){
        MenuItem addEvent = new MenuItem("Add event");

        addEvent.setOnAction(event -> {
            sequencePane.getChildren().add(new SequenceNode());
        });

        Menu addNode = new Menu("Add node", null, addEvent);

        return new ContextMenu(addNode);
    }
}
