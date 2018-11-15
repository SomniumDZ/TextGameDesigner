package controllers.events;

import javafx.scene.layout.GridPane;

import java.io.IOException;

public class FOptionalEvent extends Event<GridPane> {

    public FOptionalEvent(double x, double y) throws IOException {
        super(x, y, "fxmls/4OptionsEvent.fxml", "fxmls/4OptionsEventEditor.fxml", new Event[4]);

    }
}
