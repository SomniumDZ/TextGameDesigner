package customs.base;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import unnamed.Location;

public class LocationWorkspaceController {
    private Location location;
    @FXML
    void addEvent(ActionEvent event) {

    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
