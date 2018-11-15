package controllers.events;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FOptionsEventVisualController extends VisualController {
    @FXML
    public Label option1;
    @FXML
    public Label option2;
    @FXML
    public Label option3;
    @FXML
    public Label option4;

    public FOptionsEventVisualController() {

    }

    public Label[] getOptions(){
        return  new Label[]{option1, option2, option3, option4};
    }
}
