package customs.base;

import customs.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import unnamed.Location;

public class LocationWorkspaceController {
    private Location location;
    @FXML
    void addEvent(ActionEvent event) {
        location.getWorkspace().getChildren().add(new Event());
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
