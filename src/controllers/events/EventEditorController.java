package controllers.events;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EventEditorController {

    @FXML
    public TextField option1;
    @FXML
    public TextField option2;
    @FXML
    public TextField option3;
    @FXML
    public TextField option4;



    public EventEditorController() throws IOException {

    }

    public TextField[] getOptions() {
        return new TextField[]{option1, option2, option3, option4};
    }
}
